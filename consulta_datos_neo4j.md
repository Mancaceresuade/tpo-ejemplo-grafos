# Datos para Neo4j - Guía de Carga

## 🎯 **Dos opciones de datos:**

### **Opción 1: Datos Completos** (`datos_neo4j.cypher`)
- **9 películas** (The Matrix trilogy, Inception, Interstellar, John Wick, Speed, Point Break)
- **17 personas** (actores, directores, guionistas)
- **Múltiples relaciones** (ACTED_IN, DIRECTED, WROTE)
- **Grafo más complejo** para pruebas avanzadas

### **Opción 2: Datos Simples** (`datos_neo4j_simple.cypher`)
- **4 películas** (The Matrix, Inception, John Wick, Speed)
- **6 personas** (Keanu Reeves, Carrie-Anne Moss, Leonardo DiCaprio, Christopher Nolan, Sandra Bullock, Kathryn Bigelow)
- **Relaciones básicas** (ACTED_IN, DIRECTED)
- **Grafo simple** para pruebas rápidas

## 🚀 **Cómo cargar los datos:**

### **Método 1: Navegador de Neo4j**
1. Abrir navegador: http://localhost:7474
2. Usuario: `neo4j`, Contraseña: `neo4jadmin`
3. Copiar y pegar el contenido del archivo `.cypher`
4. Ejecutar con `Ctrl+Enter`

### **Método 2: Neo4j Desktop**
1. Abrir Neo4j Desktop
2. Conectar a tu base de datos
3. Abrir el navegador
4. Ejecutar el script Cypher

### **Método 3: Neo4j Browser CLI**
```bash
# Si tienes cypher-shell instalado
cypher-shell -u neo4j -p neo4jadmin < datos_neo4j_simple.cypher
```

## 📊 **Datos que se crearán:**

### **Películas:**
- The Matrix (1999)
- Inception (2010)
- John Wick (2014)
- Speed (1994)

### **Personas:**
- **Keanu Reeves** (1964) - Actor en The Matrix, John Wick, Speed
- **Carrie-Anne Moss** (1967) - Actriz en The Matrix
- **Leonardo DiCaprio** (1974) - Actor en Inception
- **Christopher Nolan** (1970) - Director de Inception
- **Sandra Bullock** (1964) - Actriz en Speed
- **Kathryn Bigelow** (1951) - Directora de Speed

### **Relaciones:**
- **ACTED_IN**: Persona → Película (con rol específico)
- **DIRECTED**: Director → Película

## 🔍 **Consultas útiles para verificar:**

```cypher
// Ver todas las películas
MATCH (m:Movie) RETURN m.title, m.tagline

// Ver todas las personas
MATCH (p:Person) RETURN p.name, p.born

// Ver relaciones ACTED_IN
MATCH (p:Person)-[r:ACTED_IN]->(m:Movie) 
RETURN p.name, r.role, m.title

// Ver relaciones DIRECTED
MATCH (p:Person)-[:DIRECTED]->(m:Movie) 
RETURN p.name, m.title

// Ver el grafo completo
MATCH (n) RETURN n
```

## 🎮 **Después de cargar, probar el grafo:**

Una vez cargados los datos, puedes probar tu aplicación Spring Boot:

```bash
# Información del grafo
curl http://localhost:8080/graph/info

# Todos los nodos
curl http://localhost:8080/graph/nodes

# DFS desde "The Matrix"
curl http://localhost:8080/graph/dfs/The%20Matrix

# BFS desde "Keanu Reeves"
curl http://localhost:8080/graph/bfs/Keanu%20Reeves

# Camino más corto
curl http://localhost:8080/graph/shortest-path/Keanu%20Reeves/Inception
```

## 💡 **Recomendación:**

Empieza con **`datos_neo4j_simple.cypher`** para probar rápidamente, y luego usa **`datos_neo4j.cypher`** para un grafo más complejo y pruebas más avanzadas.
