/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Usuario
 */
public class LoggedMenu extends Menu{
    
    public LoggedMenu(FrameManager frameManager) {
        super(frameManager);
        setupWindow();
    }

    @Override
    public void setupWindow() {
        setLayout(new BorderLayout());
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setPreferredSize(new Dimension(600, 400));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton profileButton = new JButton("Perfil");
        profileButton.addActionListener(e -> {
            frameManager.switchToMenu(new ProfileMenu(frameManager));
        });

        JButton ordersButton = new JButton("Pedidos");
        ordersButton.addActionListener(e -> {
            frameManager.switchToMenu(new OrdersMenu(frameManager));
        });

        buttonPanel.add(profileButton);
        buttonPanel.add(ordersButton);

        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        JLabel titleLabel = new JLabel("Elija el menú al que quiere acceder", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        backgroundPanel.add(titleLabel, BorderLayout.CENTER);

        add(backgroundPanel, BorderLayout.CENTER);
    }
}
