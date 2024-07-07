/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Usuario
 */
public class AddOrSubstractMenu extends Menu {
    private JButton addButton;
    private JButton removeButton;
    private JButton backButton;
    private int orderId;

    public AddOrSubstractMenu(FrameManager frameManager, int orderId) {
        super(frameManager);
        this.orderId = orderId;
        setupWindow();
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addButton = new JButton("Añadir Producto");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManager.switchToMenu(new MakeOrderMenu(frameManager, orderId));
            }
        });

        removeButton = new JButton("Quitar Producto");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManager.switchToMenu(new RemoveProductMenu(frameManager, orderId));
            }
        });
        
        backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameManager.switchToPreviousMenu();
            }
        });

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);
    }
}

