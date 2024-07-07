/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import DAO.DireccionDAO;
import DAO.UsuarioDAO;
import Exceptions.UsuarioException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class UsuarioImpl implements Usuario {

    private String nickname;
    private String password;
    private Direccion direccion;
    private String nombre;
    private String apellidos;
    private String dni;
    private int telefono;
    private MetodoPago metodoPago;
    private File fotoPerfil;
    private List<Pedido> pedidos;

    public UsuarioImpl(String constructor) {
        String[] constructorElements = constructor.split(";");

        try {
            Utils.Validator.checkNickname(constructorElements[0]);
            Utils.Validator.checkDirection(constructorElements[1]);
            Utils.Validator.checkPassword(constructorElements[2]);
            this.nickname = constructorElements[0];
            this.direccion = new DireccionImpl(constructorElements[1]);
            this.password = constructorElements[2];
            this.pedidos = new ArrayList<>();
        } catch (UsuarioException ex) {
            throw new RuntimeException(ex);
        }

    }

    public UsuarioImpl(String nickname, String password, String calle, String numCalle, String ciudad, String codPostal, DireccionDAO direccionDAO, UsuarioDAO usuarioDAO) throws UsuarioException {

        String direction = calle + ", " + numCalle + ", " + ciudad + ", " + codPostal;
        try {
            Utils.Validator.checkSignin(nickname, password, usuarioDAO, direccionDAO);
            Utils.Validator.checkNickname(nickname);
            Utils.Validator.checkPassword(password);
            this.nickname = nickname;
            this.direccion = new DireccionImpl(direction);
            this.password = password;
            this.pedidos = new ArrayList<>();
            usuarioDAO.insertOneToUsuarios(this, direccionDAO);
        } catch (UsuarioException ex) {
            throw ex;
        }
        
    }

    public UsuarioImpl(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.pedidos = new ArrayList<>();
    }
    
    public UsuarioImpl() {
        this.nickname = "";
        this.password = "";
        this.pedidos = new ArrayList<>();
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        try {
            Utils.Validator.checkNickname(nickname);
            this.nickname = nickname;
        } catch (UsuarioException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        try {
            Utils.Validator.checkPassword(password);
            this.password = password;
        } catch (UsuarioException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Direccion getDireccion() {
        return direccion;
    }

    @Override
    public void setDireccion(String direccion) {
        this.direccion = new DireccionImpl(direccion);
    }
    
    @Override
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getApellidos() {
        return apellidos;
    }

    @Override
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String getDni() {
        return dni;
    }

    @Override
    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public int getTelefono() {
        return telefono;
    }

    @Override
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    @Override
    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public File getFotoPerfil() {
        return fotoPerfil;
    }

    @Override
    public void setFotoPerfil(File fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    @Override
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
    public void addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    public Pedido getPedidoById(int id){
        return this.pedidos.stream().filter(pedido -> pedido.getId() == id).findFirst().get();
    }
    
    @Override
    public String toString() {
        return "Usuario: {" + "nickname=" + nickname + ", password=" + password + ", direccion=" + direccion + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", telefono=" + telefono + ", metodoPago=" + metodoPago + ", fotoPerfil=" + fotoPerfil + ", pedidos=" + pedidos + '}';
    }

}
