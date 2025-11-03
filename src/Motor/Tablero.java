package Motor;

import Clases.Alfil;
import Clases.Caballo;
import Clases.Ficha;
import Clases.Peon;
import Clases.Reina;
import Clases.Rey;
import Clases.Torre;
import javax.swing.ImageIcon;

public class Tablero {

    private final Ficha[][] tablero; // matriz que representa las 64 casillas del tablero

    public Tablero() {
        tablero = new Ficha[8][8];
        inicializarTablero();
    }

    public Ficha[][] getTablero() {
        return tablero;
    }

    // ---------------------------------------------------------
    // Inicializa las piezas en sus posiciones estándar de ajedrez
    // ---------------------------------------------------------
    private void inicializarTablero() {
        // Peones blancos
        for (int col = 0; col < 8; col++) {
            tablero[6][col] = new Peon("blanco", "peon", new ImageIcon("pixel chess_v1.2/16x32 pieces/W_Pawn.png").getImage());
        }

        // Peones negros
        for (int col = 0; col < 8; col++) {
            tablero[1][col] = new Peon("negro", "peon", new ImageIcon("pixel chess_v1.2/16x32 pieces/B_Pawn.png").getImage());
        }

        // Torres
        tablero[7][0] = new Torre("blanco", "torre", new ImageIcon("pixel chess_v1.2/16x32 pieces/W_Rook.png").getImage());
        tablero[7][7] = new Torre("blanco", "torre", new ImageIcon("pixel chess_v1.2/16x32 pieces/W_Rook.png").getImage());
        tablero[0][0] = new Torre("negro", "torre", new ImageIcon("pixel chess_v1.2/16x32 pieces/B_Rook.png").getImage());
        tablero[0][7] = new Torre("negro", "torre", new ImageIcon("pixel chess_v1.2/16x32 pieces/B_Rook.png").getImage());

        // Caballos
        tablero[7][1] = new Caballo("blanco", "caballo", new ImageIcon("pixel chess_v1.2/16x32 pieces/W_Knight.png").getImage());
        tablero[7][6] = new Caballo("blanco", "caballo", new ImageIcon("pixel chess_v1.2/16x32 pieces/W_Knight.png").getImage());
        tablero[0][1] = new Caballo("negro", "caballo", new ImageIcon("pixel chess_v1.2/16x32 pieces/B_Knight.png").getImage());
        tablero[0][6] = new Caballo("negro", "caballo", new ImageIcon("pixel chess_v1.2/16x32 pieces/B_Knight.png").getImage());

        // Alfiles
        tablero[7][2] = new Alfil("blanco", "alfil", new ImageIcon("pixel chess_v1.2/16x32 pieces/W_Bishop.png").getImage());
        tablero[7][5] = new Alfil("blanco", "alfil", new ImageIcon("pixel chess_v1.2/16x32 pieces/W_Bishop.png").getImage());
        tablero[0][2] = new Alfil("negro", "alfil", new ImageIcon("pixel chess_v1.2/16x32 pieces/B_Bishop.png").getImage());
        tablero[0][5] = new Alfil("negro", "alfil", new ImageIcon("pixel chess_v1.2/16x32 pieces/B_Bishop.png").getImage());

        // Reina y Rey
        tablero[7][3] = new Reina("blanco", "reina", new ImageIcon("pixel chess_v1.2/16x32 pieces/W_Queen.png").getImage());
        tablero[7][4] = new Rey("blanco", "rey", new ImageIcon("pixel chess_v1.2/16x32 pieces/W_King.png").getImage());
        tablero[0][3] = new Reina("negro", "reina", new ImageIcon("pixel chess_v1.2/16x32 pieces/B_Queen.png").getImage());
        tablero[0][4] = new Rey("negro", "rey", new ImageIcon("pixel chess_v1.2/16x32 pieces/B_King.png").getImage());
    }

    // ---------------------------------------------------------
    // Convierte una posición tipo "e2" a índices de matriz
    // ---------------------------------------------------------
    private int[] convertirPos(String pos) {
        pos = pos.toLowerCase();

        char columnaLetra = pos.charAt(0);   // 'a'..'h'
        int filaNumero = Character.getNumericValue(pos.charAt(1)); // 1..8

        int fila = 8 - filaNumero; // en la matriz, 0 está arriba y 7 abajo
        int col = columnaLetra - 'a';

        return new int[]{fila, col};
    }
    // ---------------------------------------------------------
    // Mueve una ficha si el movimiento es válido
    // ---------------------------------------------------------
    public boolean mover(String origen, String destino) {
        int[] o = convertirPos(origen);
        int[] d = convertirPos(destino);

        int filaOrigen = o[0], colOrigen = o[1];
        int filaDestino = d[0], colDestino = d[1];
 
        Ficha ficha = tablero[filaOrigen][colOrigen];

        if (ficha == null) {
            System.out.println("No hay ninguna ficha en " + origen + "\n");
            return false;
        }

        if (ficha.movimientoValido(filaOrigen, colOrigen, filaDestino, colDestino, tablero)) {
            System.out.println("Moviendo " + ficha.getTipo() + " de " + origen + " a " + destino);
            tablero[filaDestino][colDestino] = ficha;
            tablero[filaOrigen][colOrigen] = null;
            ficha.setPosicion(destino);
            return true;
        } else {
            System.out.println("Movimiento inválido para " + ficha.getTipo() + " en " + origen);
            return false;
        }
    }
    public boolean esTurnoCorrecto(String origen, boolean esBlanca) {
        int[] o = convertirPos(origen);
        int filaOrigen = o[0], colOrigen = o[1];
        Ficha ficha = tablero[filaOrigen][colOrigen];
        if (ficha == null) {
            return false;
        }
        return (esBlanca && ficha.getColor().equals("blanco")) || (!esBlanca && ficha.getColor().equals("negro"));
    }
    public boolean hayRey(String color) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                Ficha ficha = tablero[fila][col];
                if (ficha != null && ficha.getTipo().equals("rey") && ficha.getColor().equals(color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[] encontrarRey(String color) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                Ficha ficha = tablero[fila][col];
                if (ficha != null && ficha.getTipo().equals("rey") && ficha.getColor().equals(color)) {
                    return new int[]{fila, col};
                }
            }
        }
        return null; // Rey no encontrado
    }

    private boolean tieneMovimientosLegales(String color) {
        for (int filaOrigen = 0; filaOrigen < 8; filaOrigen++) {
            for (int colOrigen = 0; colOrigen < 8; colOrigen++) {
                Ficha ficha = tablero[filaOrigen][colOrigen];
                if (ficha != null && ficha.getColor().equals(color)) {
                    for (int filaDestino = 0; filaDestino < 8; filaDestino++) {
                        for (int colDestino = 0; colDestino < 8; colDestino++) {
                            if (ficha.movimientoValido(filaOrigen, colOrigen, filaDestino, colDestino, tablero)) {
                                if (filaOrigen == filaDestino && colOrigen == colDestino) {
                                    continue;
                                }
                            
                                // Check if this move is valid for the piece
                                if (ficha.movimientoValido(filaOrigen, colOrigen, filaDestino, colDestino, tablero)) {
                                    Ficha fichaCapturada = tablero[filaDestino][colDestino];
                                    tablero[filaDestino][colDestino] = ficha;
                                    tablero[filaOrigen][colOrigen] = null;
                                    
                                    // Check if the king is still in check after this move
                                    boolean enJaque = esJaque(color);
                                    
                                    // Undo the move
                                    tablero[filaOrigen][colOrigen] = ficha;
                                    tablero[filaDestino][colDestino] = fichaCapturada;

                                    if (!enJaque) {
                                        return true; // Hay al menos un movimiento legal
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false; // No hay movimientos legales
    }

    public boolean esJaque(String color) {
        int[] posicionRey = encontrarRey(color);
        if (!hayRey(color)) {
            return false; // El rey no está en el tablero
        }
        int filaRey = posicionRey[0];
        int colRey = posicionRey[1];
        String colorOponente = color.equals("blanco") ? "negro" : "blanco";

        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                Ficha ficha = tablero[fila][col];
                if (ficha != null && ficha.getColor().equals(colorOponente) && ficha.movimientoValido(fila, col, filaRey, colRey, tablero)) {
                    return true; 
                }
            }
        }
        return false;
    }

    public boolean esJaqueMate(String color) {
        if (!esJaque(color)) {
            return false; 
        }

        return !tieneMovimientosLegales(color);
    }
}
