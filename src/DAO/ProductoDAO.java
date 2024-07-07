/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Belleza;
import Models.Comida;
import Models.Establecimiento;
import Models.Producto;
import Models.ProductoImpl;
import Models.Supermercado;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class ProductoDAO extends BaseDAO {

    public ProductoDAO(Connection connection, String tableName) {
        super(connection, tableName);
    }

    public void saveProductos(List<List<Producto>> listasProducto, EstablecimientoDAO establecimientoDAO) {
        String insertSQL = "INSERT IGNORE INTO " + this.tableName + " (nombre, precio, id_establecimiento) VALUES (?, ?, ?)";

        try (PreparedStatement ps = this.getPreparedStatement(insertSQL)) {
            for (List<Producto> listaProducto : listasProducto) {
                for (Producto producto : listaProducto) {
                    int establecimientoId = establecimientoDAO.getIdEstablecimientoByName(
                        producto.getEstablecimiento());

                    if (establecimientoId != 0 && !isProductoFound(producto.getNombre(), establecimientoId)) {
                        ps.setString(1, producto.getNombre());
                        ps.setDouble(2, producto.getPrecio());
                        ps.setInt(3, establecimientoId);
                        ps.executeUpdate();
                    }
                }
            }
            System.out.println("Lista de productos almacenada con éxito");
        } catch (SQLException ex) {
            System.out.println("No se ha podido almacenar la lista de productos: " + ex.getMessage());
        }
    }

    public List<Producto> getProductosById(int idEstablecimiento, EstablecimientoDAO establecimientoDAO) {
        List<Producto> productos = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + this.tableName + " WHERE id_establecimiento=?";

        try (PreparedStatement ps = this.connection.prepareStatement(selectQuery)) {
            ps.setInt(1, idEstablecimiento);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new ProductoImpl();
                    producto.setId(rs.getInt("id"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getDouble("precio"));

                    // Recuperar el nombre del establecimiento desde EstablecimientoDAO
                    Establecimiento establecimiento = establecimientoDAO.getEstablecimientoById(idEstablecimiento);
                    if (establecimiento != null) {
                        producto.setEstablecimiento(establecimiento.getNombre());
                    }

                    productos.add(producto);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar buscar los productos por ID del establecimiento: " + ex.getMessage());
        }
        return productos;
    }

    public boolean isProductoFound(String nombre, int idEstablecimiento) {
        boolean b = true;
        try {
            String selectQuery = "SELECT * FROM " + this.tableName
                    + " WHERE nombre='" + nombre + "' AND id_establecimiento = " + idEstablecimiento + ";";
            ResultSet rs = this.statement.executeQuery(selectQuery);
            if (!rs.next()) {
                b = false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EstablecimientoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
}
