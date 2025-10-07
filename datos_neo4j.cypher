// Script para cargar datos de ejemplo en Neo4j
// Ejecutar en el navegador de Neo4j (http://localhost:7474)

// Limpiar la base de datos existente
MATCH (n) DETACH DELETE n;

// Crear películas
CREATE (m1:Movie {title: "The Matrix", tagline: "Welcome to the Real World"})
CREATE (m2:Movie {title: "The Matrix Reloaded", tagline: "Free your mind"})
CREATE (m3:Movie {title: "The Matrix Revolutions", tagline: "Everything that has a beginning has an end"})
CREATE (m4:Movie {title: "Inception", tagline: "Your mind is the scene of the crime"})
CREATE (m5:Movie {title: "Interstellar", tagline: "Mankind was born on Earth. It was never meant to die here"})
CREATE (m6:Movie {title: "John Wick", tagline: "An ex-hit-man comes out of retirement"})
CREATE (m7:Movie {title: "John Wick: Chapter 2", tagline: "Never stab the devil in the back"})
CREATE (m8:Movie {title: "Speed", tagline: "Get ready for rush hour"})
CREATE (m9:Movie {title: "Point Break", tagline: "100% Pure Adrenaline"})

// Crear personas (actores y directores)
CREATE (p1:Person {name: "Keanu Reeves", born: 1964})
CREATE (p2:Person {name: "Carrie-Anne Moss", born: 1967})
CREATE (p3:Person {name: "Laurence Fishburne", born: 1961})
CREATE (p4:Person {name: "Hugo Weaving", born: 1960})
CREATE (p5:Person {name: "Lilly Wachowski", born: 1967})
CREATE (p6:Person {name: "Lana Wachowski", born: 1965})
CREATE (p7:Person {name: "Christopher Nolan", born: 1970})
CREATE (p8:Person {name: "Leonardo DiCaprio", born: 1974})
CREATE (p9:Person {name: "Marion Cotillard", born: 1975})
CREATE (p10:Person {name: "Matthew McConaughey", born: 1969})
CREATE (p11:Person {name: "Anne Hathaway", born: 1982})
CREATE (p12:Person {name: "Chad Stahelski", born: 1968})
CREATE (p13:Person {name: "Derek Kolstad", born: 1974})
CREATE (p14:Person {name: "Sandra Bullock", born: 1964})
CREATE (p15:Person {name: "Dennis Hopper", born: 1936})
CREATE (p16:Person {name: "Patrick Swayze", born: 1952})
CREATE (p17:Person {name: "Kathryn Bigelow", born: 1951})

// Crear relaciones ACTED_IN
MATCH (p1:Person {name: "Keanu Reeves"}), (m1:Movie {title: "The Matrix"})
CREATE (p1)-[:ACTED_IN {role: "Neo"}]->(m1)

MATCH (p2:Person {name: "Carrie-Anne Moss"}), (m1:Movie {title: "The Matrix"})
CREATE (p2)-[:ACTED_IN {role: "Trinity"}]->(m1)

MATCH (p3:Person {name: "Laurence Fishburne"}), (m1:Movie {title: "The Matrix"})
CREATE (p3)-[:ACTED_IN {role: "Morpheus"}]->(m1)

MATCH (p4:Person {name: "Hugo Weaving"}), (m1:Movie {title: "The Matrix"})
CREATE (p4)-[:ACTED_IN {role: "Agent Smith"}]->(m1)

MATCH (p1:Person {name: "Keanu Reeves"}), (m2:Movie {title: "The Matrix Reloaded"})
CREATE (p1)-[:ACTED_IN {role: "Neo"}]->(m2)

MATCH (p2:Person {name: "Carrie-Anne Moss"}), (m2:Movie {title: "The Matrix Reloaded"})
CREATE (p2)-[:ACTED_IN {role: "Trinity"}]->(m2)

MATCH (p3:Person {name: "Laurence Fishburne"}), (m2:Movie {title: "The Matrix Reloaded"})
CREATE (p3)-[:ACTED_IN {role: "Morpheus"}]->(m2)

MATCH (p1:Person {name: "Keanu Reeves"}), (m3:Movie {title: "The Matrix Revolutions"})
CREATE (p1)-[:ACTED_IN {role: "Neo"}]->(m3)

MATCH (p2:Person {name: "Carrie-Anne Moss"}), (m3:Movie {title: "The Matrix Revolutions"})
CREATE (p2)-[:ACTED_IN {role: "Trinity"}]->(m3)

MATCH (p8:Person {name: "Leonardo DiCaprio"}), (m4:Movie {title: "Inception"})
CREATE (p8)-[:ACTED_IN {role: "Dom Cobb"}]->(m4)

MATCH (p9:Person {name: "Marion Cotillard"}), (m4:Movie {title: "Inception"})
CREATE (p9)-[:ACTED_IN {role: "Mal"}]->(m4)

MATCH (p10:Person {name: "Matthew McConaughey"}), (m5:Movie {title: "Interstellar"})
CREATE (p10)-[:ACTED_IN {role: "Cooper"}]->(m5)

MATCH (p11:Person {name: "Anne Hathaway"}), (m5:Movie {title: "Interstellar"})
CREATE (p11)-[:ACTED_IN {role: "Brand"}]->(m5)

MATCH (p1:Person {name: "Keanu Reeves"}), (m6:Movie {title: "John Wick"})
CREATE (p1)-[:ACTED_IN {role: "John Wick"}]->(m6)

MATCH (p1:Person {name: "Keanu Reeves"}), (m7:Movie {title: "John Wick: Chapter 2"})
CREATE (p1)-[:ACTED_IN {role: "John Wick"}]->(m7)

MATCH (p1:Person {name: "Keanu Reeves"}), (m8:Movie {title: "Speed"})
CREATE (p1)-[:ACTED_IN {role: "Jack Traven"}]->(m8)

MATCH (p14:Person {name: "Sandra Bullock"}), (m8:Movie {title: "Speed"})
CREATE (p14)-[:ACTED_IN {role: "Annie Porter"}]->(m8)

MATCH (p15:Person {name: "Dennis Hopper"}), (m8:Movie {title: "Speed"})
CREATE (p15)-[:ACTED_IN {role: "Howard Payne"}]->(m8)

MATCH (p1:Person {name: "Keanu Reeves"}), (m9:Movie {title: "Point Break"})
CREATE (p1)-[:ACTED_IN {role: "Johnny Utah"}]->(m9)

MATCH (p16:Person {name: "Patrick Swayze"}), (m9:Movie {title: "Point Break"})
CREATE (p16)-[:ACTED_IN {role: "Bodhi"}]->(m9)

// Crear relaciones DIRECTED
MATCH (p5:Person {name: "Lilly Wachowski"}), (m1:Movie {title: "The Matrix"})
CREATE (p5)-[:DIRECTED]->(m1)

MATCH (p6:Person {name: "Lana Wachowski"}), (m1:Movie {title: "The Matrix"})
CREATE (p6)-[:DIRECTED]->(m1)

MATCH (p5:Person {name: "Lilly Wachowski"}), (m2:Movie {title: "The Matrix Reloaded"})
CREATE (p5)-[:DIRECTED]->(m2)

MATCH (p6:Person {name: "Lana Wachowski"}), (m2:Movie {title: "The Matrix Reloaded"})
CREATE (p6)-[:DIRECTED]->(m2)

MATCH (p5:Person {name: "Lilly Wachowski"}), (m3:Movie {title: "The Matrix Revolutions"})
CREATE (p5)-[:DIRECTED]->(m3)

MATCH (p6:Person {name: "Lana Wachowski"}), (m3:Movie {title: "The Matrix Revolutions"})
CREATE (p6)-[:DIRECTED]->(m3)

MATCH (p7:Person {name: "Christopher Nolan"}), (m4:Movie {title: "Inception"})
CREATE (p7)-[:DIRECTED]->(m4)

MATCH (p7:Person {name: "Christopher Nolan"}), (m5:Movie {title: "Interstellar"})
CREATE (p7)-[:DIRECTED]->(m5)

MATCH (p12:Person {name: "Chad Stahelski"}), (m6:Movie {title: "John Wick"})
CREATE (p12)-[:DIRECTED]->(m6)

MATCH (p12:Person {name: "Chad Stahelski"}), (m7:Movie {title: "John Wick: Chapter 2"})
CREATE (p12)-[:DIRECTED]->(m7)

MATCH (p17:Person {name: "Kathryn Bigelow"}), (m8:Movie {title: "Speed"})
CREATE (p17)-[:DIRECTED]->(m8)

MATCH (p17:Person {name: "Kathryn Bigelow"}), (m9:Movie {title: "Point Break"})
CREATE (p17)-[:DIRECTED]->(m9)

// Crear relaciones WROTE (guionistas)
MATCH (p13:Person {name: "Derek Kolstad"}), (m6:Movie {title: "John Wick"})
CREATE (p13)-[:WROTE]->(m6)

MATCH (p13:Person {name: "Derek Kolstad"}), (m7:Movie {title: "John Wick: Chapter 2"})
CREATE (p13)-[:WROTE]->(m7)

// Verificar los datos creados
MATCH (n) RETURN n LIMIT 50;
