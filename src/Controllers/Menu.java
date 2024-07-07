/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Establecimiento;
import java.util.List;
import javax.swing.JPanel;

public abstract class Menu extends JPanel {
    protected transient FrameManager frameManager;
    protected String selectedType;
    protected Establecimiento establecimiento;
    protected int idPedido;

    protected Menu(FrameManager frameManager) {
        this.frameManager = frameManager;
    }
    
    public Menu(FrameManager frameManager, String selectedType) {
        this.frameManager = frameManager;
        this.selectedType = selectedType;
    }
    
    public Menu(FrameManager frameManager, Establecimiento establecimiento) {
        this.frameManager = frameManager;
        this.establecimiento = establecimiento;
    }
    
    public Menu(FrameManager frameManager, int id) {
        this.frameManager = frameManager;
        this.idPedido = id;
    }
    
    public Menu(FrameManager frameManager, int id, String selectedType) {
        this.frameManager = frameManager;
        this.selectedType = selectedType;
        this.idPedido = id;
    }
    public Menu(FrameManager frameManager, int id, Establecimiento establecimiento) {
        this.frameManager = frameManager;
        this.establecimiento = establecimiento;
        this.idPedido = id;
    }

    protected void switchToMenu(Menu menu) {
        frameManager.switchToMenu(menu);
    }

    protected void switchToPreviousMenu() {
        frameManager.switchToPreviousMenu();
    }

    protected abstract void setupWindow();
    
    protected void setupWindowWithID(int orderId) {
        // Este método será sobrescrito en las subclases según sea necesario
    }
}