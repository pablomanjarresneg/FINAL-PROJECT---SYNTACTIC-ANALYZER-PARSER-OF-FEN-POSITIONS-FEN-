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
            // Movimiento simple
            if (filaDestino == filaOrigen + direccion && tablero[filaDestino][colDestino] == null) {
                return true;
            }
            // Movimiento doble inicial
            if (filaOrigen == filaInicial && filaDestino == filaOrigen + 2 * direccion
                    && tablero[filaOrigen + direccion][colOrigen] == null
                    && tablero[filaDestino][colDestino] == null) {
                return true;
            }
        }

        // Capturas (diagonal normal o al paso)
        if (Math.abs(colDestino - colOrigen) == 1 && filaDestino == filaOrigen + direccion) {
            // Captura normal
            Ficha destino = tablero[filaDestino][colDestino];
            if (destino != null && !destino.getColor().equals(this.getColor())) {
                return true;
            }
            
            // Captura al paso
            Ficha peonAdyacente = tablero[filaOrigen][colDestino];
            if (destino == null && peonAdyacente instanceof Peon &&
                !peonAdyacente.getColor().equals(this.getColor()) &&
                peonAdyacente.getTurnoUltimoMovimiento() == this.getTurnoUltimoMovimiento() - 1) {
                
                // Verificar que el peón capturado se movió dos casillas en su último movimiento
                int filaInicialOponente = this.getColor().equals("blanco") ? 3 : 4;
                if (filaOrigen == filaInicialOponente) {
                    return true;
                }
            }
        }

        return false;
    }
}
