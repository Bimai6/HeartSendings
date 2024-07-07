/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Exceptions.UsuarioException;
import Models.Usuario;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Usuario
 */
public class ChangeAddressMenu extends Menu {
    public ChangeAddressMenu(FrameManager frameManager) {
        super(frameManager);
        setupWindow();
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());

        // Panel para los componentes
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para los campos de entrada
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Componentes para ingresar la nueva dirección
        JTextField calleField = new JTextField();
        calleField.setPreferredSize(new Dimension(200, 30));
        JLabel calleLabel = new JLabel("Calle:");
        calleLabel.setLabelFor(calleField);

        JTextField numCalleField = new JTextField();
        numCalleField.setPreferredSize(new Dimension(200, 30));
        JLabel numCalleLabel = new JLabel("Número de calle:");
        numCalleLabel.setLabelFor(numCalleField);

        JTextField ciudadField = new JTextField();
        ciudadField.setPreferredSize(new Dimension(200, 30));
        JLabel ciudadLabel = new JLabel("Ciudad:");
        ciudadLabel.setLabelFor(ciudadField);

        JTextField codPostalField = new JTextField();
        codPostalField.setPreferredSize(new Dimension(200, 30));
        JLabel codPostalLabel = new JLabel("Código Postal:");
        codPostalLabel.setLabelFor(codPostalField);

        // Agregar componentes al panel de entrada
        inputPanel.add(calleLabel);
        inputPanel.add(calleField);
        inputPanel.add(numCalleLabel);
        inputPanel.add(numCalleField);
        inputPanel.add(ciudadLabel);
        inputPanel.add(ciudadField);
        inputPanel.add(codPostalLabel);
        inputPanel.add(codPostalField);

        // Botón para confirmar la acción
        JButton changeAddressButton = new JButton("Cambiar Dirección");
        changeAddressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String calle = calleField.getText();
                    String numCalle = numCalleField.getText();
                    String ciudad = ciudadField.getText();
                    String codPostal = codPostalField.getText();

                    // Aquí implementa la lógica para modificar la dirección
                    String direccion = calle + ", " + numCalle + ", " + ciudad + ", " + codPostal;
                    Utils.Validator.checkDirection(direccion);
                    //frameManager.getCurrentUser().setDireccion(calle + ", " + numCalle + ", " + ciudad + ", " + codPostal);
                    Usuario usuario = frameManager.getCurrentUser();
                    frameManager.getDireccionDAO().modifyAddress(direccion, usuario.getDireccion().getId());
                    JOptionPane.showMessageDialog(null, "Dirección cambiada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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

        // Agregar componentes al panel principal
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(changeAddressButton, BorderLayout.SOUTH);
        panel.add(backButton, BorderLayout.WEST); // Agregar el botón de volver atrás

        // Agregar el panel al centro del menú
        add(panel, BorderLayout.CENTER);
    }
}
