/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import DAO.DireccionDAO;
import DAO.UsuarioDAO;
import Exceptions.PedidoException;
import Exceptions.UsuarioException;
import Models.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Mario Lebrero García
 */

public class Validator {

    private static final String REGEXNICKNAME = "^[a-zA-Z0-9_]{5,20}$";
    private static final String REGEXPASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,16}$";
    private static final String REGEXDIRECTION = "^([a-zA-Z\\p{L}]+\\s?){2,},\\s*\\d+,\\s*([a-zA-Z\\p{L}]+\\s?){1,},\\s*\\d{5}$";
    private static final String REGEXUSER = "^([^;]+);([^,]+),([^,]+),([^,]+),([^;]+);([^;]+)$";
    
    
    public static void checkLogin(String nickname, String password, UsuarioDAO usuarioDAO, DireccionDAO direccionDAO) throws UsuarioException {
        Usuario usuario = usuarioDAO.getUsuarioByNickname(nickname, direccionDAO);
        if(usuario == null){
            throw new UsuarioException("El nick de usuario proporcionado no coincide con ninguno existente");
        }else if(!usuario.getPassword().equals(password)){
            throw new UsuarioException("Contraseña incorrecta para el usuario: " + nickname);
        }
    }

    public static void checkSignin(String nickname, String password, UsuarioDAO usuarioDAO, DireccionDAO direccionDAO) throws UsuarioException {
        if (usuarioDAO.getUsuarioByNickname(nickname, direccionDAO) != null) {
            throw new UsuarioException("Este nick de usuario ya existe.");
        }

        try {
            checkNickname(nickname);
            checkPassword(password);
        } catch (UsuarioException ex) {
            System.out.println(ex);
        }
    }

    public static void checkDirection(String direccion) throws UsuarioException {
        if (!direccion.matches(REGEXDIRECTION)) {
            throw new UsuarioException("Direction format isn't valid");
        }
    }

    public static void checkUserFormer(String lineaUsuario) throws UsuarioException {
        if (!lineaUsuario.matches(REGEXUSER)) {
            throw new UsuarioException("User can't be formed");
        }
    }

    public static void checkPassword(String password) throws UsuarioException {
        if (!password.matches(REGEXPASSWORD)) {
            throw new UsuarioException(" La contraseña es incorrecta,"
                    + "debe contener al menos un dígito, una minúscula y una mayúscula (no puede tener otros símbolos),"
                    + " y una longitud de entre 8 y 16 caracteres");
        }
    }

    public static void checkNickname(String nickname) throws UsuarioException {
        if (!nickname.matches(REGEXNICKNAME)) {
            throw new UsuarioException("El nick de usuario tiene un formato incorrecto,"
                    + " debe contener únicamente letras mayúsculas"
                    + " o minúsculas, números, o guiones bajos, y una longitud de entre 5 y 20 caracteres.");
        }
    }
    
    public static void checkSelectedProductsIsNotEmpty(List <Producto> selectedProducts) throws NullPointerException{
        if(selectedProducts.isEmpty()){
            throw new NullPointerException("No ha seleccionado ningún producto");
        }
    }
    
    public static void isValidOrder(List <Pedido> pedidos, int id) throws PedidoException{
        if(!pedidos.stream().anyMatch(pedido ->pedido.getId() == id && pedido.getEstadoPedido().equals(EstadoPedido.PENDIENTE))){
            throw new PedidoException("No hay ningún pedido pendiente con ese ID");
        }
    }
}
