/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class StartMenu extends Menu {

    public StartMenu(FrameManager frameManager) {
        super(frameManager);
        setupWindow();
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());
    JPanel backgroundPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    };
    backgroundPanel.setPreferredSize(new Dimension(600, 400));

    JPanel imagePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Image image = ImageIO.read(new File("src/Sources/placeholderimage.png"));
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error al cargar la imagen.");
            }
        }
    };
    imagePanel.setLayout(new BorderLayout());
    imagePanel.setPreferredSize(new Dimension(200, 150));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setOpaque(false);

    JButton loginButton = new JButton("Iniciar Sesión");
    loginButton.addActionListener(e -> {
        frameManager.switchToMenu(new LoginMenu(frameManager));
    });

    JButton signinButton = new JButton("Registrarse");
    signinButton.addActionListener(e -> {
        frameManager.switchToMenu(new SigninMenu(frameManager));
    });

    buttonPanel.add(loginButton);
    buttonPanel.add(signinButton);

    backgroundPanel.setLayout(new BorderLayout());
    backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
    backgroundPanel.add(imagePanel, BorderLayout.CENTER);

    add(backgroundPanel, BorderLayout.CENTER);
    }
}
