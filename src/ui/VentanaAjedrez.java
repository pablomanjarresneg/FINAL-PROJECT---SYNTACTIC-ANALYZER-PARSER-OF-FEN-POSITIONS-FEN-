package ui;

import javax.swing.*;

import Clases.Ficha;

import java.awt.*;
import java.awt.event.*;
import Motor.Tablero;

public class VentanaAjedrez extends JFrame {
    private Tablero tablero;
    private JButton[][] casillas;

    public VentanaAjedrez() {
        tablero = new Tablero();
        initUI();
    }
    private void initUI() {
        setTitle("Tablero de Ajedrez");
        setSize(800, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new GridLayout(8,8));
        panel.setMaximumSize(new Dimension(800,800));
        casillas = new JButton[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                casillas[i][j] = new JButton();
                casillas[i][j].setFont(new Font("Arial Unicode MS", Font.PLAIN, 48));
                casillas[i][j].setPreferredSize(new Dimension(80, 80));

                Color color = (i + j) % 2 == 0 ? Color.WHITE : new Color(139, 69, 19);
                casillas[i][j].setBackground(color);
                panel.add(casillas[i][j]);
            }
        }

        add(panel);
        actualizarTablero();
        setLocationRelativeTo(null);
    }

    private void actualizarTablero() {
        Ficha[][] board = tablero.getTablero();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] != null) {
                    casillas[i][j].setText(String.valueOf(board[i][j].getSimbolo()));
                } else {
                    casillas[i][j].setText("");
                }
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> {
            new VentanaAjedrez().setVisible(true);
        });
    }
}