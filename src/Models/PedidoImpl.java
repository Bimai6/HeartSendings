/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import DAO.PedidoDAO;
import DAO.PedidoProductoDAO;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class PedidoImpl implements Pedido {
    
    private Integer id;
    private List<Producto> productos;
    private EstadoPedido estadoPedido;
    private final double GASTOS_DE_ENVIO= 2.00;
  

    public PedidoImpl(List<Producto> productos, EstadoPedido estadoPedido, PedidoDAO pedidoDAO, String usuario, PedidoProductoDAO pedidoProductoDAO) {
        this.id= pedidoDAO.insertPedidoAndGetId(estadoPedido, usuario, productos);
        this.productos = productos;
        this.estadoPedido = estadoPedido;
        pedidoDAO.insertProductosToPivot(this.id, productos, pedidoProductoDAO);
    }
    
     public PedidoImpl(Integer id, List<Producto> productos, EstadoPedido estadoPedido) {
        this.id = id;
        this.productos = productos;
        this.estadoPedido = estadoPedido;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public List<Producto> getProductos() {
        return productos;
    }

    @Override
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    @Override
    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    @Override
    public String toString() {
        return "Pedido: {" + "id=" + id + ", productos=" + productos + ", estadoPedido=" + estadoPedido + '}';
    }
    
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Producto product : this.getProductos()) {
            total += product.getPrecio() * product.getCantidad();
        }
        total += GASTOS_DE_ENVIO;
        return total;
    }

}