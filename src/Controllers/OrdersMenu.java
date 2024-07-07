/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Pedido;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Usuario
 */
public class OrdersMenu extends Menu {

    public OrdersMenu(FrameManager frameManager) {
        super(frameManager);
        setupWindow();
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());

        // Crear panel para botones
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones para realizar acciones
        JButton makeOrderButton = new JButton("Realizar Pedido");
        makeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManager.switchToMenu(new MakeOrderMenu(frameManager));
            }
        });

        JButton modifyOrderButton = new JButton("Modificar Pedido");
        modifyOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManager.switchToMenu(new ModifyOrderMenu(frameManager));
            }
        });

        JButton cancelOrderButton = new JButton("Cancelar Pedido");
        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer orderId = getValidOrderId();
                if (orderId == null) {
                    return;
                }

                try {
                    Pedido pedido = frameManager.getPedidoDAO().selectPedidoById(orderId,
                            frameManager.getCurrentUser().getNickname(), frameManager.getPedidoProductoDAO(), frameManager.getEstablecimientoDAO());

                    if (pedido == null) {
                        frameManager.logMessage("No se encontró ningún pedido con el ID proporcionado.", "warning");
                        return;
                    }

                    frameManager.getPedidoDAO().updateEstadoDelPedido(pedido);
                    frameManager.logMessage("Pedido cancelado con éxito.", "confirmation");
                } catch (Exception ex) {
                    frameManager.logMessage("Ocurrió un error al intentar cancelar el pedido.", "error");
                }
            }
        });

        JButton viewOrderStatusButton = new JButton("Ver Estado del Pedido");
        viewOrderStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer orderId = getValidOrderId();
                if (orderId == null) {
                    return;
                }

                try {
                    Pedido pedido = frameManager.getPedidoDAO().selectPedidoById(orderId,
                            frameManager.getCurrentUser().getNickname(), frameManager.getPedidoProductoDAO(), frameManager.getEstablecimientoDAO());

                    if (pedido == null) {
                        frameManager.logMessage("No se encontró ningún pedido con el ID proporcionado.", "warning");
                        return;
                    }

                    JOptionPane.showMessageDialog(null, "Los datos del pedido son los siguientes: " + pedido);
                } catch (Exception ex) {
                    frameManager.logMessage("Ocurrió un error al intentar ver el estado del pedido.", "error");
                }
            }
        });

        JButton totalPriceButton = new JButton("Mostrar Precio Total");
        totalPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer orderId = getValidOrderId();
                if (orderId == null) {
                    return;
                }

                try {
                    Pedido pedido = frameManager.getPedidoDAO().selectPedidoById(orderId,
                            frameManager.getCurrentUser().getNickname(), frameManager.getPedidoProductoDAO(), frameManager.getEstablecimientoDAO());

                    if (pedido == null) {
                        frameManager.logMessage("No se encontró ningún pedido con el ID proporcionado.", "warning");
                        return;
                    }

                    double total = pedido.calculateTotalPrice();
                    JOptionPane.showMessageDialog(null, "El precio total del pedido " + orderId + " es: $" + total);
                } catch (Exception ex) {
                    frameManager.logMessage("Ocurrió un error al intentar mostrar el precio total del pedido.", "error");
                }
            }
        });
        
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> {
            frameManager.switchToMenu(new LoggedMenu(frameManager));
        });

        // Agregar botones al panel
        buttonPanel.add(makeOrderButton);
        buttonPanel.add(modifyOrderButton);
        buttonPanel.add(cancelOrderButton);
        buttonPanel.add(viewOrderStatusButton);
        buttonPanel.add(totalPriceButton);
        buttonPanel.add(backButton);

        // Agregar panel al centro del menú
        add(buttonPanel, BorderLayout.CENTER);
    }

    private Integer getValidOrderId() {
        while (true) {
            String input = JOptionPane.showInputDialog(null, "Ingrese el ID del pedido:");
            if (input == null) {
                // El usuario cerró la ventana de input
                return null;
            }

            try {
                int orderId = Integer.parseInt(input);
                if (orderId > 0) {
                    return orderId;
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un ID válido (número entero positivo).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un ID válido (número entero positivo).", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
