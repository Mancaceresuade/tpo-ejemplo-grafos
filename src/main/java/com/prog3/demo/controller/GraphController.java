package com.prog3.demo.controller;

import com.prog3.demo.service.GraphService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para operaciones del grafo de películas y personas
 */
@RestController
@RequestMapping("/graph")
public class GraphController {
    
    private final GraphService graphService;
    
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }
    
    /**
     * Obtiene información general del grafo
     */
    @GetMapping("/info")
    public Mono<Map<String, Object>> getGraphInfo() {
        return graphService.getGraphInfo();
    }
    
    /**
     * Obtiene todos los nodos del grafo
     */
    @GetMapping("/nodes")
    public Mono<List<String>> getAllNodes() {
        return graphService.getAllNodes();
    }
    
    /**
     * Obtiene nodos por tipo (MOVIE o PERSON)
     */
    @GetMapping("/nodes/type/{type}")
    public Mono<List<String>> getNodesByType(@PathVariable String type) {
        return graphService.getNodesByType(type);
    }
    
    /**
     * Obtiene los vecinos de un nodo específico
     */
    @GetMapping("/nodes/{nodeId}/neighbors")
    public Mono<List<String>> getNeighbors(@PathVariable String nodeId) {
        return graphService.getNeighbors(nodeId);
    }
    
    /**
     * Obtiene todas las aristas del grafo
     */
    @GetMapping("/edges")
    public Mono<List<String[]>> getAllEdges() {
        return graphService.getAllEdges();
    }
    
    /**
     * Obtiene información detallada de un nodo
     */
    @GetMapping("/nodes/{nodeId}/info")
    public Mono<Map<String, Object>> getNodeInfo(@PathVariable String nodeId) {
        return graphService.getNodeInfo(nodeId);
    }
    
    /**
     * Realiza un recorrido DFS (Depth-First Search) recursivo
     */
    @GetMapping("/dfs/{startNodeId}")
    public Mono<List<String>> performDFS(@PathVariable String startNodeId) {
        return graphService.performDFS(startNodeId);
    }
    
    /**
     * Realiza un recorrido DFS iterativo
     */
    @GetMapping("/dfs-iterative/{startNodeId}")
    public Mono<List<String>> performDFSIterative(@PathVariable String startNodeId) {
        return graphService.performDFSIterative(startNodeId);
    }
    
    /**
     * Realiza un recorrido BFS (Breadth-First Search)
     */
    @GetMapping("/bfs/{startNodeId}")
    public Mono<List<String>> performBFS(@PathVariable String startNodeId) {
        return graphService.performBFS(startNodeId);
    }
    
    /**
     * Encuentra el camino más corto entre dos nodos
     */
    @GetMapping("/shortest-path/{startNodeId}/{endNodeId}")
    public Mono<List<String>> findShortestPath(
            @PathVariable String startNodeId, 
            @PathVariable String endNodeId) {
        return graphService.findShortestPath(startNodeId, endNodeId);
    }
    
    /**
     * Obtiene la representación en string del grafo
     */
    @GetMapping(value = "/string", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> getGraphString() {
        return graphService.getGraphString();
    }
    
    /**
     * Endpoint para comparar DFS vs BFS desde el mismo nodo
     */
    @GetMapping("/compare-traversals/{startNodeId}")
    public Mono<Map<String, Object>> compareTraversals(@PathVariable String startNodeId) {
        return Mono.zip(
                graphService.performDFS(startNodeId),
                graphService.performBFS(startNodeId)
        ).map(tuple -> Map.of(
                "startNode", startNodeId,
                "dfs", tuple.getT1(),
                "bfs", tuple.getT2(),
                "dfsCount", tuple.getT1().size(),
                "bfsCount", tuple.getT2().size()
        ));
    }
    
    /**
     * Obtiene los datos del grafo en formato JSON para Cytoscape.js
     */
    @GetMapping("/cytoscape-data")
    public Mono<Map<String, Object>> getCytoscapeData() {
        return graphService.getCytoscapeData();
    }
    
    /**
     * Agrega un nuevo actor al grafo
     */
    @PostMapping("/add-actor")
    public Mono<Map<String, Object>> addActor(@RequestBody Map<String, Object> request) {
        String actorName = (String) request.get("actorName");
        @SuppressWarnings("unchecked")
        List<String> movieTitles = (List<String>) request.get("movieTitles");
        
        if (actorName == null || actorName.trim().isEmpty()) {
            return Mono.just(Map.of("success", false, "message", "El nombre del actor es requerido"));
        }
        
        if (movieTitles == null || movieTitles.isEmpty()) {
            return Mono.just(Map.of("success", false, "message", "Debe especificar al menos una película"));
        }
        
        return graphService.addActor(actorName.trim(), movieTitles);
    }
    
    /**
     * Obtiene la lista de todas las películas disponibles
     */
    @GetMapping("/movies")
    public Mono<List<String>> getAllMovies() {
        return graphService.getAllMovies();
    }
    
    /**
     * Obtiene la lista de todos los actores disponibles
     */
    @GetMapping("/actors")
    public Mono<List<String>> getAllActors() {
        return graphService.getAllActors();
    }
}
