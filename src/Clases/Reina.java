package Clases;
import java.awt.Image;

public class Reina extends Ficha {

    public Reina(String color, String tipo, Image icono) {
        super(color, tipo, icono);
    }

    @Override
    public boolean movimientoValido(int filaOrigen, int colOrigen, int filaDestino, int colDestino, Ficha[][] tablero) {
        // combina lógica de torre + alfil
        int diffFila = Math.abs(filaDestino - filaOrigen);
        int diffCol = Math.abs(colDestino - colOrigen);

        if (diffFila == diffCol || filaOrigen == filaDestino || colOrigen == colDestino) {
            int dirFila = Integer.compare(filaDestino, filaOrigen);
            int dirCol = Integer.compare(colDestino, colOrigen);

            int f = filaOrigen + dirFila;
            int c = colOrigen + dirCol;

            while (f != filaDestino || c != colDestino) {
                if (tablero[f][c] != null) return false;
                f += dirFila;
                c += dirCol;
            }

            Ficha destino = tablero[filaDestino][colDestino];
            return destino == null || !destino.getColor().equals(this.getColor());
        }
        return false;
    }
}
