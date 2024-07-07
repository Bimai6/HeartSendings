/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Direccion;
import Models.DireccionImpl;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class DireccionDAO extends BaseDAO {

    public DireccionDAO(Connection connection, String tableName) {
        super(connection, tableName);
    }

    public void saveDireccion(List<Direccion> direcciones) {
        String insertQuery = "INSERT IGNORE INTO " + this.tableName + " (street, number, city, postalCode) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = this.getPreparedStatement(insertQuery);

        try {
            for (Direccion address : direcciones) {
                if (!isDireccionFound(address.getCalle(), address.getNumCalle(), address.getCiudad(), address.getCodPostal())) {
                    ps.setString(1, address.getCalle());
                    ps.setString(2, address.getNumCalle());
                    ps.setString(3, address.getCiudad());
                    ps.setString(4, address.getCodPostal());
                    ps.executeUpdate();
                }
            }
            System.out.println("Lista de direcciones almacenada con éxito");
        } catch (SQLException ex) {
            System.out.println("Error al almacenar la lista de direcciones");
        }
    }

    public void insertOneDireccion(Direccion address) {
        String insertQuery = "INSERT IGNORE INTO " + this.tableName + " (street, number, city, postalCode) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = this.getPreparedStatement(insertQuery);

        try {

            ps.setString(1, address.getCalle());
            ps.setString(2, address.getNumCalle());
            ps.setString(3, address.getCiudad());
            ps.setString(4, address.getCodPostal());
            ps.executeUpdate();

            System.out.println("Nueva dirección almacenada con éxito");
        } catch (SQLException ex) {
            System.out.println("Error al almacenar la lista de direcciones");
        }
    }

    public void modifyAddress(String direccion, int id) {
        try {
            String[] direccionElements = direccion.split(", ");

            // Consulta para actualizar la calle
            String updateCalle = "UPDATE " + this.tableName + " SET street=? WHERE id=?";
            PreparedStatement psCalle = this.getPreparedStatement(updateCalle);
            psCalle.setString(1, direccionElements[0]);
            psCalle.setInt(2, id);
            psCalle.executeUpdate();

            // Consulta para actualizar el número
            String updateNumero = "UPDATE " + this.tableName + " SET number=? WHERE id=?";
            PreparedStatement psNumero = this.getPreparedStatement(updateNumero);
            psNumero.setString(1, direccionElements[1]);
            psNumero.setInt(2, id);
            psNumero.executeUpdate();

            // Consulta para actualizar la ciudad
            String updateCiudad = "UPDATE " + this.tableName + " SET city=? WHERE id=?";
            PreparedStatement psCiudad = this.getPreparedStatement(updateCiudad);
            psCiudad.setString(1, direccionElements[2]);
            psCiudad.setInt(2, id);
            psCiudad.executeUpdate();

            // Consulta para actualizar el código postal
            String updateCodPostal = "UPDATE " + this.tableName + " SET postalCode=? WHERE id=?";
            PreparedStatement psCodPostal = this.getPreparedStatement(updateCodPostal);
            psCodPostal.setString(1, direccionElements[3]);
            psCodPostal.setInt(2, id);
            psCodPostal.executeUpdate();

            System.out.println("Dirección modificada con éxito");
        } catch (SQLException ex) {
            System.out.println("No se ha podido modificar la dirección del usuario: " + ex.getMessage());
        }
    }

    public Direccion getDireccionById(int id) {
        Direccion direccion = null;
        try {
            String selectSQL = "SELECT * FROM " + this.tableName + " WHERE id=?";
            PreparedStatement ps = this.connection.prepareStatement(selectSQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                direccion = new DireccionImpl();
                direccion.setId(rs.getInt("id"));
                direccion.setCalle(rs.getString("street"));
                direccion.setNumCalle(rs.getString("number"));
                direccion.setCiudad(rs.getString("city"));
                direccion.setCodPostal(rs.getString("postalCode"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar buscar la dirección por su ID en la base de datos: " + ex.getMessage());
        }
        return direccion;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isDireccionFound(String calle, String numeroCalle, String ciudad, String codPostal) {
        boolean b = true;
        try {
            String selectQuery = "SELECT * FROM " + this.tableName
                    + " WHERE street='" + calle + "' AND number = '" + numeroCalle
                    + "' AND city='" + ciudad + "' AND postalCode='" + codPostal + "';";
            ResultSet rs = this.statement.executeQuery(selectQuery);
            if (!rs.next()) {
                b = false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EstablecimientoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public int getIdDireccionByAtributos(String calle, String numCalle, String ciudad, String codPostal) {
        int idDireccion = -1;

        try {
            String selectSQL = "SELECT id FROM " + this.tableName + " WHERE street=? AND number=? AND city=? AND postalCode=?";
            PreparedStatement ps = this.connection.prepareStatement(selectSQL);
            ps.setString(1, calle);
            ps.setString(2, numCalle);
            ps.setString(3, ciudad);
            ps.setString(4, codPostal);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idDireccion = rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar buscar la dirección por sus atributos en la base de datos: " + ex.getMessage());
        }

        return idDireccion;
    }
}
