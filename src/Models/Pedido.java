/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Models;

import java.util.List;

/**
 *
 * @author Usuario
 */
public interface Pedido {

    EstadoPedido getEstadoPedido();

    Integer getId();

    List<Producto> getProductos();

    void setEstadoPedido(EstadoPedido estadoPedido);

    void setId(Integer id);

    void setProductos(List<Producto> productos);
    
    double calculateTotalPrice();
}
