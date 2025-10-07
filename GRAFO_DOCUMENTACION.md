# Grafo de Películas y Personas - Documentación (Versión Simplificada)

## Descripción

Se ha implementado un **grafo simplificado basado en lista de adyacencia** para representar las entidades `MovieEntity` y `PersonEntity` con sus relaciones, incluyendo algoritmos de recorrido **DFS** y **BFS**.

**🎯 Característica principal**: Usa solo **String IDs** como nodos - mucho más simple y directo.

## Estructura del Grafo

### Nodos (String IDs)
- **Películas**: Títulos como "The Matrix", "Inception", etc.
- **Personas**: Nombres como "Keanu Reeves", "Christopher Nolan", etc.

### Relaciones
- **ACTED_IN**: Persona → Película (actor actuó en película)
- **DIRECTED**: Persona → Película (director dirigió película)

## Clase Implementada

### `SimpleMovieGraph`
Clase única que implementa el grafo simplificado con lista de adyacencia.

**Características:**
- Lista de adyacencia: `Map<String, List<String>>` (ID → Lista de IDs adyacentes)
- Tipos de nodos: `Map<String, String>` (ID → "MOVIE" o "PERSON")
- Lista de aristas: `List<String[]>` (from, to, relationshipType)

## Algoritmos Implementados

### DFS (Depth-First Search)
- **Recursivo**: `dfs(String startNodeId)` → retorna `List<String>`
- **Iterativo**: `dfsIterative(String startNodeId)` → retorna `List<String>` (usa pila)

### BFS (Breadth-First Search)
- **Iterativo**: `bfs(String startNodeId)` → retorna `List<String>` (usa cola)

### Camino Más Corto
- **BFS**: `findShortestPath(String startNodeId, String endNodeId)` → retorna `List<String>`

## API REST Endpoints

### Información del Grafo
```
GET /graph/info                    # Información general
GET /graph/nodes                   # Todos los nodos (List<String>)
GET /graph/nodes/type/{type}       # Nodos por tipo (MOVIE/PERSON)
GET /graph/nodes/{nodeId}/info     # Información detallada de un nodo
GET /graph/nodes/{nodeId}/neighbors # Vecinos de un nodo
GET /graph/edges                   # Todas las aristas (List<String[]>)
GET /graph/string                  # Representación en texto
```

### Algoritmos de Recorrido
```
GET /graph/dfs/{startNodeId}                    # DFS recursivo (List<String>)
GET /graph/dfs-iterative/{startNodeId}          # DFS iterativo (List<String>)
GET /graph/bfs/{startNodeId}                    # BFS (List<String>)
GET /graph/shortest-path/{startNodeId}/{endNodeId}  # Camino más corto (List<String>)
```

### Utilidades
```
GET /graph/compare-traversals/{startNodeId}     # Comparar DFS vs BFS
```

## Ejemplos de Uso

### 1. Obtener información del grafo
```bash
curl http://localhost:8080/graph/info
```

### 2. Obtener todos los nodos
```bash
curl http://localhost:8080/graph/nodes
# Respuesta: ["The Matrix", "Keanu Reeves", "Carrie-Anne Moss", ...]
```

### 3. Obtener solo películas
```bash
curl http://localhost:8080/graph/nodes/type/MOVIE
# Respuesta: ["The Matrix", "Inception", "Interstellar", ...]
```

### 4. Realizar DFS desde una película
```bash
curl http://localhost:8080/graph/dfs/The%20Matrix
# Respuesta: ["The Matrix", "Keanu Reeves", "Carrie-Anne Moss", ...]
```

### 5. Realizar BFS desde una persona
```bash
curl http://localhost:8080/graph/bfs/Keanu%20Reeves
# Respuesta: ["Keanu Reeves", "The Matrix", "John Wick", ...]
```

### 6. Encontrar camino más corto
```bash
curl http://localhost:8080/graph/shortest-path/Keanu%20Reeves/The%20Matrix
# Respuesta: ["Keanu Reeves", "The Matrix"]
```

### 7. Comparar DFS vs BFS
```bash
curl http://localhost:8080/graph/compare-traversals/The%20Matrix
# Respuesta: {"startNode": "The Matrix", "dfs": [...], "bfs": [...], "dfsCount": 5, "bfsCount": 5}
```

## Características Técnicas

### Complejidad Temporal
- **DFS**: O(V + E) donde V = vértices, E = aristas
- **BFS**: O(V + E)
- **Camino más corto**: O(V + E)

### Complejidad Espacial
- **DFS recursivo**: O(V) por la pila de llamadas
- **DFS iterativo**: O(V) por la pila explícita
- **BFS**: O(V) por la cola

### Estructura de Datos
- **Lista de adyacencia**: `Map<String, List<String>>` - Eficiente para grafos dispersos
- **Acceso por ID**: O(1) para encontrar nodos usando HashMap
- **Inserción de aristas**: O(1)
- **Tipos de nodos**: `Map<String, String>` para identificar si es MOVIE o PERSON

## Ventajas del Diseño Simplificado

1. **Simplicidad**: Solo usa String IDs - mucho más fácil de entender y usar
2. **Eficiencia**: Lista de adyacencia optimizada con HashMap
3. **Flexibilidad**: Maneja tanto películas como personas usando solo sus nombres/títulos
4. **Completitud**: Implementa DFS, BFS y camino más corto
5. **API REST**: Fácil integración y testing
6. **Programación Reactiva**: Compatible con Spring WebFlux
7. **Menos código**: Eliminamos clases innecesarias como GraphNode y GraphEdge

## Casos de Uso

1. **Análisis de redes**: Encontrar conexiones entre actores y películas
2. **Recomendaciones**: Encontrar películas similares por actores/directores
3. **Búsqueda de caminos**: Encontrar la relación más corta entre dos entidades
4. **Análisis de grafos**: Estudiar la estructura de la base de datos de películas
