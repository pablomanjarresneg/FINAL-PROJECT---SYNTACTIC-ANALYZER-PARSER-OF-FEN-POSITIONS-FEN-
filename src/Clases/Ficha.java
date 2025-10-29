package clases;

public abstract class Ficha {
    protected String color;     // "blanco" o "negro"
    protected String tipo;      // "peon", "torre", etc.
    protected char simbolo;     // símbolo unicode (♙, ♜, etc.)
    protected String posicion;  // opcional, por si quieres guardar "e4"

    public Ficha(String color, String tipo, char simbolo) {
        this.color = color;
        this.tipo = tipo;
        this.simbolo = simbolo;
    }

    public String getColor() {
        return color;
    }

    public String getTipo() {
        return tipo;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    // método abstracto que cada ficha implementa a su manera
    public abstract boolean movimientoValido(int filaOrigen, int colOrigen, int filaDestino, int colDestino, Ficha[][] tablero);
}
