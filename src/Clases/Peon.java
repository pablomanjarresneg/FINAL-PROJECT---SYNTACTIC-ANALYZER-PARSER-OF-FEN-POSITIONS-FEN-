package Clases;
import java.awt.Image;

public class Peon extends Ficha {

    public Peon(String color, String tipo, Image icono) {
        super(color, tipo, icono);
    }

    @Override
    public boolean movimientoValido(int filaOrigen, int colOrigen, int filaDestino, int colDestino, Ficha[][] tablero) {
        int direccion = this.getColor().equals("blanco") ? -1 : 1;
        int filaInicial = this.getColor().equals("blanco") ? 6 : 1;

        // Movimiento vertical
        if (colOrigen == colDestino) {
            if (filaDestino == filaOrigen + direccion && tablero[filaDestino][colDestino] == null) {
                return true;
            }
            if (filaOrigen == filaInicial && filaDestino == filaOrigen + 2 * direccion
                    && tablero[filaOrigen + direccion][colOrigen] == null
                    && tablero[filaDestino][colDestino] == null) {
                return true;
            }
        }

        // Comer en diagonal
        if (Math.abs(colDestino - colOrigen) == 1 && filaDestino == filaOrigen + direccion) {
            Ficha destino = tablero[filaDestino][colDestino];
            if (destino != null && !destino.getColor().equals(this.getColor())) {
                return true;
            }
        }

        return false;
    }
}
