package ui;

import Clases.Ficha;
import Motor.Tablero;
import Parser.Codificador;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class VentanaAjedrez extends JFrame {
    private final Tablero tablero;
    private JButton[][] casillas;
    private JTextField areaMovimientos;
    private final Codificador codificador;
    private boolean esTurnoBlancas = true;
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

                    Color color = (filaTablero + columnaTablero) % 2 == 0 ? Color.WHITE : new Color(46, 139, 87);

                    casillas[filaTablero][columnaTablero].setBackground(color);
                    panelTablero.add(casillas[filaTablero][columnaTablero]);
                }

            }
        }
        // Crear panel para movimientos
        JPanel panelMovimientos = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelMovimientos.setMaximumSize(new Dimension(getWidth() / 2, 30));
        panelMovimientos.setAlignmentX(CENTER_ALIGNMENT);
        panelMovimientos.setBackground(Color.LIGHT_GRAY);
        panelMovimientos.setAlignmentX(CENTER_ALIGNMENT);
        panelMovimientos.setFont(new Font("Arial", Font.BOLD, 15));
        String tituloTurno = esTurnoBlancas ? "Turno de las Blancas" : "Turno de las Negras";
        
        panelMovimientos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            tituloTurno, 
            TitledBorder.CENTER,     
            TitledBorder.DEFAULT_POSITION  
        ));

        // Área de texto para movimientos
        areaMovimientos = new JTextField(25);
        areaMovimientos.setEditable(true);
        areaMovimientos.setFont(new Font("Arial", Font.PLAIN, 14));
        areaMovimientos.addActionListener(e -> {
            procesarMovimiento(areaMovimientos, esTurnoBlancas);
        });
        SwingUtilities.invokeLater(() -> areaMovimientos.requestFocus());

        

        panelMovimientos.add(areaMovimientos, BorderLayout.PAGE_END);

        JPanel guardarYCargarPanel = new JPanel();
        guardarYCargarPanel.setMaximumSize(new Dimension(20,20));
        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.setFocusPainted(false);
        botonGuardar.addActionListener( e -> {
            String nombreArchivo = JOptionPane.showInputDialog(this, "Ingrese nombre del archivo para guardar la notación BNF:", "Guardar Partida", JOptionPane.PLAIN_MESSAGE);
            if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                nombreArchivo = "partidas/" + nombreArchivo + ".bnf";
            } else {
                JOptionPane.showMessageDialog(this, "Nombre de archivo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            codificador.guardarPartidaBNF(nombreArchivo);
        });
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
    }

    private void procesarMovimiento(JTextField campo, boolean esBlanca) {
        String movimiento = campo.getText().trim();
        if (movimiento.isEmpty()) return;

        if (!codificador.validarBNF(movimiento)) {
            JOptionPane.showMessageDialog(this, "Movimiento inválido: " + movimiento, "Error", JOptionPane.ERROR_MESSAGE);
            campo.setText("");
            return;
        }
        boolean aplicado = intentarAplicarMovimiento(movimiento, !esBlanca);
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
            esTurnoBlancas = !esTurnoBlancas;
            return true;
        }

        return false;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> {
            new VentanaAjedrez().setVisible(true);
        });
    }
}
