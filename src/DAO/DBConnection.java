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
public class DBConnection {
    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String JDBC_USER = "sqluser";
    private static final String JDBC_PASSWORD = "sqluserpw";
    private static final String DATABASE = "pedidosdb_mlg";
    private static Statement statement;
    private static Connection connection;
    
    public static Connection getConnection() {
        try {
            // Registrar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Intentar conectarse a la base de datos
            connection = DriverManager.getConnection(JDBC_URL + DATABASE, JDBC_USER, JDBC_PASSWORD);
            System.out.println("Conexión establecida con éxito a la base de datos " + DATABASE);
        } catch (SQLException e) {
            // Si la base de datos no existe, crearla y luego conectarse
            if (e.getErrorCode() == 1049) { // Código de error MySQL para "Unknown database"
                System.out.println("La base de datos no existe. Intentando crearla...");
                try {
                    connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                    statement = createStatement();
                    statement.executeUpdate("CREATE DATABASE " + DATABASE);
                    System.out.println("Base de datos " + DATABASE + " creada con éxito.");

                    // Cerrar la conexión inicial y volver a conectarse a la nueva base de datos
                    connection.close();
                    connection = DriverManager.getConnection(JDBC_URL + DATABASE, JDBC_USER, JDBC_PASSWORD);
                    System.out.println("Conexión establecida con éxito a la base de datos " + DATABASE);
                } catch (SQLException ex) {
                    System.err.println("Error al crear la base de datos: " + ex.getMessage());
                }
            } else {
                System.err.println("Error al establecer la conexión con la base de datos: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el controlador JDBC: " + e.getMessage());
        }
        return connection;
    }
    
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    private static Statement createStatement(){
        try {
            if(connection == null){
                getConnection();
            }
            statement = connection.createStatement();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return statement;
    }
    
    public static void createTables() {
        try{
            statement = createStatement();
            String columnsAddress = "CREATE TABLE IF NOT EXISTS address ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "street VARCHAR(255), "
                    + "number VARCHAR(10), "
                    + "city VARCHAR(255), "
                    + "postalCode VARCHAR(10))";

            String columnsUsers = "CREATE TABLE IF NOT EXISTS users ("
                    + "nickname VARCHAR(255) PRIMARY KEY, "
                    + "password VARCHAR(255) NOT NULL, "
                    + "id_direccion INT, "
                    + "FOREIGN KEY (id_direccion) REFERENCES address(id))";

            String columnsEstablecimientos = "CREATE TABLE IF NOT EXISTS establecimientos ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(255), "
                    + "direccion VARCHAR(255), "
                    + "tipoEstablecimiento VARCHAR(255))";

            String columnsProductos = "CREATE TABLE IF NOT EXISTS productos ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(255), "
                    + "precio DOUBLE, "
                    + "id_establecimiento INT, "
                    + "FOREIGN KEY (id_establecimiento) REFERENCES establecimientos(id))";

            String columnsPedidos = "CREATE TABLE IF NOT EXISTS pedidos ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "precio DOUBLE, "
                    + "estado VARCHAR(50), "
                    + "user_nickname VARCHAR(255), "
                    + "FOREIGN KEY (user_nickname) REFERENCES users(nickname))";
            
            String columnsPedidoProducto = "CREATE TABLE IF NOT EXISTS pedidoProducto ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "id_producto INT, "
                    + "id_pedido INT, "
                    + "cantidad INT, "
                    + "FOREIGN KEY (id_producto) REFERENCES productos(id), "
                    + "FOREIGN KEY (id_pedido) REFERENCES pedidos(id))";

            statement.executeUpdate("USE " + DATABASE);
            statement.executeUpdate(columnsAddress);
            statement.executeUpdate(columnsUsers);
            statement.executeUpdate(columnsEstablecimientos);
            statement.executeUpdate(columnsProductos);
            statement.executeUpdate(columnsPedidos);
            statement.executeUpdate(columnsPedidoProducto);

            System.out.println("Tablas creadas con éxito.");
        } catch (SQLException e) {
            System.out.println("Error en createTables: " + e.getMessage());
        }
    }
}
