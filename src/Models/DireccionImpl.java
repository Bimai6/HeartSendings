/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import DAO.DireccionDAO;
import Exceptions.UsuarioException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class DireccionImpl implements Direccion {
    private int id;
    private String calle;
    private String numCalle;
    private String ciudad;
    private String codPostal;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getCalle() {
        return calle;
    }

    @Override
    public void setCalle(String calle) {
        this.calle = calle;
    }

    @Override
    public String getNumCalle() {
        return numCalle;
    }

    @Override
    public void setNumCalle(String numCalle) {
        this.numCalle = numCalle;
    }

    @Override
    public String getCiudad() {
        return ciudad;
    }

    @Override
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String getCodPostal() {
        return codPostal;
    }

    @Override
    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }
    
    public DireccionImpl(String direccion) {
        try {
            Utils.Validator.checkDirection(direccion);
            String[] direccionElements = direccion.split(", ");
            this.id = 0;
            this.calle = direccionElements[0];
            this.numCalle = direccionElements[1];
            this.ciudad = direccionElements[2];
            this.codPostal = direccionElements[3];
        } catch (UsuarioException ex) {
            System.out.println("El formato de la dirección no es válido");
        }
    }
    
    public DireccionImpl(int id){
        this.id = id;
        this.calle = "";
        this.numCalle = "";
        this.ciudad = "";
        this.codPostal = "";
    }
    
    public DireccionImpl(){
        this.id = 0;
        this.calle = "";
        this.numCalle = "";
        this.ciudad = "";
        this.codPostal = "";
    }

    @Override
    public String toString() {
        return "DireccionImpl{" + "id=" + id + ", calle=" + calle + ", numero=" + numCalle + ", ciudad=" + ciudad + ", codPostal=" + codPostal + '}';
    }
    
    
}
