package com.prog3.demo.service;

import com.prog3.demo.model.*;
import com.prog3.demo.repo.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio para manejar operaciones del grafo de películas y personas
 */
@Service
public class GraphService {
    
    private final MovieRepository movieRepository;
    private SimpleMovieGraph movieGraph;
    
    public GraphService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.movieGraph = new SimpleMovieGraph();
    }
    
    /**
     * Construye el grafo a partir de los datos en Neo4j
     */
    public Mono<SimpleMovieGraph> buildGraph() {
        return movieRepository.findAll()
                .collectList()
                .map(movies -> {
                    // Extraer todas las personas únicas de las películas
                    List<PersonEntity> allPersons = movies.stream()
                            .flatMap(movie -> {
                                List<PersonEntity> persons = movie.getActors().stream()
                                        .collect(Collectors.toList());
                                persons.addAll(movie.getDirectors());
                                return persons.stream();
                            })
                            .distinct()
                            .collect(Collectors.toList());
                    
                    // Construir el grafo
                    movieGraph.buildFromEntities(movies, allPersons);
                    return movieGraph;
                });
    }
    
    /**
     * Obtiene el grafo actual
     */
    public SimpleMovieGraph getGraph() {
        return movieGraph;
    }
    
    /**
     * Realiza un recorrido DFS desde un nodo específico
     */
    public Mono<List<String>> performDFS(String startNodeId) {
        return buildGraph()
                .map(graph -> graph.dfs(startNodeId));
    }
    
    /**
     * Realiza un recorrido DFS iterativo desde un nodo específico
     */
    public Mono<List<String>> performDFSIterative(String startNodeId) {
        return buildGraph()
                .map(graph -> graph.dfsIterative(startNodeId));
    }
    
    /**
     * Realiza un recorrido BFS desde un nodo específico
     */
    public Mono<List<String>> performBFS(String startNodeId) {
        return buildGraph()
                .map(graph -> graph.bfs(startNodeId));
    }
    
    /**
     * Encuentra el camino más corto entre dos nodos
     */
    public Mono<List<String>> findShortestPath(String startNodeId, String endNodeId) {
        return buildGraph()
                .map(graph -> graph.findShortestPath(startNodeId, endNodeId));
    }
    
    /**
     * Obtiene información del grafo
     */
    public Mono<Map<String, Object>> getGraphInfo() {
        return buildGraph()
                .map(graph -> Map.of(
                    "nodeCount", graph.getNodeCount(),
                    "edgeCount", graph.getEdgeCount(),
                    "isEmpty", graph.isEmpty()
                ));
    }
    
    /**
     * Obtiene todos los nodos del grafo
     */
    public Mono<List<String>> getAllNodes() {
        return buildGraph()
                .map(graph -> graph.getAllNodes().stream()
                        .collect(Collectors.toList()));
    }
    
    /**
     * Obtiene todos los nodos de un tipo específico
     */
    public Mono<List<String>> getNodesByType(String type) {
        return buildGraph()
                .map(graph -> graph.getNodesByType(type));
    }
    
    /**
     * Obtiene los vecinos de un nodo específico
     */
    public Mono<List<String>> getNeighbors(String nodeId) {
        return buildGraph()
                .map(graph -> graph.getNeighbors(nodeId));
    }
    
    /**
     * Obtiene todas las aristas del grafo
     */
    public Mono<List<String[]>> getAllEdges() {
        return buildGraph()
                .map(SimpleMovieGraph::getAllEdges);
    }
    
    /**
     * Obtiene información detallada de un nodo
     */
    public Mono<Map<String, Object>> getNodeInfo(String nodeId) {
        return buildGraph()
                .map(graph -> graph.getNodeInfo(nodeId));
    }
    
    /**
     * Obtiene la representación en string del grafo
     */
    public Mono<String> getGraphString() {
        return buildGraph()
                .map(SimpleMovieGraph::toString);
    }
    
    /**
     * Obtiene los datos del grafo en formato JSON para Cytoscape.js
     */
    public Mono<Map<String, Object>> getCytoscapeData() {
        return buildGraph()
                .map(graph -> {
                    // Crear nodos para Cytoscape
                    List<Object> nodes = graph.getAllNodes().stream()
                            .map(nodeId -> {
                                Map<String, Object> nodeData = new java.util.HashMap<>();
                                nodeData.put("id", nodeId);
                                nodeData.put("label", nodeId);
                                nodeData.put("type", graph.getNodeType(nodeId));
                                
                                Map<String, Object> node = new java.util.HashMap<>();
                                node.put("data", nodeData);
                                return node;
                            })
                            .collect(Collectors.toList());
                    
                    // Crear aristas para Cytoscape
                    List<Object> edges = graph.getAllEdges().stream()
                            .map(edge -> {
                                Map<String, Object> edgeData = new java.util.HashMap<>();
                                edgeData.put("id", edge[0] + "-" + edge[1] + "-" + edge[2]);
                                edgeData.put("source", edge[0]);
                                edgeData.put("target", edge[1]);
                                edgeData.put("label", edge[2]);
                                
                                Map<String, Object> edgeObj = new java.util.HashMap<>();
                                edgeObj.put("data", edgeData);
                                return edgeObj;
                            })
                            .collect(Collectors.toList());
                    
                    Map<String, Object> result = new java.util.HashMap<>();
                    result.put("nodes", nodes);
                    result.put("edges", edges);
                    return result;
                });
    }
    
    /**
     * Agrega un nuevo actor al grafo con sus películas
     */
    public Mono<Map<String, Object>> addActor(String actorName, List<String> movieTitles) {
        return buildGraph()
                .map(graph -> {
                    try {
                        // Verificar si el actor ya existe
                        if (graph.getAllNodes().contains(actorName)) {
                            return Map.of(
                                "success", false, 
                                "message", "El actor '" + actorName + "' ya existe en el grafo"
                            );
                        }
                        
                        // Agregar el nodo del actor
                        graph.addNode(actorName, "PERSON");
                        
                        // Agregar relaciones con las películas
                        int addedRelations = 0;
                        for (String movieTitle : movieTitles) {
                            if (graph.getAllNodes().contains(movieTitle)) {
                                // La película existe, agregar la relación
                                graph.addEdge(actorName, movieTitle, "ACTED_IN");
                                graph.addEdge(movieTitle, actorName, "ACTED_BY");
                                addedRelations++;
                            }
                        }
                        
                        if (addedRelations == 0) {
                            // Si no se agregaron relaciones, remover el nodo del actor
                            graph.getAllNodes().remove(actorName);
                            return Map.of(
                                "success", false,
                                "message", "No se encontraron películas válidas. El actor no fue agregado."
                            );
                        }
                        
                        return Map.of(
                            "success", true,
                            "message", String.format("Actor '%s' agregado exitosamente con %d películas", actorName, addedRelations),
                            "actorName", actorName,
                            "addedRelations", addedRelations
                        );
                        
                    } catch (Exception e) {
                        return Map.of(
                            "success", false,
                            "message", "Error agregando actor: " + e.getMessage()
                        );
                    }
                });
    }
    
    /**
     * Obtiene todas las películas del grafo
     */
    public Mono<List<String>> getAllMovies() {
        return buildGraph()
                .map(graph -> graph.getNodesByType("MOVIE"));
    }
    
    /**
     * Obtiene todos los actores del grafo
     */
    public Mono<List<String>> getAllActors() {
        return buildGraph()
                .map(graph -> graph.getNodesByType("PERSON"));
    }
}
