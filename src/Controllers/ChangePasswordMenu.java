/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Exceptions.UsuarioException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 * @author Usuario
 */
public class ChangePasswordMenu extends Menu {
    public ChangePasswordMenu(FrameManager frameManager) {
        super(frameManager);
        setupWindow();
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());

        // Panel para los componentes
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Componentes para ingresar la nueva contraseña
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setPreferredSize(new Dimension(200, 30));
        JLabel newPasswordLabel = new JLabel("Nueva Contraseña:");
        newPasswordLabel.setLabelFor(newPasswordField);

        // Botón para confirmar la acción
        JButton changePasswordButton = new JButton("Cambiar Contraseña");
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String newPassword = new String(newPasswordField.getPassword());
                    // Aquí implementa la lógica para modificar la contraseña
                    Utils.Validator.checkPassword(newPassword);
                    //frameManager.getCurrentUser().setPassword(newPassword);
                    String nickname = frameManager.getCurrentUser().getNickname();
                    frameManager.getUsuarioDAO().modifyPassword(nickname, newPassword);
                    JOptionPane.showMessageDialog(null, "Contraseña cambiada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    // Volver al menú anterior (ProfileMenu)
                    frameManager.switchToPreviousMenu();
                } catch (UsuarioException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

       // Botón para volver atrás
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> frameManager.switchToPreviousMenu());

        // Agregar componentes al panel
        panel.add(newPasswordLabel, BorderLayout.NORTH);
        panel.add(newPasswordField, BorderLayout.CENTER);
        panel.add(changePasswordButton, BorderLayout.SOUTH);
        panel.add(backButton, BorderLayout.WEST); // Agregar el botón de volver atrás

        // Agregar el panel al centro del menú
        add(panel, BorderLayout.CENTER);
    }
}
