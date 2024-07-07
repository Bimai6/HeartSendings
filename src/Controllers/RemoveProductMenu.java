package Controllers;

import Models.Pedido;
import Models.Producto;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RemoveProductMenu extends Menu {

    private JButton backButton;
    private JComboBox<String> productComboBox;
    private JComboBox<Integer> quantityComboBox;
    private int orderId;
    private Map<String, Integer> productQuantityMap;

    public RemoveProductMenu(FrameManager frameManager, int orderId) {
        super(frameManager);
        this.orderId = orderId;
        this.productQuantityMap = new HashMap<>();
        setupWindow();
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel productLabel = new JLabel("Seleccione el producto a quitar:");
        JLabel quantityLabel = new JLabel("Cantidad a quitar:");

        productComboBox = new JComboBox<>();
        quantityComboBox = new JComboBox<>();

        cargarProductosDelPedido();

        productComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) productComboBox.getSelectedItem();
                int availableQuantity = productQuantityMap.getOrDefault(selectedProduct, 0);
                quantityComboBox.removeAllItems();
                for (int i = 1; i <= availableQuantity; i++) {
                    quantityComboBox.addItem(i);
                }
            }
        });

        JButton removeButton = new JButton("Quitar");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) productComboBox.getSelectedItem();
                int quantityToRemove = (int) quantityComboBox.getSelectedItem();

                Pedido pedido = frameManager.getPedidoDAO().selectPedidoById(orderId, frameManager.getCurrentUser().getNickname(), frameManager.getPedidoProductoDAO(), frameManager.getEstablecimientoDAO());
                if (pedido != null) {
                    List<Producto> productos = pedido.getProductos();
                    for (Producto producto : productos) {
                        if (producto.getNombre().equals(selectedProduct)) {
                            int productId = producto.getId();
                            frameManager.getPedidoProductoDAO().removeProductFromPedido(orderId, productId, quantityToRemove);

                            // Verificar si el pedido está vacío
                            Pedido updatedPedido = frameManager.getPedidoDAO().selectPedidoById(orderId, frameManager.getCurrentUser().getNickname(), frameManager.getPedidoProductoDAO(), frameManager.getEstablecimientoDAO());
                            if (updatedPedido == null || updatedPedido.getProductos().isEmpty()) {
                                frameManager.logMessage("El pedido se ha eliminado porque ya no contiene productos.", "confirmation");
                            } else {
                                frameManager.logMessage("Cantidad del producto quitada del Pedido con éxito", "confirmation");
                            }

                            // Volver al menú de modificar pedido
                            frameManager.switchToMenu(new ModifyOrderMenu(frameManager));
                            break;
                        }
                    }
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

        JPanel inputPanel = new JPanel();
        inputPanel.add(productLabel);
        inputPanel.add(productComboBox);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityComboBox);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(removeButton, BorderLayout.EAST);
        panel.add(backButton, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
    }

    private void cargarProductosDelPedido() {
        Pedido pedido = frameManager.getPedidoDAO().selectPedidoById(orderId, frameManager.getCurrentUser().getNickname(), frameManager.getPedidoProductoDAO(), frameManager.getEstablecimientoDAO());
        if (pedido != null) {
            List<Producto> productos = pedido.getProductos();
            for (Producto producto : productos) {
                String nombreProducto = producto.getNombre();
                int cantidad = frameManager.getPedidoProductoDAO().getCantidadProductoEnPedido(orderId, producto.getId());
                productComboBox.addItem(nombreProducto);
                productQuantityMap.put(nombreProducto, cantidad);

                // Verificamos si es el primer producto y establecemos su cantidad por defecto si es así
                if (productComboBox.getItemCount() == 1) {
                    quantityComboBox.removeAllItems();
                    for (int i = 1; i <= cantidad; i++) {
                        quantityComboBox.addItem(i);
                    }
                }
            }
        }
    }
}
