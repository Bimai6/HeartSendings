/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

/**
 *
 * @author Usuario
 */
import DAO.DireccionDAO;
import DAO.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import Exceptions.UsuarioException;
import Models.Usuario;
import Models.UsuarioImpl;

public class SigninMenu extends Menu {
    private JTextField nickField;
    private JPasswordField passwordField;
    private JTextField calleField;
    private JTextField numCalleField;
    private JTextField ciudadField;
    private JTextField codPostalField;

    public SigninMenu(FrameManager frameManager) {
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

        JLabel calleLabel = new JLabel("Calle:");
        calleField = new JTextField(20);
        JPanel callePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        callePanel.add(calleLabel);
        callePanel.add(calleField);

        JLabel numCalleLabel = new JLabel("Número de calle:");
        numCalleField = new JTextField(20);
        JPanel numCallePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        numCallePanel.add(numCalleLabel);
        numCallePanel.add(numCalleField);

        JLabel ciudadLabel = new JLabel("Ciudad:");
        ciudadField = new JTextField(20);
        JPanel ciudadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ciudadPanel.add(ciudadLabel);
        ciudadPanel.add(ciudadField);

        JLabel codPostalLabel = new JLabel("Código Postal:");
        codPostalField = new JTextField(20);
        JPanel codPostalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        codPostalPanel.add(codPostalLabel);
        codPostalPanel.add(codPostalField);
        
        JButton registerButton = new JButton("Registrarse");
        registerButton.addActionListener(e -> {
            try {
                String nick = nickField.getText();
                String password = new String(passwordField.getPassword());
                String calle = calleField.getText();
                String numCalle = numCalleField.getText();
                String ciudad = ciudadField.getText();
                String codPostal = codPostalField.getText();
                
                // Crear nuevo usuario
                DireccionDAO direccionDAO = frameManager.getDireccionDAO();
                UsuarioDAO usuarioDAO = frameManager.getUsuarioDAO();
                Usuario instanceUser = new UsuarioImpl(nick, password, calle, numCalle, ciudad, codPostal, direccionDAO, usuarioDAO);
                //Validator.addUserToDatabase(newUser);
                Usuario currentUser= usuarioDAO.getUsuarioByNickname(instanceUser.getNickname(), direccionDAO);
                frameManager.setCurrentUser(currentUser);
                frameManager.switchToMenu(new LoggedMenu(frameManager));
            } catch (UsuarioException ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        });
        
        JButton backButton = new JButton("Atrás");
        backButton.addActionListener(e -> {
            switchToPreviousMenu();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        add(Box.createVerticalStrut(20));
        add(nickPanel);
        add(Box.createVerticalStrut(20));
        add(passwordPanel);
        add(Box.createVerticalStrut(20));
        add(callePanel);
        add(Box.createVerticalStrut(20));
        add(numCallePanel);
        add(Box.createVerticalStrut(20));
        add(ciudadPanel);
        add(Box.createVerticalStrut(20));
        add(codPostalPanel);
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
    }
    
}