/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

/**
 *
 * @author Usuario
 */
import DAO.DireccionDAO;
import DAO.EstablecimientoDAO;
import DAO.PedidoDAO;
import DAO.PedidoProductoDAO;
import DAO.ProductoDAO;
import DAO.UsuarioDAO;
import Models.*;
import java.awt.BorderLayout;
import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.util.Stack;
import java.util.*;
import java.util.stream.Collectors;

public class FrameManager {

    private JFrame frame;
    private Stack<JPanel> menuHistory;
    private BootMenu bootMenu;
    private Usuario currentUser;
    private List<List<Producto>> allProducts;
    private List<Belleza> establecimientosBellezaPrograma;
    private List<Supermercado> establecimientosSupermercadoPrograma;
    private List<Comida> establecimientosComidaPrograma;
    private List<Usuario> usuarios;
    private List<Direccion> direcciones;
    private List<Producto> selectedProducts; // Lista para almacenar los productos seleccionados
    private DireccionDAO direccionDAO;
    private UsuarioDAO usuarioDAO;
    private EstablecimientoDAO establecimientoDAO;
    private ProductoDAO productoDAO;
    private PedidoDAO pedidoDAO;
    private PedidoProductoDAO pedidoProductoDAO;
    private Connection connection;

    public FrameManager() {

        // Bloque de creación de listas de productos
        List<String> stringURLsProducto = Arrays.asList("http://demo4746359.mockable.io/productos/belleza", "http://demo4746359.mockable.io/productos/supermercado", "http://demo4746359.mockable.io/productos/comida");
        List<URL> urlsProducto = Utils.SpecificListManipulation.readVariousURL(stringURLsProducto);

        allProducts = new ArrayList<>();
        Utils.SpecificListManipulation.addUnknownNumberOfList(allProducts, urlsProducto);
        Utils.SpecificListManipulation.setIdToProducts(allProducts);

        // Bloque de creación de listas de establecimientos
        String stringURLEstablecimiento = "http://demo4746359.mockable.io/establecimientos";
        URL urlEstablecimiento = Utils.URLMapper.readAsURL(stringURLEstablecimiento);

        establecimientosBellezaPrograma = Utils.URLMapper.mapBellezas(urlEstablecimiento);
        establecimientosSupermercadoPrograma = Utils.URLMapper.mapSupermercados(urlEstablecimiento);
        establecimientosComidaPrograma = Utils.URLMapper.mapComidas(urlEstablecimiento);

        Utils.SpecificListManipulation.setListProductosBellezaToListBellezas(establecimientosBellezaPrograma, allProducts.get(0));
        Utils.SpecificListManipulation.setListProductosSupermercadoToListSupermercados(establecimientosSupermercadoPrograma, allProducts.get(1));
        Utils.SpecificListManipulation.setListProductosComidaToListComidas(establecimientosComidaPrograma, allProducts.get(2));
        
        Utils.SpecificListManipulation.setIdToEstablecimientos(establecimientosBellezaPrograma);
        Utils.SpecificListManipulation.setIdToEstablecimientos(establecimientosBellezaPrograma);
        Utils.SpecificListManipulation.setIdToEstablecimientos(establecimientosBellezaPrograma);

        //Bloque de creación para la lista de usuarios y las direcciones
        String s = Utils.ReadFile.readFile("src/Sources/usuarios.txt");
        usuarios = new ArrayList<>();
        Utils.SpecificListManipulation.addUnknowNumberOfUserToAList(usuarios, s);
        //Para tener una lista con las direcciones
        direcciones = usuarios.stream().map(Usuario::getDireccion).filter(Objects::nonNull).collect(Collectors.toList());
        Utils.SpecificListManipulation.setIdToDirections(direcciones);

        //Variable para gestionar los productos antes de meterlos en la base de datos
        selectedProducts = new ArrayList<>();

        //Configuración base del Frame
        frame = new JFrame("Heart Sendings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Establecer el tamaño deseado del JFrame
        frame.setLocationRelativeTo(null);
        menuHistory = new Stack<>();
        bootMenu = new BootMenu(this);

        //Bloque de conexión con base de datos
        connection = DAO.DBConnection.getConnection();
        DAO.DBConnection.createTables();

        direccionDAO = new DireccionDAO(connection, "address");
        direccionDAO.saveDireccion(direcciones);
        
        usuarioDAO = new UsuarioDAO(connection, "users");
        usuarioDAO.saveUsuarios(usuarios, direccionDAO);

        establecimientoDAO = new EstablecimientoDAO(connection, "establecimientos");
        establecimientoDAO.saveEstablecimientos(establecimientosBellezaPrograma, establecimientosComidaPrograma, establecimientosSupermercadoPrograma);
        
        productoDAO = new ProductoDAO(connection, "productos");
        productoDAO.saveProductos(allProducts, establecimientoDAO);
        
        pedidoDAO = new PedidoDAO(connection, "pedidos");
        pedidoProductoDAO = new PedidoProductoDAO(connection, "pedidoproducto");
    }

    public void start() {
        switchToMenu(bootMenu);
    }

    public void switchToMenu(JPanel menu) {
        // Ocultar el StartMenu si se muestra
        if (menu instanceof StartMenu) {
            if (!menuHistory.isEmpty() && menuHistory.peek() instanceof StartMenu) {
                StartMenu startMenu = (StartMenu) menuHistory.peek();
                startMenu.setVisible(false);
            }
        }

        // Eliminar cualquier menú existente
        if (!menuHistory.isEmpty()) {
            JPanel previousMenu = menuHistory.peek();
            frame.getContentPane().remove(previousMenu);
        }

        // Agregar el nuevo menú
        frame.getContentPane().add(menu, BorderLayout.CENTER);
        frame.pack();
        frame.revalidate();
        frame.repaint();

        // Agregar el nuevo menú al historial
        menuHistory.push(menu);
    }

    public void switchToPreviousMenu() {
        if (!menuHistory.isEmpty()) {
            menuHistory.pop();
            if (!menuHistory.isEmpty()) {
                JPanel previousMenu = menuHistory.peek();
                frame.getContentPane().removeAll();
                frame.getContentPane().add(previousMenu, BorderLayout.CENTER);
                frame.pack();
                frame.revalidate();
                frame.repaint();
            }
        }
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }

    public List<List<Producto>> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<List<Producto>> allProducts) {
        this.allProducts = allProducts;
    }

    public List<Belleza> getEstablecimientosBellezaPrograma() {
        return establecimientosBellezaPrograma;
    }

    public void setEstablecimientosBellezaPrograma(List<Belleza> establecimientosBellezaPrograma) {
        this.establecimientosBellezaPrograma = establecimientosBellezaPrograma;
    }

    public List<Supermercado> getEstablecimientosSupermercadoPrograma() {
        return establecimientosSupermercadoPrograma;
    }

    public void setEstablecimientosSupermercadoPrograma(List<Supermercado> establecimientosSupermercadoPrograma) {
        this.establecimientosSupermercadoPrograma = establecimientosSupermercadoPrograma;
    }

    public List<Comida> getEstablecimientosComidaPrograma() {
        return establecimientosComidaPrograma;
    }

    public void setEstablecimientosComidaPrograma(List<Comida> establecimientosComidaPrograma) {
        this.establecimientosComidaPrograma = establecimientosComidaPrograma;
    }

    public DireccionDAO getDireccionDAO() {
        return direccionDAO;
    }

    public void setDireccionDAO(DireccionDAO direccionDAO) {
        this.direccionDAO = direccionDAO;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public EstablecimientoDAO getEstablecimientoDAO() {
        return establecimientoDAO;
    }

    public void setEstablecimientoDAO(EstablecimientoDAO establecimientoDAO) {
        this.establecimientoDAO = establecimientoDAO;
    }

    public ProductoDAO getProductoDAO() {
        return productoDAO;
    }

    public void setProductoDAO(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public PedidoDAO getPedidoDAO() {
        return pedidoDAO;
    }

    public void setPedidoDAO(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    public PedidoProductoDAO getPedidoProductoDAO() {
        return pedidoProductoDAO;
    }

    public void setPedidoProductoDAO(PedidoProductoDAO pedidoProductoDAO) {
        this.pedidoProductoDAO = pedidoProductoDAO;
    }

    // Método para agregar productos seleccionados a la lista
    public void addSelectedProduct(Producto product) {
        selectedProducts.add(product);
    }

    // Método para obtener la lista de productos seleccionados
    public List<Producto> getSelectedProducts() {
        return selectedProducts;
    }

    // Método para limpiar la lista de productos seleccionados
    public void clearSelectedProducts() {
        selectedProducts.clear();
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
        if (!visible) {
            // Cierra la conexión cuando la ventana se oculte
            DAO.DBConnection.closeConnection(connection);
        }
    }

    void logMessage(String msg, String type) {
        switch (type) {
            case "confirmation":
                JOptionPane.showMessageDialog(null, msg, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "error":
                JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case "warning":
                JOptionPane.showMessageDialog(null, msg, "Advertencia", JOptionPane.WARNING_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, msg);
        }
    }
}
