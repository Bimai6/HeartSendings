/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Belleza;
import Models.BellezaImpl;
import Models.Comida;
import Models.ComidaImpl;
import Models.Establecimiento;
import Models.Supermercado;
import Models.SupermercadoImpl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class EstablecimientoDAO extends BaseDAO {

    public EstablecimientoDAO(Connection connection, String tableName) {
        super(connection, tableName);
    }

    public void saveEstablecimientos(List<Belleza> eBellezas, List<Comida> eComidas, List<Supermercado> eSupermercados) {
        String insertSQL = "INSERT IGNORE INTO " + this.tableName + " (nombre, direccion, tipoEstablecimiento) VALUES (?, ?, ?)";
        PreparedStatement ps = this.getPreparedStatement(insertSQL);
        boolean error = false;

        for (Belleza eBelleza : eBellezas) {
            if (!this.isEstablecimientoFound(eBelleza.getNombre(), eBelleza.getDireccion())) {
                try {
                    ps.setString(1, eBelleza.getNombre());
                    ps.setString(2, eBelleza.getDireccion());
                    ps.setString(3, "Belleza");
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Error en el almacenamiento de la lista de establecimiento para las filas de Belleza");
                    error = true;
                }
            }
        }

        for (Comida eComida : eComidas) {
            if (!this.isEstablecimientoFound(eComida.getNombre(), eComida.getDireccion())) {
                try {
                    ps.setString(1, eComida.getNombre());
                    ps.setString(2, eComida.getDireccion());
                    ps.setString(3, "Comida");
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Error en el almacenamiento de la lista de establecimiento para las filas de Comida");
                    error = true;
                }
            }
        }

        for (Supermercado eSupermercado : eSupermercados) {
            if (!this.isEstablecimientoFound(eSupermercado.getNombre(), eSupermercado.getDireccion())) {
                try {
                    ps.setString(1, eSupermercado.getNombre());
                    ps.setString(2, eSupermercado.getDireccion());
                    ps.setString(3, "Supermercado");
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Error en el almacenamiento de la lista de establecimiento para las filas de Supermercado");
                    error = true;
                }
            }
        }
        if (!error) {
            System.out.println("Lista de establecimientos almacenada con éxito");
        }
    }

    public Establecimiento getEstablecimientoById(int id) {
        Establecimiento establecimiento = null;
        String selectSQL = "SELECT * FROM " + this.tableName + " WHERE id=?";

        try (PreparedStatement ps = this.connection.prepareStatement(selectSQL)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tipoEstablecimiento = rs.getString("tipoEstablecimiento");
                    establecimiento = createEstablecimientoInstance(tipoEstablecimiento);
                    if (establecimiento != null) {
                        establecimiento.setId(rs.getInt("id"));
                        establecimiento.setNombre(rs.getString("nombre"));
                        establecimiento.setDireccion(rs.getString("direccion"));
                        establecimiento.setTipoEstablecimiento(rs.getString("tipoEstablecimiento"));
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar buscar el establecimiento por ID: " + ex.getMessage());
        }
        return establecimiento;
    }

    private Establecimiento createEstablecimientoInstance(String tipoEstablecimiento) {
        switch (tipoEstablecimiento) {
            case "Belleza":
                return new BellezaImpl();
            case "Comida":
                return new ComidaImpl();
            case "Supermercado":
                return new SupermercadoImpl();
            default:
                return null;
        }
    }

    public List<Establecimiento> getEstablecimientoByTipo(String tipoEstablecimiento) {
        List<Establecimiento> establecimientos = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + this.tableName + " WHERE tipoEstablecimiento=?";

        try (PreparedStatement ps = this.connection.prepareStatement(selectSQL)) {
            ps.setString(1, tipoEstablecimiento);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Establecimiento establecimiento = createEstablecimientoInstance(tipoEstablecimiento);
                    if (establecimiento != null) {
                        establecimiento.setId(rs.getInt("id"));
                        establecimiento.setNombre(rs.getString("nombre"));
                        establecimiento.setDireccion(rs.getString("direccion"));
                        establecimiento.setTipoEstablecimiento(rs.getString("tipoEstablecimiento"));
                        establecimientos.add(establecimiento);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar buscar establecimientos por tipo: " + ex.getMessage());
        }
        return establecimientos;
    }

    public boolean isEstablecimientoFound(String nombre, String direccion) {
        boolean b = true;
        try {
            String selectQuery = "SELECT * FROM " + this.tableName + " WHERE nombre='" + nombre + "' AND direccion = '" + direccion + "';";
            ResultSet rs = this.statement.executeQuery(selectQuery);
            if (!rs.next()) {
                b = false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EstablecimientoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public int getIdEstablecimientoByName(String nombre) {
        int idEstablecimiento = -1;
        String selectSQL = "SELECT id FROM establecimientos WHERE nombre = ?";
        try (PreparedStatement ps = this.connection.prepareStatement(selectSQL)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idEstablecimiento = rs.getInt("id");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar buscar el establecimiento por nombre y dirección en la base de datos: " + ex.getMessage());
        }
        return idEstablecimiento;
    }
}
