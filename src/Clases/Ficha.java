package Clases;

import java.awt.Image;

public abstract class Ficha {
    protected String color;     // "blanco" o "negro"
    protected String tipo;      // "peon", "torre", etc.
    protected Image icono;     // símbolo unicode (♙, ♜, etc.)
    protected String posicion;  // opcional, por si quieres guardar "e4"

    public Ficha(String color, String tipo, Image icono) {
        this.color = color;
        this.tipo = tipo;
        this.icono = icono;
    }

    public String getColor() {
        return color;
    }

    public String getTipo() {
        return tipo;
    }

    public Image getIcono() {
        return icono;
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
