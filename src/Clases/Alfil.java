package clases;

public class Alfil extends Ficha {

    public Alfil(String color, String tipo, char simbolo) {
        super(color, tipo, simbolo);
    }

    @Override
    public boolean movimientoValido(int filaOrigen, int colOrigen, int filaDestino, int colDestino, Ficha[][] tablero) {
        int diffFila = Math.abs(filaDestino - filaOrigen);
        int diffCol = Math.abs(colDestino - colOrigen);

        if (diffFila != diffCol) return false;

        int dirFila = Integer.compare(filaDestino, filaOrigen);
        int dirCol = Integer.compare(colDestino, colOrigen);

        int f = filaOrigen + dirFila;
        int c = colOrigen + dirCol;

        while (f != filaDestino && c != colDestino) {
            if (tablero[f][c] != null) return false;
            f += dirFila;
            c += dirCol;
        }

        Ficha destino = tablero[filaDestino][colDestino];
        return destino == null || !destino.getColor().equals(this.getColor());
    }
}
