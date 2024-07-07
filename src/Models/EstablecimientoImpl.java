/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mario Lebrero García
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoProducto")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BellezaImpl.class, name = "belleza"),
    @JsonSubTypes.Type(value = ComidaImpl.class, name = "comida"),
    @JsonSubTypes.Type(value = SupermercadoImpl.class, name = "supermercado"), // Agrega otras subclases para tipos de producto adicionales aquí si es necesario
})
public abstract class EstablecimientoImpl implements Establecimiento {
    
    protected int id;
    @JsonProperty("establecimiento")
    protected String nombre;
    @JsonProperty("direccion")
    protected String direccion;
    protected String tipoEstablecimiento;
    protected List<Producto> productos;

    protected EstablecimientoImpl() {
        this.id = 0;
        this.nombre = "";
        this.direccion = "";
        this.productos = new ArrayList<>();
    }

    public EstablecimientoImpl(Integer id, String nombre, String direccion, String tipoEstablecimiento, List<Producto> productos) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipoEstablecimiento = tipoEstablecimiento;
        this.productos = productos;
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
    public String getDireccion() {
        return direccion;
    }

    @Override
    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
    public String toString() {
        return "nombre=" + nombre + ", direccion=" + direccion + ", productos=" + productos;
    }

    public String getTipoEstablecimiento(){
        return tipoEstablecimiento;
    }
    
    public void setTipoEstablecimiento(String tipoEstablecimiento){
        this.tipoEstablecimiento = tipoEstablecimiento;
    }
    
    public void addProductoToProductos(Producto producto){
        this.getProductos().add(producto);
    }
}