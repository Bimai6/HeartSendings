/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Establecimiento;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Usuario
 */
public class SeleccionarEstablecimientosMenu extends Menu {
    private JButton backButton;
    private JComboBox<String> establishmentListComboBox;
    private List<? extends Establecimiento> establishments;
    private int orderId;

    public SeleccionarEstablecimientosMenu(FrameManager frameManager, String selectedEstablishmentType) {
        super(frameManager);
        switch (selectedEstablishmentType) {
            case "Supermercado":
                establishments = frameManager.getEstablecimientoDAO().getEstablecimientoByTipo("Supermercado");
                break;
            case "Belleza":
                establishments = frameManager.getEstablecimientoDAO().getEstablecimientoByTipo("Belleza");
                break;
            case "Comida":
                establishments = frameManager.getEstablecimientoDAO().getEstablecimientoByTipo("Comida");
                break;
        }
        setupWindow();
    }
    
    public SeleccionarEstablecimientosMenu(FrameManager frameManager, int id, String selectedEstablishmentType) {
        super(frameManager);
        this.orderId = id;
        switch (selectedEstablishmentType) {
            case "Supermercado":
                establishments = frameManager.getEstablecimientoDAO().getEstablecimientoByTipo("Supermercado");
                break;
            case "Belleza":
                establishments = frameManager.getEstablecimientoDAO().getEstablecimientoByTipo("Belleza");
                break;
            case "Comida":
                establishments = frameManager.getEstablecimientoDAO().getEstablecimientoByTipo("Comida");
                break;
        }
        setupWindowWithID(orderId);
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para la selección de establecimiento
        JPanel selectionPanel = new JPanel(new BorderLayout());
        JLabel establishmentLabel = new JLabel("Seleccione un establecimiento:");
        establishmentListComboBox = new JComboBox<>(establishments.stream().map(Establecimiento::getNombre).toArray(String[]::new));
        selectionPanel.add(establishmentLabel, BorderLayout.NORTH);
        selectionPanel.add(establishmentListComboBox, BorderLayout.CENTER);

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEstablishmentName = (String) establishmentListComboBox.getSelectedItem();
                if (selectedEstablishmentName != null) {
                    // Buscar el establecimiento correspondiente en la lista de establecimientos
                    Establecimiento selectedEstablishment = null;
                    for (Establecimiento establishment : establishments) {
                        if (establishment.getNombre().equals(selectedEstablishmentName)) {
                            selectedEstablishment = establishment;
                            break;
                        }
                    }
                    if (selectedEstablishment != null) {
                        selectedEstablishment.setProductos(frameManager.getProductoDAO().getProductosById(selectedEstablishment.getId(), frameManager.getEstablecimientoDAO()));
                        frameManager.switchToMenu(new ProductosMenu(frameManager, selectedEstablishment));
                    }
                }
            }
        });
        buttonPanel.add(confirmButton, BorderLayout.CENTER);

        // Botón para volver al menú anterior
        backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Regresar al menú anterior
                frameManager.switchToPreviousMenu();
            }
        });
        buttonPanel.add(backButton, BorderLayout.SOUTH);

        // Agregar paneles al panel principal
        mainPanel.add(selectionPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar el panel principal al centro del menú
        add(mainPanel, BorderLayout.CENTER);
    }
    
    protected void setupWindowWithID(int orderId) {
        setLayout(new BorderLayout());

    // Panel principal
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Panel para la selección de establecimiento
    JPanel selectionPanel = new JPanel(new BorderLayout());
    JLabel establishmentLabel = new JLabel("Seleccione un establecimiento:");
    establishmentListComboBox = new JComboBox<>(establishments.stream().map(Establecimiento::getNombre).toArray(String[]::new));
    selectionPanel.add(establishmentLabel, BorderLayout.NORTH);
    selectionPanel.add(establishmentListComboBox, BorderLayout.CENTER);

    // Panel para los botones
    JPanel buttonPanel = new JPanel(new BorderLayout());
    JButton confirmButton = new JButton("Confirmar");
    confirmButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedEstablishmentName = (String) establishmentListComboBox.getSelectedItem();
            if (selectedEstablishmentName != null) {
                // Buscar el establecimiento correspondiente en la lista de establecimientos
                Establecimiento selectedEstablishment = null;
                for (Establecimiento establishment : establishments) {
                    if (establishment.getNombre().equals(selectedEstablishmentName)) {
                        selectedEstablishment = establishment;
                        break;
                    }
                }
                if (selectedEstablishment != null) {
                    // Cambiar al menú siguiente (ProductosMenu) con el ID del pedido
                    selectedEstablishment.setProductos(frameManager.getProductoDAO().getProductosById(selectedEstablishment.getId(), frameManager.getEstablecimientoDAO()));
                    frameManager.switchToMenu(new ProductosMenu(frameManager, orderId, selectedEstablishment));
                }
            }
        }
    });
    buttonPanel.add(confirmButton, BorderLayout.CENTER);

    // Botón para volver al menú anterior
    backButton = new JButton("Volver");
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Regresar al menú anterior
            frameManager.switchToPreviousMenu();
        }
    });
    buttonPanel.add(backButton, BorderLayout.SOUTH);

    // Agregar paneles al panel principal
    mainPanel.add(selectionPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Agregar el panel principal al centro del menú
    add(mainPanel, BorderLayout.CENTER);
    }
}
