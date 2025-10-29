package clases;

public class Torre extends Ficha {

    public Torre(String color, String tipo, char simbolo) {
        super(color, tipo, simbolo);
    }

    @Override
    public boolean movimientoValido(int filaOrigen, int colOrigen, int filaDestino, int colDestino, Ficha[][] tablero) {
        if (filaOrigen != filaDestino && colOrigen != colDestino) return false;

        int dirFila = Integer.compare(filaDestino, filaOrigen);
        int dirCol = Integer.compare(colDestino, colOrigen);

        int f = filaOrigen + dirFila;
        int c = colOrigen + dirCol;

        // revisar si hay algo en el camino
        while (f != filaDestino || c != colDestino) {
            if (tablero[f][c] != null) return false;
            f += dirFila;
            c += dirCol;
        }

        // verificar destino
        Ficha destino = tablero[filaDestino][colDestino];
        return destino == null || !destino.getColor().equals(this.getColor());
    }
}

