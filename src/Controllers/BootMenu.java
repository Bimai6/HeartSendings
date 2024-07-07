/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Usuario
 */
import javax.swing.*;

public class BootMenu extends JPanel {

    private transient FrameManager frameManager;

    public BootMenu(FrameManager frameManager) {
        this.frameManager = frameManager;
        setupWindow();
    }

    public void setupWindow() {
        JLabel welcomeLabel = new JLabel("Bienvenido! Por favor, espere mientras se carga la aplicación...");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        setLayout(new BorderLayout());
        add(welcomeLabel, BorderLayout.CENTER);

        // Cambiar al StartMenu después de un breve retraso
        Timer timer = new Timer(1000, e -> {
            frameManager.switchToMenu(new StartMenu(frameManager));
        });
        timer.setRepeats(false); // Solo se ejecuta una vez
        timer.start();
    }
}
