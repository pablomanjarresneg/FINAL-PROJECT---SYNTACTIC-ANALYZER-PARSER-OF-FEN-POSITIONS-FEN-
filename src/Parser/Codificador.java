package Parser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Codificador {
    private List<String> movimientos;
    private static final String FEN_INICIAL = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    
    public Codificador() {
        this.movimientos = new ArrayList<>();
    }

    public boolean validarBNF(String movimiento) {
        // Formato BNF para movimientos de ajedrez:
        // <movimiento> ::= <posicion><posicion>
        // <posicion> ::= <columna><fila>
        // <columna> ::= [a-h]
        // <fila> ::= [1-8]
        String notacionCoordenadasCompletas = "^[a-h][1-8][a-h][1-8]$";                        // e2e4 (desde-hasta)
        String notacionCoordenadaDestino = "^[a-h][1-8]$";                                      // e4 (solo destino)
        
        // Limpiar espacios en blanco
        movimiento = movimiento.replaceAll("\\s+", "");
        
        // Verificar si el movimiento coincide con algún patrón válido
        return movimiento.matches(notacionCoordenadasCompletas) || 
               movimiento.matches(notacionCoordenadaDestino);
    }

    public boolean validarFEN(String fen) {
        // Validación básica de formato FEN
        String[] partes = fen.split(" ");
        if (partes.length != 6) return false;

        // Validar estructura del tablero
        String[] filas = partes[0].split("/");
        if (filas.length != 8) return false;

        // Validar cada fila
        for (String fila : filas) {
            int suma = 0;
            for (char c : fila.toCharArray()) {
                if (Character.isDigit(c)) {
                    suma += Character.getNumericValue(c);
                } else if ("prnbqkPRNBQK".indexOf(c) != -1) {
                    suma++;
                } else {
                    return false;
                }
            }
            if (suma != 8) return false;
        }

        return true;
    }

    public void registrarMovimiento(String movimiento) {
        if (validarBNF(movimiento)) {
            movimientos.add(movimiento);
        }
    }

    public void guardarPartidaBNF(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("# Notación BNF de la partida\n\n");
            writer.write("<partida> ::= <posicion_inicial> <secuencia_movimientos>\n");
            writer.write("<posicion_inicial> ::= \"" + FEN_INICIAL + "\"\n");
            writer.write("<secuencia_movimientos> ::= ");
            
            for (int i = 0; i < movimientos.size(); i++) {
                writer.write("<movimiento" + (i+1) + ">");
                if (i < movimientos.size() - 1) {
                    writer.write(" ");
                }
            }
            writer.write("\n\n");

            // Definir cada movimiento individual
            for (int i = 0; i < movimientos.size(); i++) {
                writer.write("<movimiento" + (i+1) + "> ::= \"" + movimientos.get(i) + "\"\n");
            }

            // Agregar definiciones base
            writer.write("\n# Definiciones base\n");
            writer.write("<columna> ::= \"a\" | \"b\" | \"c\" | \"d\" | \"e\" | \"f\" | \"g\" | \"h\"\n");
            writer.write("<fila> ::= \"1\" | \"2\" | \"3\" | \"4\" | \"5\" | \"6\" | \"7\" | \"8\"\n");
            writer.write("<posicion> ::= <columna><fila>\n");
            writer.write("<movimiento> ::= <posicion> <posicion>\n");
        } catch (IOException e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
    }

    public String[] obtenerMovimientos() {
        return movimientos.toArray(new String[0]);
    }

    public void limpiarMovimientos() {
        movimientos.clear();
    }
}
