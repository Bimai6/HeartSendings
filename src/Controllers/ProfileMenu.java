/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Usuario
 */
public class ProfileMenu extends Menu {

    public ProfileMenu(FrameManager frameManager) {
        super(frameManager);
        setupWindow();
    }

    public void setupWindow() {
        setLayout(new BorderLayout());

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botón para modificar la contraseña
        JButton changePasswordButton = new JButton("Modificar Contraseña");
        changePasswordButton.addActionListener(e -> {
            frameManager.switchToMenu(new ChangePasswordMenu(frameManager));
        });

        // Botón para modificar la dirección de entrega
        JButton changeAddressButton = new JButton("Modificar Dirección de Entrega");
        changeAddressButton.addActionListener(e -> {
            frameManager.switchToMenu(new ChangeAddressMenu(frameManager));
        });

        // Botón para cerrar sesión
        JButton closeSesionButton = new JButton("Cerrar Sesión");
        closeSesionButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "¿Quiere cerrar sesión?", "Confirmación", JOptionPane.YES_NO_OPTION);
            // Aplica lógica según el botón presionado
            if (response == JOptionPane.YES_OPTION) {
                frameManager.setCurrentUser(null);
                frameManager.switchToMenu(new StartMenu(frameManager));
            }
        });

        // Botón para volver al menú "Logged"
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> {
            frameManager.switchToMenu(new LoggedMenu(frameManager));
        });

        // Agregar botones al panel
        buttonPanel.add(changePasswordButton);
        buttonPanel.add(changeAddressButton);
        buttonPanel.add(closeSesionButton);
        buttonPanel.add(backButton);

        // Agregar el panel de botones al centro del menú
        add(buttonPanel, BorderLayout.CENTER);
    }
}
