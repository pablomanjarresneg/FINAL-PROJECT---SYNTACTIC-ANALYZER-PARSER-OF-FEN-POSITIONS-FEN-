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

        // Movimiento normal del rey (una casilla en cualquier dirección)
        if (diffFila <= 1 && diffCol <= 1) {
            Ficha destino = tablero[filaDestino][colDestino];
            boolean movimientoBasicoValido = destino == null || !destino.getColor().equals(this.getColor());

            // Asegurar que el rey no se mueve a una casilla amenazada
            if (movimientoBasicoValido && !estaEnJaque(filaDestino, colDestino, tablero)) {
                return true;
            }
        }
        
        // Verificar si es un intento de enroque
        if (!this.seHaMovido() && filaOrigen == filaDestino && Math.abs(colDestino - colOrigen) == 2) {
            return puedeEnrocar(filaOrigen, colOrigen, filaDestino, colDestino, tablero);
        }

        return false;
    }

    private boolean puedeEnrocar(int filaOrigen, int colOrigen, int filaDestino, int colDestino, Ficha[][] tablero) {
        // Determinar si es enroque corto o largo
        boolean enroqueCorto = colDestino > colOrigen;
        int colTorre = enroqueCorto ? 7 : 0;
        
        // Verificar que la torre está en su lugar y no se ha movido
        Ficha torre = tablero[filaOrigen][colTorre];
        if (!(torre instanceof Torre) || torre.seHaMovido()) {
            return false;
        }
        
        // Verificar que las casillas intermedias están vacías
        int inicio = Math.min(colOrigen, colDestino);
        int fin = Math.max(colOrigen, colDestino);
        for (int col = inicio + 1; col < fin; col++) {
            if (tablero[filaOrigen][col] != null) {
                return false;
            }
        }
        
        // Verificar que el rey no está en jaque y no pasa por casillas amenazadas
        int direccion = enroqueCorto ? 1 : -1;
        for (int col = colOrigen; col != colDestino + direccion; col += direccion) {
            if (estaEnJaque(filaOrigen, col, tablero)) {
                return false;
            }
        }
        
        return true;
    }

    private boolean estaEnJaque(int fila, int col, Ficha[][] tablero) {
        // Revisar amenazas en todas las direcciones
        return hayAmenazaEnDireccion(fila, col, tablero, -1, -1) || // Diagonal superior izquierda
               hayAmenazaEnDireccion(fila, col, tablero, -1, 0)  || // Vertical hacia arriba
               hayAmenazaEnDireccion(fila, col, tablero, -1, 1)  || // Diagonal superior derecha
               hayAmenazaEnDireccion(fila, col, tablero, 0, -1)  || // Horizontal hacia la izquierda
               hayAmenazaEnDireccion(fila, col, tablero, 0, 1)   || // Horizontal hacia la derecha
               hayAmenazaEnDireccion(fila, col, tablero, 1, -1)  || // Diagonal inferior izquierda
               hayAmenazaEnDireccion(fila, col, tablero, 1, 0)   || // Vertical hacia abajo
               hayAmenazaEnDireccion(fila, col, tablero, 1, 1)   || // Diagonal inferior derecha
               hayAmenazaDeCaballo(fila, col, tablero);             // Amenazas de caballo
    }

    private boolean hayAmenazaEnDireccion(int fila, int col, Ficha[][] tablero, int dFila, int dCol) {
        int f = fila + dFila;
        int c = col + dCol;
        boolean primerMovimiento = true;

        while (f >= 0 && f < 8 && c >= 0 && c < 8) {
            Ficha pieza = tablero[f][c];
            if (pieza != null) {
                if (pieza.getColor().equals(this.getColor())) {
                    return false;
                }
                
                String tipo = pieza.getTipo().toLowerCase();
                if ((tipo.equals("reina")) ||
                    (tipo.equals("torre") && dFila * dCol == 0) ||  // Torres en líneas rectas
                    (tipo.equals("alfil") && dFila * dCol != 0) ||  // Alfiles en diagonales
                    (tipo.equals("rey") && primerMovimiento) ||      // Rey solo amenaza casillas adyacentes
                    (tipo.equals("peon") && primerMovimiento && 
                     ((this.getColor().equals("blanco") && dFila == 1) ||  // Peones negros atacan hacia abajo
                      (this.getColor().equals("negro") && dFila == -1)))) {// Peones blancos atacan hacia arriba
                    return true;
                }
                return false;
            }
            f += dFila;
            c += dCol;
            primerMovimiento = false;
        }
        return false;
    }

    private boolean hayAmenazaDeCaballo(int fila, int col, Ficha[][] tablero) {
        int[][] movimientosCaballo = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };

        for (int[] mov : movimientosCaballo) {
            int nuevaFila = fila + mov[0];
            int nuevaCol = col + mov[1];

            if (nuevaFila >= 0 && nuevaFila < 8 && nuevaCol >= 0 && nuevaCol < 8) {
                Ficha pieza = tablero[nuevaFila][nuevaCol];
                if (pieza != null && !pieza.getColor().equals(this.getColor()) && 
                    pieza.getTipo().toLowerCase().equals("caballo")) {
                    return true;
                }
            }
        }
        return false;
    }

    // Este método está implementado más abajo con la lógica completa de verificación de jaque
}