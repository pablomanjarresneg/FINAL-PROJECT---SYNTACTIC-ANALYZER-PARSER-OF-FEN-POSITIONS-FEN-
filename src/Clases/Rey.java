package Clases;
import java.awt.Image;
    
public class Rey extends Ficha {

    public Rey(String color, String tipo, Image icono) {
        super(color, tipo, icono);
    }

    @Override
    public boolean movimientoValido(int filaOrigen, int colOrigen, int filaDestino, int colDestino, Ficha[][] tablero) {
        int diffFila = Math.abs(filaDestino - filaOrigen);
        int diffCol = Math.abs(colDestino - colOrigen);

        if (diffFila <= 1 && diffCol <= 1) {
            Ficha destino = tablero[filaDestino][colDestino];
            boolean movimientoBasicoValido = destino == null || !destino.getColor().equals(this.getColor());

            // Ensure the king does not move into check
            if (movimientoBasicoValido && !estaEnJaque(filaDestino, colDestino, tablero)) {
                return true;
            }
        }

        return false;
    }

    private boolean estaEnJaque(int fila, int columna, Ficha[][] tablero) {
        // Implement logic to check if the king is in check at the given position
        // This method should consider all possible threats to the king
        return false; // Placeholder implementation
    }
}