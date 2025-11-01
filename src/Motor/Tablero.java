package Motor;

import Clases.Alfil;
import Clases.Caballo;
import Clases.Ficha;
import Clases.Peon;
import Clases.Reina;
import Clases.Rey;
import Clases.Torre;

public class Tablero {

    private Ficha[][] tablero; // matriz que representa las 64 casillas del tablero

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
            tablero[6][col] = new Peon("blanco", "peon", '♙');
        }

        // Peones negros
        for (int col = 0; col < 8; col++) {
            tablero[1][col] = new Peon("negro", "peon", '♟');
        }

        // Torres
        tablero[7][0] = new Torre("blanco", "torre", '♖');
        tablero[7][7] = new Torre("blanco", "torre", '♖');
        tablero[0][0] = new Torre("negro", "torre", '♜');
        tablero[0][7] = new Torre("negro", "torre", '♜');

        // Caballos
        tablero[7][1] = new Caballo("blanco", "caballo", '♘');
        tablero[7][6] = new Caballo("blanco", "caballo", '♘');
        tablero[0][1] = new Caballo("negro", "caballo", '♞');
        tablero[0][6] = new Caballo("negro", "caballo", '♞');

        // Alfiles
        tablero[7][2] = new Alfil("blanco", "alfil", '♗');
        tablero[7][5] = new Alfil("blanco", "alfil", '♗');
        tablero[0][2] = new Alfil("negro", "alfil", '♝');
        tablero[0][5] = new Alfil("negro", "alfil", '♝');

        // Reina y Rey
        tablero[7][3] = new Reina("blanco", "reina", '♕');
        tablero[7][4] = new Rey("blanco", "rey", '♔');
        tablero[0][3] = new Reina("negro", "reina", '♛');
        tablero[0][4] = new Rey("negro", "rey", '♚');
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

    // ---------------------------------------------------------
    // Muestra el tablero por consola
    // ---------------------------------------------------------
    public void mostrarTablero() {
        System.out.println("   a  b  c  d  e  f  g  h");
        for (int fila = 0; fila < 8; fila++) {
            System.out.print((8 - fila) + " ");
            for (int col = 0; col < 8; col++) {
                if (tablero[fila][col] == null) {
                    System.out.print(" . ");
                } else {
                    System.out.print(" " + tablero[fila][col].getSimbolo() + " ");
                }
            }
            System.out.println(" " + (8 - fila));
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }
}
