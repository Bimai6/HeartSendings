/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Models;

import java.io.File;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface Usuario {

    String getApellidos();

    Direccion getDireccion();

    String getDni();

    File getFotoPerfil();

    MetodoPago getMetodoPago();

    String getNickname();

    String getNombre();

    String getPassword();

    List<Pedido> getPedidos();
    
    Pedido getPedidoById(int id);

    int getTelefono();

    void setApellidos(String apellidos);

    void setDireccion(String direccion);
    
    void setDireccion(Direccion direccion);

    void setDni(String dni);

    void setFotoPerfil(File fotoPerfil);

    void setMetodoPago(MetodoPago metodoPago);

    void setNickname(String nickname);

    void setNombre(String nombre);

    void setPassword(String password);

    void setPedidos(List<Pedido> pedidos);
    
    void addPedido(Pedido pedido);

    void setTelefono(int telefono);
    
}
