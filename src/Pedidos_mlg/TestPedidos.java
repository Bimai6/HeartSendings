/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pedidos_mlg;

import Controllers.FrameManager;
import DAO.DireccionDAO;
import DAO.UsuarioDAO;
import Models.Direccion;
import Models.Usuario;
import Utils.Validator;
import java.sql.*;
import java.util.List;
/**
 *
 * @author Usuario
 */
public class TestPedidos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            FrameManager frameManager = new FrameManager();
            frameManager.start();
            frameManager.setVisible(true);
        } catch (Exception ex) {
            System.out.println(ex);
        }  
    } 
}
