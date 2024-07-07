/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Mario Lebrero García
 */
public class ProductoImpl implements Producto {
    
  @JsonIgnore
    private int id;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("precio")
    private double precio;
    @JsonProperty("cantidad")
    private int cantidad;
    @JsonProperty("establecimiento")
    private String establecimiento;

    public ProductoImpl() {
        this.id = 0;
        this.nombre = "";
        this.precio = 0;
        this.cantidad = 0;
        this.establecimiento = "";
    }
    
    

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
    public double getPrecio() {
        return precio;
    }

    @Override
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public int getCantidad() {
        return cantidad;
    }

    @Override
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String getEstablecimiento() {
        return establecimiento;
    }

    @Override
    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    @Override
    public String toString() {
        return "Producto: {" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", establecimiento=" + establecimiento + '}';
    }

}