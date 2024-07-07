/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Models;

/**
 *
 * @author Usuario
 */
public interface Producto {

    int getCantidad();

    String getEstablecimiento();

    int getId();

    String getNombre();

    double getPrecio();

    void setCantidad(int cantidad);

    void setEstablecimiento(String establecimiento);

    void setId(int id);

    void setNombre(String nombre);

    void setPrecio(double precio);
    
}
