package Controllers;

import Models.Establecimiento;
import Models.Producto;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ProductosMenu extends Menu {

    private JButton backButton;
    private JButton confirmSelectionButton;
    private Establecimiento selectedEstablishment;
    private List<Producto> availableProducts;
    private JList<String> productsList;
    private DefaultListModel<String> listModel;
    private JTextField quantityField;
    private int orderId;

    public ProductosMenu(FrameManager frameManager, Establecimiento selectedEstablishment) {
        super(frameManager);
        this.selectedEstablishment = selectedEstablishment;
        setupWindow();
    }

    public ProductosMenu(FrameManager frameManager, int id, Establecimiento selectedEstablishment) {
        super(frameManager);
        this.orderId = id;
        this.selectedEstablishment = selectedEstablishment;
        setupWindowWithID(orderId);
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());

        // Panel para los componentes
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Etiqueta para mostrar el nombre del establecimiento seleccionado
        JLabel selectedEstablishmentLabel = new JLabel("Establecimiento seleccionado: " + selectedEstablishment.getNombre());

        // Modelo de lista para los productos disponibles
        listModel = new DefaultListModel<>();
        productsList = new JList<>(listModel);
        productsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(productsList);

        // Campo de texto para la cantidad
        JPanel quantityPanel = new JPanel(new BorderLayout());
        JLabel quantityLabel = new JLabel("Cantidad:");
        quantityField = new JTextField();
        quantityPanel.add(quantityLabel, BorderLayout.WEST);
        quantityPanel.add(quantityField, BorderLayout.CENTER);

        // Botón para confirmar la selección de un producto
        confirmSelectionButton = new JButton("Confirmar Selección");
        confirmSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = productsList.getSelectedIndex();
                if (selectedIndex != -1) {
                    try {
                        int quantity = Integer.parseInt(quantityField.getText());
                        if (quantity <= 0) {
                            throw new NumberFormatException();
                        }
                        Producto selectedProduct = selectedEstablishment.getProductos().get(selectedIndex);
                        selectedProduct.setCantidad(quantity);
                        Utils.SpecificListManipulation.updateCarrito(frameManager.getSelectedProducts(), selectedProduct);
                        frameManager.switchToMenu(new MakeOrderMenu(frameManager));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "Por favor, ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Botón para volver al menú anterior
        backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManager.switchToPreviousMenu();
            }
        });

        // Agregar componentes al panel
        panel.add(selectedEstablishmentLabel, BorderLayout.NORTH);
        panel.add(listScrollPane, BorderLayout.CENTER);
        panel.add(quantityPanel, BorderLayout.SOUTH);
        panel.add(confirmSelectionButton, BorderLayout.EAST);
        panel.add(backButton, BorderLayout.WEST);

        // Agregar el panel al centro del menú
        add(panel, BorderLayout.CENTER);

        // Cargar los productos disponibles para el establecimiento seleccionado
        cargarProductosDisponibles();
    }

    public void setupWindowWithID(int orderId) {
        setLayout(new BorderLayout());

        // Panel para los componentes
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Etiqueta para mostrar el nombre del establecimiento seleccionado
        JLabel selectedEstablishmentLabel = new JLabel("Establecimiento seleccionado: " + selectedEstablishment.getNombre());

        // Modelo de lista para los productos disponibles
        listModel = new DefaultListModel<>();
        productsList = new JList<>(listModel);
        productsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(productsList);

        // Campo de texto para la cantidad
        JPanel quantityPanel = new JPanel(new BorderLayout());
        JLabel quantityLabel = new JLabel("Cantidad:");
        quantityField = new JTextField();
        quantityPanel.add(quantityLabel, BorderLayout.WEST);
        quantityPanel.add(quantityField, BorderLayout.CENTER);

        // Botón para confirmar la selección de un producto
        confirmSelectionButton = new JButton("Confirmar Selección");
        confirmSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = productsList.getSelectedIndex();
                if (selectedIndex != -1) {
                    try {
                        int quantity = Integer.parseInt(quantityField.getText());
                        if (quantity <= 0) {
                            throw new NumberFormatException();
                        }
                        Producto selectedProduct = availableProducts.get(selectedIndex);
                        selectedProduct.setCantidad(quantity);
                        Utils.SpecificListManipulation.updateCarrito(frameManager.getSelectedProducts(), selectedProduct);
                        frameManager.switchToMenu(new MakeOrderMenu(frameManager, orderId));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "Por favor, ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Botón para volver al menú anterior
        backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManager.switchToPreviousMenu();
            }
        });

        // Agregar componentes al panel
        panel.add(selectedEstablishmentLabel, BorderLayout.NORTH);
        panel.add(listScrollPane, BorderLayout.CENTER);
        panel.add(quantityPanel, BorderLayout.SOUTH);
        panel.add(confirmSelectionButton, BorderLayout.EAST);
        panel.add(backButton, BorderLayout.WEST);

        // Agregar el panel al centro del menú
        add(panel, BorderLayout.CENTER);

        // Cargar los productos disponibles para el establecimiento seleccionado
        cargarProductosDisponibles();
    }

    private void cargarProductosDisponibles() {
        availableProducts = selectedEstablishment.getProductos();
        for (Producto product : availableProducts) {
            listModel.addElement(product.getNombre());
        }
    }
}