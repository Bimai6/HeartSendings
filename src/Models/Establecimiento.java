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
public interface Establecimiento {
    
    int getId();
    
    String getTipoEstablecimiento();

    String getDireccion();

    String getNombre();

    List<Producto> getProductos();
    
    void setId(int id);

    void setDireccion(String direccion);

    void setNombre(String nombre);

    void setProductos(List<Producto> productos);
    
    void setTipoEstablecimiento(String tipoEstablecimiento);
    
    void addProductoToProductos(Producto producto);
}
