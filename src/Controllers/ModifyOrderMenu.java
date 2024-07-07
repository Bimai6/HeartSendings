/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Pedido;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
public class ModifyOrderMenu extends Menu {
     private JTextField orderIdField;
    private JButton confirmButton;
    private JButton backButton;

    public ModifyOrderMenu(FrameManager frameManager) {
        super(frameManager);
        setupWindow();
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para los componentes principales
        JPanel panel = new JPanel(new FlowLayout());

        JLabel orderIdLabel = new JLabel("Ingrese el ID del pedido:");
        orderIdField = new JTextField(10);
        confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int orderId = Integer.parseInt(orderIdField.getText());
            Pedido pedido = frameManager.getPedidoDAO().selectPedidoById(orderId,frameManager.getCurrentUser().getNickname(), frameManager.getPedidoProductoDAO(), frameManager.getEstablecimientoDAO());
            if (pedido != null) {
                frameManager.switchToMenu(new AddOrSubstractMenu(frameManager, orderId));
            } else {
                JOptionPane.showMessageDialog(ModifyOrderMenu.this, "No existe ningun pedido con ese ID.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(ModifyOrderMenu.this, "Ingrese un ID de pedido válido.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }
});

        backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManager.switchToPreviousMenu();
            }
        });

        panel.add(orderIdLabel);
        panel.add(orderIdField);
        panel.add(confirmButton);

        // Panel para el botón de volver
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(backButton);

        // Agregar componentes al panel principal
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(backButtonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    
}
