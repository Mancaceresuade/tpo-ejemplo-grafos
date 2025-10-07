package com.prog3.demo.model;

import java.util.*;

/**
 * Grafo simplificado basado en lista de adyacencia para películas y personas.
 * Usa solo String IDs para representar nodos - mucho más simple y directo.
 */
public class SimpleMovieGraph {
    
    // Lista de adyacencia: Map<ID_nodo, Lista_de_IDs_adyacentes>
    private Map<String, List<String>> adjacencyList;
    
    // Mapa para saber el tipo de cada nodo: "MOVIE" o "PERSON"
    private Map<String, String> nodeTypes;
    
    // Lista de aristas con información de relación: [from, to, relationshipType]
    private List<String[]> edges;
    
    public SimpleMovieGraph() {
        this.adjacencyList = new HashMap<>();
        this.nodeTypes = new HashMap<>();
        this.edges = new ArrayList<>();
    }
    
    /**
     * Agrega un nodo al grafo
     */
    public void addNode(String nodeId, String type) {
        adjacencyList.putIfAbsent(nodeId, new ArrayList<>());
        nodeTypes.put(nodeId, type);
    }
    
    /**
     * Agrega una arista entre dos nodos (evita duplicados)
     */
    public void addEdge(String fromId, String toId, String relationshipType) {
        // Asegurar que ambos nodos existan
        if (!adjacencyList.containsKey(fromId)) {
            adjacencyList.put(fromId, new ArrayList<>());
        }
        if (!adjacencyList.containsKey(toId)) {
            adjacencyList.put(toId, new ArrayList<>());
        }
        
        // Agregar la arista a la lista de adyacencia solo si no existe
        List<String> neighbors = adjacencyList.get(fromId);
        if (!neighbors.contains(toId)) {
            neighbors.add(toId);
        }
        
        // Agregar la arista a la lista de aristas solo si no existe
        String[] newEdge = new String[]{fromId, toId, relationshipType};
        boolean edgeExists = edges.stream()
                .anyMatch(edge -> edge[0].equals(fromId) && edge[1].equals(toId) && edge[2].equals(relationshipType));
        
        if (!edgeExists) {
            edges.add(newEdge);
        }
    }
    
    /**
     * Construye el grafo a partir de entidades MovieEntity y PersonEntity
     */
    public void buildFromEntities(List<MovieEntity> movies, List<PersonEntity> persons) {
        // Agregar todos los nodos de películas
        for (MovieEntity movie : movies) {
            addNode(movie.getTitle(), "MOVIE");
        }
        
        // Agregar todos los nodos de personas
        for (PersonEntity person : persons) {
            addNode(person.getName(), "PERSON");
        }
        
        // Agregar las relaciones (BIDIRECCIONALES)
        for (MovieEntity movie : movies) {
            String movieId = movie.getTitle();
            
            // Relaciones ACTED_IN (Person <-> Movie) - BIDIRECCIONAL
            for (PersonEntity actor : movie.getActors()) {
                String actorId = actor.getName();
                addEdge(actorId, movieId, "ACTED_IN");
                addEdge(movieId, actorId, "ACTED_BY"); // Relación inversa
            }
            
            // Relaciones DIRECTED (Person <-> Movie) - BIDIRECCIONAL  
            for (PersonEntity director : movie.getDirectors()) {
                String directorId = director.getName();
                addEdge(directorId, movieId, "DIRECTED");
                addEdge(movieId, directorId, "DIRECTED_BY"); // Relación inversa
            }
        }
    }
    
    /**
     * Algoritmo DFS (Depth-First Search) - Recursivo
     */
    public List<String> dfs(String startNodeId) {
        if (!adjacencyList.containsKey(startNodeId)) {
            return new ArrayList<>();
        }
        
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        
        dfsRecursive(startNodeId, visited, result);
        return result;
    }
    
    private void dfsRecursive(String currentNodeId, Set<String> visited, List<String> result) {
        visited.add(currentNodeId);
        result.add(currentNodeId);
        
        // Visitar todos los nodos adyacentes no visitados
        List<String> neighbors = adjacencyList.get(currentNodeId);
        if (neighbors != null) {
            for (String neighborId : neighbors) {
                if (!visited.contains(neighborId)) {
                    dfsRecursive(neighborId, visited, result);
                }
            }
        }
    }
    
    /**
     * Algoritmo DFS iterativo usando pila
     */
    public List<String> dfsIterative(String startNodeId) {
        if (!adjacencyList.containsKey(startNodeId)) {
            return new ArrayList<>();
        }
        
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        
        stack.push(startNodeId);
        
        while (!stack.isEmpty()) {
            String currentNodeId = stack.pop();
            
            if (!visited.contains(currentNodeId)) {
                visited.add(currentNodeId);
                result.add(currentNodeId);
                
                // Agregar vecinos no visitados a la pila
                List<String> neighbors = adjacencyList.get(currentNodeId);
                if (neighbors != null) {
                    for (String neighborId : neighbors) {
                        if (!visited.contains(neighborId)) {
                            stack.push(neighborId);
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Algoritmo BFS (Breadth-First Search)
     */
    public List<String> bfs(String startNodeId) {
        if (!adjacencyList.containsKey(startNodeId)) {
            return new ArrayList<>();
        }
        
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        
        queue.offer(startNodeId);
        visited.add(startNodeId);
        
        while (!queue.isEmpty()) {
            String currentNodeId = queue.poll();
            result.add(currentNodeId);
            
            // Agregar todos los vecinos no visitados a la cola
            List<String> neighbors = adjacencyList.get(currentNodeId);
            if (neighbors != null) {
                for (String neighborId : neighbors) {
                    if (!visited.contains(neighborId)) {
                        visited.add(neighborId);
                        queue.offer(neighborId);
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Encuentra el camino más corto entre dos nodos usando BFS
     */
    public List<String> findShortestPath(String startNodeId, String endNodeId) {
        if (!adjacencyList.containsKey(startNodeId) || !adjacencyList.containsKey(endNodeId)) {
            return new ArrayList<>();
        }
        
        Map<String, String> parent = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.offer(startNodeId);
        visited.add(startNodeId);
        parent.put(startNodeId, null);
        
        while (!queue.isEmpty()) {
            String currentNodeId = queue.poll();
            
            if (currentNodeId.equals(endNodeId)) {
                // Reconstruir el camino
                return reconstructPath(parent, startNodeId, endNodeId);
            }
            
            List<String> neighbors = adjacencyList.get(currentNodeId);
            if (neighbors != null) {
                for (String neighborId : neighbors) {
                    if (!visited.contains(neighborId)) {
                        visited.add(neighborId);
                        parent.put(neighborId, currentNodeId);
                        queue.offer(neighborId);
                    }
                }
            }
        }
        
        return new ArrayList<>(); // No hay camino
    }
    
    private List<String> reconstructPath(Map<String, String> parent, String start, String end) {
        List<String> path = new ArrayList<>();
        String current = end;
        
        while (current != null) {
            path.add(0, current); // Agregar al inicio
            current = parent.get(current);
        }
        
        return path;
    }
    
    /**
     * Obtiene todos los nodos del grafo
     */
    public Set<String> getAllNodes() {
        return new HashSet<>(adjacencyList.keySet());
    }
    
    /**
     * Obtiene todos los nodos de un tipo específico
     */
    public List<String> getNodesByType(String type) {
        return adjacencyList.keySet().stream()
                .filter(nodeId -> nodeTypes.get(nodeId).equals(type.toUpperCase()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Obtiene los vecinos de un nodo específico
     */
    public List<String> getNeighbors(String nodeId) {
        return adjacencyList.getOrDefault(nodeId, new ArrayList<>());
    }
    
    /**
     * Obtiene todas las aristas del grafo
     */
    public List<String[]> getAllEdges() {
        return new ArrayList<>(edges);
    }
    
    /**
     * Obtiene el tipo de un nodo
     */
    public String getNodeType(String nodeId) {
        return nodeTypes.get(nodeId);
    }
    
    /**
     * Verifica si el grafo está vacío
     */
    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }
    
    /**
     * Obtiene el número de nodos
     */
    public int getNodeCount() {
        return adjacencyList.size();
    }
    
    /**
     * Obtiene el número de aristas
     */
    public int getEdgeCount() {
        return edges.size();
    }
    
    /**
     * Obtiene información detallada de un nodo
     */
    public Map<String, Object> getNodeInfo(String nodeId) {
        Map<String, Object> info = new HashMap<>();
        info.put("id", nodeId);
        info.put("type", nodeTypes.get(nodeId));
        info.put("neighbors", getNeighbors(nodeId));
        info.put("neighborCount", getNeighbors(nodeId).size());
        return info;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SimpleMovieGraph {\n");
        sb.append("  Nodos: ").append(getNodeCount()).append("\n");
        sb.append("  Aristas: ").append(getEdgeCount()).append("\n");
        
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            sb.append("  ").append(entry.getKey())
              .append(" (").append(nodeTypes.get(entry.getKey())).append(") -> ");
            for (String neighbor : entry.getValue()) {
                sb.append(neighbor).append(" ");
            }
            sb.append("\n");
        }
        
        sb.append("}");
        return sb.toString();
    }
}
