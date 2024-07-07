/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Usuario
 */
public class EstablecimientosMenu extends Menu {

    private JButton backButton;
    private JComboBox<String> establishmentTypeComboBox;
    private int orderId;

    public EstablecimientosMenu(FrameManager frameManager) {
        super(frameManager);
        setupWindow();
    }

    public EstablecimientosMenu(FrameManager frameManager, int id) {
        super(frameManager);
        this.orderId = id;
        setupWindowWithID(orderId);
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para el tipo de establecimiento
        JPanel typePanel = new JPanel(new BorderLayout());
        JLabel establishmentTypeLabel = new JLabel("Seleccione el tipo de establecimiento:");
        establishmentTypeComboBox = new JComboBox<>(new String[]{"Supermercado", "Belleza", "Comida"});
        typePanel.add(establishmentTypeLabel, BorderLayout.NORTH);
        typePanel.add(establishmentTypeComboBox, BorderLayout.CENTER);

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton showEstablishmentsButton = new JButton("Mostrar Establecimientos Disponibles");
        showEstablishmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) establishmentTypeComboBox.getSelectedItem();
                if (selectedType != null) {
                    frameManager.switchToMenu(new SeleccionarEstablecimientosMenu(frameManager, selectedType));
                }
            }
        });
        buttonPanel.add(showEstablishmentsButton, BorderLayout.CENTER);

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
        mainPanel.add(typePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Agregar el panel principal al centro del menú
        add(mainPanel, BorderLayout.CENTER);
    }

    public void setupWindowWithID(int orderId) {
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para el tipo de establecimiento
        JPanel typePanel = new JPanel(new BorderLayout());
        JLabel establishmentTypeLabel = new JLabel("Seleccione el tipo de establecimiento:");
        establishmentTypeComboBox = new JComboBox<>(new String[]{"Supermercado", "Belleza", "Comida"});
        typePanel.add(establishmentTypeLabel, BorderLayout.NORTH);
        typePanel.add(establishmentTypeComboBox, BorderLayout.CENTER);

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton showEstablishmentsButton = new JButton("Mostrar Establecimientos Disponibles");
        showEstablishmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) establishmentTypeComboBox.getSelectedItem();
                if (selectedType != null) {
                    // Cambiar al menú siguiente (SeleccionarEstablecimientosMenu) con el ID del pedido
                    frameManager.switchToMenu(new SeleccionarEstablecimientosMenu(frameManager, orderId, selectedType));
                }
            }
        });
        buttonPanel.add(showEstablishmentsButton, BorderLayout.CENTER);

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
        mainPanel.add(typePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Agregar el panel principal al centro del menú
        add(mainPanel, BorderLayout.CENTER);
    }
}
