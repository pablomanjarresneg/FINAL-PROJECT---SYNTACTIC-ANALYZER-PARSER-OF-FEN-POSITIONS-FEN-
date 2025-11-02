package Clases;
import java.awt.Image;

public class Caballo extends Ficha {

    public Caballo(String color, String tipo, Image icono) {
        super(color, tipo, icono);
    }

    @Override
    public boolean movimientoValido(int filaOrigen, int colOrigen, int filaDestino, int colDestino, Ficha[][] tablero) {
        int diffFila = Math.abs(filaDestino - filaOrigen);
        int diffCol = Math.abs(colDestino - colOrigen);

        if (!((diffFila == 2 && diffCol == 1) || (diffFila == 1 && diffCol == 2))) return false;

        Ficha destino = tablero[filaDestino][colDestino];
        return destino == null || !destino.getColor().equals(this.getColor());
    }
}
