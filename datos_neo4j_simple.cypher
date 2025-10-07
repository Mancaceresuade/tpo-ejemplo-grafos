// Script más simple para cargar datos básicos en Neo4j
// Ejecutar en el navegador de Neo4j (http://localhost:7474)

// Limpiar la base de datos existente
MATCH (n) DETACH DELETE n;

// Crear películas
CREATE (m1:Movie {title: "The Matrix", tagline: "Welcome to the Real World"})
CREATE (m2:Movie {title: "Inception", tagline: "Your mind is the scene of the crime"})
CREATE (m3:Movie {title: "John Wick", tagline: "An ex-hit-man comes out of retirement"})
CREATE (m4:Movie {title: "Speed", tagline: "Get ready for rush hour"})

// Crear personas
CREATE (p1:Person {name: "Keanu Reeves", born: 1964})
CREATE (p2:Person {name: "Carrie-Anne Moss", born: 1967})
CREATE (p3:Person {name: "Leonardo DiCaprio", born: 1974})
CREATE (p4:Person {name: "Christopher Nolan", born: 1970})
CREATE (p5:Person {name: "Sandra Bullock", born: 1964})
CREATE (p6:Person {name: "Kathryn Bigelow", born: 1951})

// Relaciones ACTED_IN
MATCH (p1:Person {name: "Keanu Reeves"}), (m1:Movie {title: "The Matrix"})
CREATE (p1)-[:ACTED_IN {role: "Neo"}]->(m1)

MATCH (p2:Person {name: "Carrie-Anne Moss"}), (m1:Movie {title: "The Matrix"})
CREATE (p2)-[:ACTED_IN {role: "Trinity"}]->(m1)

MATCH (p3:Person {name: "Leonardo DiCaprio"}), (m2:Movie {title: "Inception"})
CREATE (p3)-[:ACTED_IN {role: "Dom Cobb"}]->(m2)

MATCH (p1:Person {name: "Keanu Reeves"}), (m3:Movie {title: "John Wick"})
CREATE (p1)-[:ACTED_IN {role: "John Wick"}]->(m3)

MATCH (p1:Person {name: "Keanu Reeves"}), (m4:Movie {title: "Speed"})
CREATE (p1)-[:ACTED_IN {role: "Jack Traven"}]->(m4)

MATCH (p5:Person {name: "Sandra Bullock"}), (m4:Movie {title: "Speed"})
CREATE (p5)-[:ACTED_IN {role: "Annie Porter"}]->(m4)

// Relaciones DIRECTED
MATCH (p4:Person {name: "Christopher Nolan"}), (m2:Movie {title: "Inception"})
CREATE (p4)-[:DIRECTED]->(m2)

MATCH (p6:Person {name: "Kathryn Bigelow"}), (m4:Movie {title: "Speed"})
CREATE (p6)-[:DIRECTED]->(m4)

// Verificar los datos
MATCH (n) RETURN n;
