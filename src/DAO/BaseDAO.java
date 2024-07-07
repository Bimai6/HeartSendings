/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;

/**
 *
 * @author Usuario
 */
public abstract class BaseDAO {

    protected Connection connection;
    protected Statement statement;
    protected String tableName;

    public BaseDAO(Connection connection, String tableName) {
        try {
            this.connection = connection;
            this.statement = connection.createStatement();
            this.tableName = tableName;
        } catch (SQLException ex) {
            System.out.println("Error en conexión en el constructor de BaseDAO");
        }
    }

    public PreparedStatement getPreparedStatement(String sqlQuery) {
        PreparedStatement ps = null;
        try {
            ps = this.connection.prepareStatement(sqlQuery);

        } catch (SQLException ex) {
            System.out.println("Error en la query para el prepareStatement" + ex.getMessage());
        }
        return ps;
    }

    public PreparedStatement getPreparedStatementWithGeneratedKeys(String sqlQuery) {
        PreparedStatement ps = null;
        try {
            ps = this.connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            System.out.println("Error en la query para el prepareStatement" + ex.getMessage());
        }
        return ps;
    }
}
