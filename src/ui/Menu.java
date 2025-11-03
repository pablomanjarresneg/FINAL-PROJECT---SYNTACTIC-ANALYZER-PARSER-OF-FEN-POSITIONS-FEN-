package ui;

import javax.swing.*;
public class Menu extends JFrame {

    public Menu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
        

        JPanel panel = new JPanel();
        add(panel);

        JButton startButton = new JButton("Iniciar Nueva Partida");
        startButton.addActionListener(e -> {
            VentanaAjedrez ventana = new VentanaAjedrez();
            ventana.setVisible(true);
            dispose();
        });

        panel.add(startButton);
        
        JButton loadButton = new JButton("Cargar Partida");
        loadButton.addActionListener(e -> {
            VentanaAjedrez ventana = new VentanaAjedrez();
            String nombreArchivo = JOptionPane.showInputDialog(this, "Ingrese nombre del archivo a cargar:", "Cargar Partida", JOptionPane.PLAIN_MESSAGE);
            if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre de archivo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            nombreArchivo = nombreArchivo.trim();
            ventana.cargarPartida(nombreArchivo);
            ventana.setVisible(true);
            dispose();
        });
        panel.add(loadButton);

        JButton exitButton = new JButton("Salir");
        exitButton.addActionListener(e -> {
            System.exit(0);
        }); 
        panel.add(exitButton);
        setVisible(true);
        setLocationRelativeTo(null);


    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> {
            new Menu().setVisible(true);
        });
    }

}
