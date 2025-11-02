package ui;

import Clases.Ficha;
import Motor.Tablero;
import Parser.Codificador;
import java.awt.*;
import javax.swing.*;

public class VentanaAjedrez extends JFrame {
    private final Tablero tablero;
    private JButton[][] casillas;
    private JTextField areaMovimientosBlancas;
    private JTextField areaMovimientosNegras;
    private final Codificador codificador;
    
    public VentanaAjedrez() {
        tablero = new Tablero();
        codificador = new Codificador();
        initUI();
    }

    private void initUI() {
        setTitle("Tablero de Ajedrez");
        setSize(700, 700);
        setMaximumSize(new Dimension(900, 1000));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panelTablero = new JPanel(new GridLayout(9,9));
        casillas = new JButton[8][8];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == 0 && j == 0) {
                    JButton esquinaVacia = new JButton();
                    esquinaVacia.setBackground(Color.WHITE);
                    panelTablero.add(esquinaVacia);
                } else if (i == 0) {
                    // Fila superior (etiquetas de columnas)
                    JButton etiqueta = new JButton(String.valueOf((char)('A' + j - 1)));
                    etiqueta.setFont(new Font("Arial", Font.BOLD, 16));
                    etiqueta.setBackground(Color.WHITE);
                    panelTablero.add(etiqueta);
                } else if (j == 0) {
                    // Columna izquierda (etiquetas de filas)
                    JButton etiqueta = new JButton(String.valueOf(9 - i));
                    etiqueta.setFont(new Font("Arial", Font.BOLD, 16));
                    etiqueta.setBackground(Color.WHITE);
                    panelTablero.add(etiqueta);
                } else { 
                    int filaTablero = i - 1;
                    int columnaTablero = j - 1;

                    casillas[filaTablero][columnaTablero] = new JButton();
                    casillas[filaTablero][columnaTablero].setFocusPainted(false);
                    casillas[filaTablero][columnaTablero].setFocusable(false);
                    casillas[filaTablero][columnaTablero].setFont(new Font("Arial Unicode MS", Font.PLAIN, 38));
                    casillas[filaTablero][columnaTablero].setPreferredSize(new Dimension(80, 80));
                    Color color = (filaTablero + columnaTablero) % 2 == 0 ? Color.WHITE : new Color(139, 69, 19);
                    casillas[filaTablero][columnaTablero].setBackground(color);
                    panelTablero.add(casillas[filaTablero][columnaTablero]);
                }

            }
        }
        // Crear panel para movimientos
        JPanel panelMovimientos = new JPanel(new BorderLayout());
        panelMovimientos.setPreferredSize(new Dimension(600, 50));
        panelMovimientos.setBackground(Color.LIGHT_GRAY);
        panelMovimientos.setBorder(BorderFactory.createTitledBorder("Movimientos"));

        // Área de texto para movimientos de las blancas
        areaMovimientosBlancas = new JTextField(20);
        areaMovimientosBlancas.setEditable(true);
        areaMovimientosBlancas.setFont(new Font("Arial", Font.PLAIN, 14));
        areaMovimientosBlancas.addActionListener(e -> {
            procesarMovimiento(areaMovimientosBlancas, true);
        });
        SwingUtilities.invokeLater(() -> areaMovimientosBlancas.requestFocus());

        // Área de texto para movimientos de las negras
        areaMovimientosNegras = new JTextField(20);
        areaMovimientosNegras.setEditable(true);
        areaMovimientosNegras.setFont(new Font("Arial", Font.PLAIN, 14));
        areaMovimientosBlancas.setEditable(false);
        areaMovimientosNegras.addActionListener(e -> {
            procesarMovimiento(areaMovimientosNegras, false);
        });

        panelMovimientos.add(areaMovimientosBlancas, BorderLayout.EAST);
        panelMovimientos.add(areaMovimientosNegras, BorderLayout.WEST);

        JPanel guardarYCargarPanel = new JPanel();
        guardarYCargarPanel.setMaximumSize(new Dimension(20,20));
        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.setFocusPainted(false);
        JButton botonCargar = new JButton("Cargar");
        botonCargar.setFocusPainted(false);

        guardarYCargarPanel.add(botonGuardar);
        guardarYCargarPanel.add(botonCargar);

        add(guardarYCargarPanel, BorderLayout.NORTH);
        add(panelTablero, BorderLayout.CENTER);
        add(panelMovimientos, BorderLayout.SOUTH);
        actualizarTablero();
        setLocationRelativeTo(null);
    }

    private void actualizarTablero() {
        Ficha[][] tableroFichas = tablero.getTablero();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(tableroFichas[i][j] != null) {
                    casillas[i][j].setText(String.valueOf(tableroFichas[i][j].getSimbolo()));
                } else {
                    casillas[i][j].setText("");
                }
            }
        }
    }


    private void cambiarTurno() {
        codificador.limpiarMovimientos();
        areaMovimientosBlancas.setEditable(!areaMovimientosBlancas.isEditable());
        areaMovimientosNegras.setEditable(!areaMovimientosNegras.isEditable());
    }

    private void procesarMovimiento(JTextField campo, boolean esBlanca) {
        String movimiento = campo.getText().trim();
        if (movimiento.isEmpty()) return;

        if (!codificador.validarBNF(movimiento)) {
            JOptionPane.showMessageDialog(this, "Movimiento inválido: " + movimiento, "Error", JOptionPane.ERROR_MESSAGE);
            campo.setText("");
            return;
        }
        boolean aplicado = intentarAplicarMovimiento(movimiento, esBlanca);
        codificador.limpiarMovimientos();
        if (aplicado) {
            campo.setText("");
            actualizarTablero();
            cambiarTurno();
        } else {
            JOptionPane.showMessageDialog(this, "Movimiento no permitido: " + movimiento, "Error", JOptionPane.ERROR_MESSAGE);
            campo.setText("");
        }
    }

    private boolean intentarAplicarMovimiento(String movimiento, boolean esBlanca) {
        String origen = movimiento.substring(0, 2);
        String destino = movimiento.substring(2, 4);

        if (tablero.esTurnoCorrecto(origen, esBlanca)) {
            return false;
        }
        boolean exito = tablero.mover(origen, destino);
        if (exito) {
            codificador.registrarMovimiento(movimiento);
        }
        return true;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> {
            new VentanaAjedrez().setVisible(true);
        });
    }
}