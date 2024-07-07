package DAO;

import Models.Producto;
import Models.ProductoImpl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoProductoDAO extends BaseDAO {

    public PedidoProductoDAO(Connection connection, String nameTable) {
        super(connection, nameTable);
    }

    public void insertProductoPedido(int idProducto, int idPedido, int cantidad) {
        String insertQuery = "INSERT INTO " + this.tableName + " (id_producto, id_pedido, cantidad) VALUES (?, ?, ?)";
        PreparedStatement ps = this.getPreparedStatement(insertQuery);

        try {
            ps.setInt(1, idProducto);
            ps.setInt(2, idPedido);
            ps.setInt(3, cantidad);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Producto> getProductosByPedidoId(int pedidoId, EstablecimientoDAO establecimientoDAO) {
        List<Producto> productos = new ArrayList<>();
        String selectQuery = "SELECT p.id, p.nombre AS nombre_producto, p.precio, pp.cantidad, e.nombre AS nombre_establecimiento "
                + "FROM productos p "
                + "INNER JOIN pedidoProducto pp ON p.id = pp.id_producto "
                + "INNER JOIN establecimientos e ON p.id_establecimiento = e.id "
                + "WHERE pp.id_pedido = ?";
        PreparedStatement ps = this.getPreparedStatement(selectQuery);

        try {
            ps.setInt(1, pedidoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre_producto");
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad"); //está en la tabla pedidoproducto
                String nombreEstablecimiento = rs.getString("nombre_establecimiento"); //está en la tabla establecimiento

                Producto producto = new ProductoImpl();
                producto.setId(id);
                producto.setNombre(nombre);
                producto.setPrecio(precio);
                producto.setCantidad(cantidad);
                producto.setEstablecimiento(nombreEstablecimiento);

                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return productos;
    }

    public int getCantidadProductoEnPedido(int pedidoId, int productoId) {
        String query = "SELECT cantidad FROM " + this.tableName + " WHERE id_pedido = ? AND id_producto = ?";
        try (PreparedStatement ps = getPreparedStatement(query)) {
            ps.setInt(1, pedidoId);
            ps.setInt(2, productoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("cantidad");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isPedidoProductFound(int idPedido, int idProducto) {
        boolean b = true;
        try {
            String selectQuery = "SELECT * FROM " + this.tableName + " WHERE id_pedido=" + idPedido + " AND id_producto = " + idProducto + ";";
            ResultSet rs = this.statement.executeQuery(selectQuery);
            if (!rs.next()) {
                b = false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EstablecimientoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public void removeProductFromPedido(int pedidoId, int productoId, int quantity) {
        String checkQuery = "SELECT cantidad FROM " + this.tableName + " WHERE id_pedido = ? AND id_producto = ?";
        String updateQuery = "UPDATE " + this.tableName + " SET cantidad = cantidad - ? WHERE id_pedido = ? AND id_producto = ?";
        String deleteQuery = "DELETE FROM " + this.tableName + " WHERE id_pedido = ? AND id_producto = ?";
        String countQuery = "SELECT COUNT(*) AS count FROM " + this.tableName + " WHERE id_pedido = ?";
        String deletePedidoQuery = "DELETE FROM pedidos WHERE id = ?";

        try (PreparedStatement checkPs = getPreparedStatement(checkQuery); PreparedStatement updatePs = getPreparedStatement(updateQuery); PreparedStatement deletePs = getPreparedStatement(deleteQuery); PreparedStatement countPs = getPreparedStatement(countQuery); PreparedStatement deletePedidoPs = getPreparedStatement(deletePedidoQuery)) {

            checkPs.setInt(1, pedidoId);
            checkPs.setInt(2, productoId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("cantidad");
                if (currentQuantity > quantity) {
                    updatePs.setInt(1, quantity);
                    updatePs.setInt(2, pedidoId);
                    updatePs.setInt(3, productoId);
                    updatePs.executeUpdate();
                } else if (currentQuantity == quantity) {
                    deletePs.setInt(1, pedidoId);
                    deletePs.setInt(2, productoId);
                    deletePs.executeUpdate();
                }
            }

            countPs.setInt(1, pedidoId);
            ResultSet countRs = countPs.executeQuery();
            if (countRs.next()) {
                int count = countRs.getInt("count");
                if (count == 0) {
                    deletePedidoPs.setInt(1, pedidoId);
                    deletePedidoPs.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOneProductFromPedido(int pedidoId, int productoId, int quantity) {
        String checkQuery = "SELECT cantidad FROM " + this.tableName + " WHERE id_pedido = ? AND id_producto = ?";
        String updateQuery = "UPDATE " + this.tableName + " SET cantidad = cantidad + ? WHERE id_pedido = ? AND id_producto = ?";
        String insertQuery = "INSERT IGNORE INTO " + this.tableName + " (id_producto, id_pedido, cantidad) VALUES (?, ?, ?)";

        try (PreparedStatement checkPs = getPreparedStatement(checkQuery); PreparedStatement updatePs = getPreparedStatement(updateQuery); PreparedStatement insertPs = getPreparedStatement(insertQuery)) {

            checkPs.setInt(1, pedidoId);
            checkPs.setInt(2, productoId);
            ResultSet checkRs = checkPs.executeQuery();

            if (!checkRs.next()) {
                insertPs.setInt(1, productoId);
                insertPs.setInt(2, pedidoId);
                insertPs.setInt(3, quantity);
                insertPs.executeUpdate();
            } else {
                updatePs.setInt(1, quantity);
                updatePs.setInt(2, pedidoId);
                updatePs.setInt(3, productoId);
                updatePs.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateManyProductsFromPedido(int pedidoId, List<Producto> productos) {
        productos.forEach(producto -> this.updateOneProductFromPedido(pedidoId, producto.getId(), producto.getCantidad()));
    }
}
