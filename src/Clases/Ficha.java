package Clases;
public abstract class Ficha {
    protected String color;
    protected String tipo;
    protected int filas;
    protected int columnas;
    protected boolean vivo;
    protected char simbolo;
    public Ficha(String color, String tipo, int filas, int columnas) {
    this.color = color;
    this.tipo = tipo;
    this.filas = filas;
    this.columnas = columnas;
    this.vivo = true;
    this.simbolo = ' ';
    
    }
}