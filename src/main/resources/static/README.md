# 🎬 Visualización del Grafo de Películas

Esta aplicación web permite visualizar y explorar el grafo de películas, actores y directores usando **Cytoscape.js**.

## 🚀 Cómo usar

1. **Iniciar la aplicación Spring Boot**:
   ```bash
   mvn spring-boot:run
   ```

2. **Abrir la visualización**:
   - Navega a: `http://localhost:8080/graph-visualization.html`

## 🎯 Funcionalidades

### 📊 Visualización del Grafo
- **Nodos rojos**: Películas
- **Nodos azules**: Personas (actores/directores)
- **Aristas**: Relaciones entre nodos (ACTED_IN, DIRECTED, etc.)

### 🔧 Controles Disponibles

#### Layouts
- **Cose Bilkent**: Layout automático optimizado
- **Círculo**: Nodos organizados en círculo
- **Grid**: Organización en cuadrícula
- **Breadth First**: Basado en BFS
- **Concéntrico**: Nodos en círculos concéntricos

#### Algoritmos de Grafos
- **DFS (Depth-First Search)**: Recorrido en profundidad
- **BFS (Breadth-First Search)**: Recorrido en anchura
- **Camino más corto**: Encuentra la ruta más corta entre dos nodos

### 🖱️ Interacciones

- **Click en nodo**: Muestra información detallada del nodo
- **Zoom**: Usa la rueda del mouse o gestos de pellizco
- **Pan**: Arrastra para mover el grafo
- **Selección**: Click en nodos para seleccionarlos

### 📈 Panel de Información

El panel lateral muestra:
- **Estadísticas del grafo**: Número de nodos y aristas
- **Información de nodos**: Detalles al hacer click
- **Resultados de algoritmos**: Caminos encontrados por DFS/BFS
- **Leyenda**: Explicación de colores y tipos

## 🔗 Endpoints de la API

La aplicación utiliza estos endpoints REST:

- `GET /graph/cytoscape-data` - Datos del grafo en formato JSON
- `GET /graph/dfs/{nodeId}` - Ejecutar DFS desde un nodo
- `GET /graph/bfs/{nodeId}` - Ejecutar BFS desde un nodo
- `GET /graph/shortest-path/{start}/{end}` - Camino más corto
- `GET /graph/nodes/{nodeId}/info` - Información de un nodo
- `GET /graph/nodes/{nodeId}/neighbors` - Vecinos de un nodo

## 🎨 Características Técnicas

- **Cytoscape.js**: Biblioteca de visualización de grafos
- **Responsive Design**: Se adapta a diferentes tamaños de pantalla
- **Animaciones**: Transiciones suaves entre layouts
- **Interactividad**: Zoom, pan, selección de nodos
- **Estilos modernos**: Diseño atractivo con gradientes y efectos

## 🛠️ Personalización

Puedes modificar los estilos en el archivo HTML:
- **Colores de nodos**: Cambia los colores en la sección CSS
- **Layouts**: Agrega nuevos layouts de Cytoscape
- **Estilos**: Modifica la apariencia visual

## 📝 Notas

- Asegúrate de que Neo4j esté ejecutándose y tenga datos
- La aplicación se conecta automáticamente a la base de datos
- Los algoritmos se ejecutan en tiempo real sobre los datos de Neo4j
