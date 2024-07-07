/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import javax.swing.*;
import java.awt.*;
import Utils.Validator;
import Exceptions.UsuarioException;
import Models.Usuario;

public class LoginMenu extends Menu {
    private JTextField nickField;
    private JPasswordField passwordField;

    public LoginMenu(FrameManager frameManager) {
        super(frameManager);
        setupWindow();
    }

    @Override
    public void setupWindow() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0xDDDDFF)); 
        JLabel nickLabel = new JLabel("Nick del usuario:");
        nickField = new JTextField(20);
        JPanel nickPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nickPanel.add(nickLabel);
        nickPanel.add(nickField);
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField(20);
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        
        JButton enterButton = new JButton("Entrar");
        enterButton.addActionListener(e -> {
            try {
                String nick = nickField.getText();
                String password = new String(passwordField.getPassword());
                Validator.checkLogin(nick, password, frameManager.getUsuarioDAO(), frameManager.getDireccionDAO()); 
                System.out.println("Login exitoso para el usuario: " + nick);
                // Lógica para configurar el menú después de iniciar sesión
                Usuario usuario = frameManager.getUsuarioDAO().getUsuarioByNickname(nick, frameManager.getDireccionDAO());
                frameManager.setCurrentUser(usuario);
                frameManager.switchToMenu(new LoggedMenu(frameManager));
            } catch (UsuarioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton backButton = new JButton("Atrás");
        backButton.addActionListener(e -> {
            switchToPreviousMenu();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(enterButton);
        buttonPanel.add(backButton);

        add(Box.createVerticalStrut(20));
        add(nickPanel);
        add(Box.createVerticalStrut(20));
        add(passwordPanel);
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
    }
    
    
}
