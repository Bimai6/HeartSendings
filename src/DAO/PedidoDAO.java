/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.EstadoPedido;
import Models.Pedido;
import Models.PedidoImpl;
import Models.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class PedidoDAO extends BaseDAO {

    public PedidoDAO(Connection connection, String tableName) {
        super(connection, tableName);
    }

    public int insertPedidoAndGetId(EstadoPedido estadoPedido, String usuario, List<Producto> productos) {
    String insertPedidoQuery = "INSERT INTO pedidos (precio, estado, user_nickname) VALUES (?, ?, ?)";

    PreparedStatement psPedido = null;
    int pedidoId = -1;

    try {
        // Desactivar el modo de confirmación automática para poder controlar la transacción manualmente
        this.connection.setAutoCommit(false);

        // Preparar la consulta para insertar el pedido
        psPedido = this.getPreparedStatementWithGeneratedKeys(insertPedidoQuery);

        // Calcular el precio total del pedido
        double precioTotal = calcularPrecioTotal(productos);

        // Insertar el pedido
        psPedido.setDouble(1, precioTotal);
        psPedido.setString(2, estadoPedido.toString().toLowerCase());
        psPedido.setString(3, usuario);
        psPedido.executeUpdate();

        // Obtener el ID del pedido recién insertado
        ResultSet generatedKeys = psPedido.getGeneratedKeys();
        if (generatedKeys.next()) {
            pedidoId = generatedKeys.getInt(1);
        }

        // Confirmar la transacción
        this.connection.commit();
    } catch (SQLException e) {
        // Si ocurre algún error, hacer rollback de la transacción
        if (this.connection != null) {
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
    } finally {
        // Restaurar el modo de confirmación automática
        try {
            if (this.connection != null) {
                this.connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Cerrar los recursos
        if (psPedido != null) {
            try {
                psPedido.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return pedidoId;
}
    
    public void insertProductosToPivot(int pedidoId, List<Producto> productos, PedidoProductoDAO pedidoProductoDAO) {
    // Insertar los productos en la tabla pivot pedidoProducto
    for (Producto producto : productos) {
        // Insertar una fila en la tabla pedidoProducto asociando el producto al pedido
        pedidoProductoDAO.insertProductoPedido(producto.getId(), pedidoId, producto.getCantidad());
    }
}
    
    public double calcularPrecioTotal(List<Producto> productos) {
    double precioTotal = 0.0;
    for (Producto producto : productos) {
        // Multiplica el precio del producto por la cantidad y lo suma al precio total
        precioTotal += producto.getPrecio() * producto.getCantidad();
    }
    return precioTotal;
}
    

    public Pedido selectPedidoById(int id, String userNickname, PedidoProductoDAO pedidoProductoDAO, EstablecimientoDAO establecimientoDAO) {
        Pedido pedido = null;
        String selectQuery = "SELECT * FROM pedidos WHERE id = " + id + " AND user_nickname = '" + userNickname + "'";
        Statement statement = this.getPreparedStatement(selectQuery);
        ResultSet rs = null;

        try {
            rs = statement.executeQuery(selectQuery);

            // Si se encontró un pedido con el ID y el nombre de usuario especificados
            if (rs.next()) {
                // Obtener el estado del pedido y los productos asociados a través del PedidoProductoDAO
                EstadoPedido estadoPedido = EstadoPedido.valueOf(rs.getString("estado").toUpperCase());
                List<Producto> productos = pedidoProductoDAO.getProductosByPedidoId(id, establecimientoDAO);

                pedido = new PedidoImpl(id, productos, estadoPedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return pedido;
    }
    
    public void updateEstadoDelPedido(Pedido pedido){
        try {
            String updateEstado = "UPDATE " + this.tableName + " SET estado=? WHERE id=?";
            PreparedStatement ps = this.getPreparedStatement(updateEstado);
            
            ps.setString(1, String.valueOf(EstadoPedido.CANCELADO).toLowerCase());
            ps.setInt(2, pedido.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("No se ha podido actualizar el estado del producto");
        }
    }
}
