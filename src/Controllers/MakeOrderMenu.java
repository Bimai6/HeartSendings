/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.EstadoPedido;
import Models.Pedido;
import Models.PedidoImpl;
import Models.Producto;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Usuario
 */
public class MakeOrderMenu extends Menu {

    private JButton confirmOrderButton;
    private JButton backButton;
    private JButton nextButton; // Nuevo botón para avanzar al menú siguiente
    private transient List<Producto> selectedProducts;
    private int orderId;

    public MakeOrderMenu(FrameManager frameManager) {
        super(frameManager);
        selectedProducts = frameManager.getSelectedProducts(); 
        setupWindow();
    }

    public MakeOrderMenu(FrameManager frameManager, int id) {
        super(frameManager);
        selectedProducts = frameManager.getSelectedProducts(); 
        this.orderId = id;
        setupWindowWithID(orderId);
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());

        // Panel para los componentes
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botón para confirmar el pedido
        confirmOrderButton = new JButton("Confirmar Pedido");
        confirmOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Verificar si hay productos seleccionados
                    Utils.Validator.checkSelectedProductsIsNotEmpty(selectedProducts);
                    // Crear un pedido con los productos seleccionados, se añadirá cada pedido nuevo a la base de datos automáticamente al instanciarse
                    Pedido pedido = new PedidoImpl(selectedProducts, EstadoPedido.PENDIENTE,
                            frameManager.getPedidoDAO(),
                            frameManager.getCurrentUser().getNickname(), frameManager.getPedidoProductoDAO());
                    String sId = String.valueOf(pedido.getId());
                    // Mostrar mensaje de éxito
                    JOptionPane.showMessageDialog(null, "Pedido modificado con éxito, el ID es: " + sId, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    // Agregar el pedido al usuario actual
                    frameManager.getCurrentUser().addPedido(pedido);

                    // Limpiar la lista de productos seleccionados
                    selectedProducts.clear();
                    // Regresar al menú anterior
                    frameManager.switchToMenu(new OrdersMenu(frameManager));
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado ningún producto", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // Botón para volver al menú anterior sin hacer nada
        backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar la lista de productos seleccionados
                selectedProducts.clear();
                // Regresar al menú Orders
                frameManager.switchToMenu(new OrdersMenu(frameManager));
            }
        });

        // Botón para avanzar al menú siguiente
        nextButton = new JButton("Seleccionar productos");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al menú siguiente (EstablecimientosMenu en este caso)
                frameManager.switchToMenu(new EstablecimientosMenu(frameManager));
            }
        });

        // Agregar componentes al panel
        panel.add(confirmOrderButton, BorderLayout.NORTH);
        panel.add(backButton, BorderLayout.SOUTH);
        panel.add(nextButton, BorderLayout.CENTER);

        // Agregar el panel al centro del menú
        add(panel, BorderLayout.CENTER);
    }

    protected void setupWindowWithID(int orderId) {
        // Implementación específica para MakeOrderMenu con el ID del pedido
        setLayout(new BorderLayout());

        // Panel para los componentes
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botón para guardar los cambios
        JButton saveChangesButton = new JButton("Guardar Cambios");
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el pedido con el ID proporcionado
                Pedido pedido = frameManager.getPedidoDAO()
                        .selectPedidoById(orderId, frameManager.getCurrentUser().getNickname(),
                                frameManager.getPedidoProductoDAO(), frameManager.getEstablecimientoDAO());
                if (pedido != null) {
                    try {
                        // Verificar si hay productos seleccionados
                        Utils.Validator.checkSelectedProductsIsNotEmpty(selectedProducts);
                        // Agregar los productos seleccionados al pedido existente
                        frameManager.getPedidoProductoDAO().updateManyProductsFromPedido(orderId, selectedProducts);
                        // Mostrar mensaje de éxito
                        JOptionPane.showMessageDialog(null, "Pedido modificado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        // Limpiar la lista de productos seleccionados
                        selectedProducts.clear();
                        // Regresar al menú ModifyOrderMenu
                        frameManager.switchToMenu(new ModifyOrderMenu(frameManager));
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(null, "No ha seleccionado ningún producto", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Mostrar mensaje de error si no se encuentra el pedido
                    JOptionPane.showMessageDialog(null, "No se encontró un pedido con el ID proporcionado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        // Botón para avanzar al menú siguiente
        nextButton = new JButton("Seleccionar productos");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al menú siguiente (EstablecimientosMenu en este caso)
                frameManager.switchToMenu(new EstablecimientosMenu(frameManager, orderId));
            }
        });
        // Botón para volver al menú anterior
        backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar la lista de productos seleccionados
                selectedProducts.clear();
                // Regresar al menú Orders 
                frameManager.switchToMenu(new OrdersMenu(frameManager));
            }
        });

        // Agregar componentes al panel
        panel.add(saveChangesButton, BorderLayout.NORTH);
        panel.add(backButton, BorderLayout.SOUTH);
        panel.add(nextButton, BorderLayout.CENTER);

        // Agregar el panel al centro del menú
        add(panel, BorderLayout.CENTER);
    }
}
