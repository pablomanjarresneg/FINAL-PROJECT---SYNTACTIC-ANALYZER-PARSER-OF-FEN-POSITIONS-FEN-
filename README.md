# FINAL PROJECT - Analizador Sintáctico (Parser) de posiciones FEN

Resumen
-------
Proyecto final: analizador sintáctico para posiciones en notación FEN (Forsyth–Edwards Notation).
Valida la sintaxis de cadenas FEN, construye una representación interna (AST/objeto) y reporta errores con mensajes claros.

Características
---------------
- Validación completa de la notación FEN (tablero, turno, enroques, captura al paso, contador medio-movimiento y número de jugadas).
- Construcción de una representación de la posición (clases/estructuras que representan tablero, piezas y metadatos).
- Interfaz de línea de comandos (CLI) para validar y mostrar la posición.
- API simple para integración en otros proyectos.
- Conjunto de tests unitarios para casos correctos y malformados.

Requisitos
----------
- JDK 11+ (implementación principal en Java)
- Maven o Gradle para build y tests
- (Opcional) IDE (IntelliJ IDEA, VSCode)

Instalación y compilación
-------------------------
Con Maven:
1. mvn clean package
2. El jar resultante estará en target/, p. ej. target/fen-parser.jar

Con Gradle:
1. ./gradlew build
2. El jar estará en build/libs/

Ejecución (CLI)
---------------
Validar una FEN desde consola:
- java -jar target/fen-parser.jar "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

Salida esperada (ejemplo):
- "Válida" o "Inválida: error en campo X (mensaje)"
- O bien, representación JSON de la posición si se pasa la opción --json

Uso como librería (ejemplo Java)
--------------------------------