/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Direccion;
import Models.Usuario;
import Models.UsuarioImpl;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class UsuarioDAO extends BaseDAO {

    public UsuarioDAO(Connection connection, String tableName) {
        super(connection, tableName);
    }

    public void saveUsuarios(List<Usuario> usuarios, DireccionDAO direccionDAO) {
        String insertSQL = "INSERT IGNORE INTO " + this.tableName + " (nickname, password, id_direccion) VALUES (?, ?, ?)";

        PreparedStatement ps = this.getPreparedStatement(insertSQL);
        try {
            for (Usuario user : usuarios) {
                ps.setString(1, user.getNickname());
                ps.setString(2, user.getPassword());
                ps.setInt(3, direccionDAO.getIdDireccionByAtributos(user.getDireccion().getCalle(), user.getDireccion().getNumCalle(), user.getDireccion().getCiudad(), user.getDireccion().getCodPostal()));
                ps.executeUpdate();
            }
            System.out.println("Lista de usuarios almacenada con éxito");
        } catch (SQLException ex) {
            System.out.println("No se ha podido almacenar la lista de usuarios");
        }
    }

    public void insertOneToUsuarios(Usuario usuario, DireccionDAO direccionDAO) {
    String insertUsuarioSQL = "INSERT IGNORE INTO " + this.tableName + " (nickname, password, id_direccion) VALUES (?, ?, ?)";

    // Insertar la dirección del usuario primero
    String insertDireccionSQL = "INSERT IGNORE INTO " + direccionDAO.getTableName() + " (street, number, city, postalCode) VALUES (?, ?, ?, ?)";
     PreparedStatement psDireccion = direccionDAO.getPreparedStatementWithGeneratedKeys(insertDireccionSQL); // Solicitar las claves generadas

    try {
        // Insertar la dirección
        psDireccion.setString(1, usuario.getDireccion().getCalle());
        psDireccion.setString(2, usuario.getDireccion().getNumCalle());
        psDireccion.setString(3, usuario.getDireccion().getCiudad());
        psDireccion.setString(4, usuario.getDireccion().getCodPostal());
        psDireccion.executeUpdate();

        // Obtener el ID asignado a la dirección recién insertada
        ResultSet rs = psDireccion.getGeneratedKeys();
        int idDireccion = -1;
        if (rs.next()) {
            idDireccion = rs.getInt(1);
        }

        // Insertar el usuario con el ID de la dirección correspondiente
        PreparedStatement psUsuario = this.getPreparedStatementWithGeneratedKeys(insertUsuarioSQL); // Solicitar las claves generadas
        psUsuario.setString(1, usuario.getNickname());
        psUsuario.setString(2, usuario.getPassword());
        psUsuario.setInt(3, idDireccion);
        psUsuario.executeUpdate();

        System.out.println("Nuevo usuario almacenado con éxito");
    } catch (SQLException ex) {
        System.out.println("No se ha podido almacenar el nuevo usuario: " + ex.getMessage());
    }
}

    public Usuario getUsuarioByNickname(String nickname, DireccionDAO direccionDAO) {
    Usuario usuario = null;
    try {
        String selectSQL = "SELECT * FROM " + this.tableName + " WHERE nickname=?";
        PreparedStatement ps = this.connection.prepareStatement(selectSQL);
        ps.setString(1, nickname);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            usuario = new UsuarioImpl();
            String username = rs.getString("nickname");
            String password = rs.getString("password");
            int idDireccion = rs.getInt("id_direccion");
            usuario.setNickname(username);
            usuario.setPassword(password);
            
            // Obtener la dirección correspondiente
            Direccion direccion = direccionDAO.getDireccionById(idDireccion);
            usuario.setDireccion(direccion);
        }
    } catch (SQLException ex) {
        System.out.println("Error al intentar buscar el usuario por su nombre en la base de datos" + ex.getMessage());
    }
    return usuario;
}
    
    public void modifyPassword(String nickname, String password){
        try {
            String query = "UPDATE " + this.tableName +" SET password='"+ password +"' WHERE nickname='"+ nickname +"'";
            System.out.println(query);
             this.statement.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Error modificando la contraseña del usuario "+ex);
        }
    }
}
