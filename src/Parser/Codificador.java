package Parser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Codificador {
    public List<String> movimientos;
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
        String notacionCoordenadasCompletas = "^[a-h][1-8][a-h][1-8]$";// e2e4 (desde-hasta)
        String notacionCoordenadaDestino = "^[a-h][1-8]$";// e4 (solo destino)
        
        // Limpiar espacios en blanco
        movimiento = movimiento.replaceAll("\\s+", "");
        
        // Verificar si el movimiento coincide con algún patrón válido
        return movimiento.matches(notacionCoordenadasCompletas) || 
               movimiento.matches(notacionCoordenadaDestino);
    }


    public ArrayList<String> cargarDesdeFEN(String fen) {
        ArrayList<String> filas = new ArrayList<>();

        // Extraer solo la parte de posición de piezas (antes del primer espacio)
        String piezaPlacement = fen.split(" ")[0];

        // Patrón para validar formato de fila FEN: solo dígitos (1-8) y piezas válidas
        Pattern patronFila = Pattern.compile("^[prnbqkPRNBQK1-8]+$");

        // Dividir por '/' para obtener cada fila
        String[] filasFEN = piezaPlacement.split("/");

        for (String filaFEN : filasFEN) {
            // Validar formato de la fila
            if (!patronFila.matcher(filaFEN).matches()) {
                System.err.println("Error: Formato inválido en fila FEN: " + filaFEN);
                continue;
            }

            // Expandir dígitos a espacios usando expresiones regulares
            // Reemplaza cada dígito por el número correspondiente de espacios
            String filaExpandida = filaFEN;
            for (int i = 1; i <= 8; i++) {
                filaExpandida = filaExpandida.replaceAll(String.valueOf(i), " ".repeat(i));
            }

            // Verificar que la fila tenga exactamente 8 caracteres
            if (filaExpandida.length() == 8) {
                filas.add(filaExpandida);
            } else {
                System.err.println("Error: Fila no tiene 8 caracteres: [" + filaExpandida + "] (longitud: " + filaExpandida.length() + ")");
            }
        }

        System.out.println("Filas cargadas desde FEN:");
        for (int i = 0; i < filas.size(); i++) {
            System.out.println("Fila " + (i + 1) + ": [" + filas.get(i) + "]");
        }

        return filas;
    }


    public boolean validarFEN(String fen) {
        // Extraer solo la parte de posición de piezas (antes del primer espacio)
        String piezaPlacement = fen.split(" ")[0];

        // Patrón completo para validar formato FEN: 8 filas separadas por '/'
        // Cada fila debe contener solo piezas válidas (p,r,n,b,q,k mayúsculas/minúsculas) o dígitos (1-8)
        Pattern patronFENCompleto = Pattern.compile("^([prnbqkPRNBQK1-8]+/){7}[prnbqkPRNBQK1-8]+$");

        // Validar formato general
        if (!patronFENCompleto.matcher(piezaPlacement).matches()) {
            return false;
        }

        // Validar que cada fila sume exactamente 8 casillas
        String[] filas = piezaPlacement.split("/");
        Pattern patronDigito = Pattern.compile("\\d");

        for (String fila : filas) {
            int suma = 0;
            Matcher matcher = patronDigito.matcher(fila);

            // Calcular suma expandiendo dígitos
            String filaExpandida = fila;
            while (matcher.find()) {
                int digito = Integer.parseInt(matcher.group());
                suma += digito;
            }

            // Contar piezas (caracteres que no son dígitos)
            suma += fila.replaceAll("\\d", "").length();

            if (suma != 8) {
                return false;
            }
        }

        return true;
    }

    public void registrarMovimiento(String movimiento) {
        if (validarBNF(movimiento)) {
            movimientos.add(movimiento);
        }
    }
    public boolean archivoExiste(String nombreArchivo) {
        java.io.File archivo = new java.io.File(nombreArchivo);
        return archivo.exists();
    }
    public void guardarPartidaBNF(String nombreArchivo) {
        if (!archivoExiste(nombreArchivo)) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("# Notación BNF de la partida\n\n");
            writer.write("<partida> ::= <posicion_inicial> <secuencia_movimientos>\n");
            writer.write("<posicion_inicial> ::= \"" + FEN_INICIAL + "\"\n");
            writer.write("<secuencia_movimientos> ::= ");

            String[] movimientosArray = movimientos.toArray(String[]::new);

            for (int i = 0; i < movimientosArray.length; i++) {
                writer.write("<movimiento" + (i+1) + ">");
                if (i < movimientosArray.length - 1) {
                    writer.write(" <movimiento" + (i+2) + ">");
                }
            }
            writer.write("\n\n");

            // Definir cada movimiento individual
            for (int i = 0; i < movimientosArray.length; i++) {
                writer.write("<movimiento" + (i+1) + "> ::= \"" + movimientosArray[i] + "\"\n");
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
        } else {
            System.out.println("El archivo " + nombreArchivo + " ya existe. No se sobrescribirá.");
        }
    }

    public ArrayList<String> cargarPartidaBNF(String nombreArchivo) {
        ArrayList<String> movimientosCargados = new ArrayList<>();
        try {
            java.io.File archivo = new java.io.File(nombreArchivo);
            try (java.util.Scanner scanner = new java.util.Scanner(archivo)) {
                boolean enSeccionMovimientos = false;
                while (scanner.hasNextLine()) {
                    String linea = scanner.nextLine().trim();
                    if (linea.startsWith("<secuencia_movimientos>")) {
                        enSeccionMovimientos = true;
                        continue;
                    }
                    if (enSeccionMovimientos) {
                        if (linea.isEmpty() || linea.startsWith("#")) {
                            continue; 
                        }
                        if (linea.startsWith("<movimiento")) {
                            String[] partes = linea.split("::=");
                            if (partes.length == 2) {
                                String movimiento = partes[1].trim().replaceAll("\"", "");
                                movimientosCargados.add(movimiento);
                            }
                        } else {
                            // Salir de la sección de movimientos si encontramos otra definición
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la partida: " + e.getMessage());
        }

        return movimientosCargados;
    }

    public List<String> getMovimientos() {
        return new ArrayList<>(movimientos);
    }
    public void limpiarMovimientos() {
        movimientos.clear();
    }
}
