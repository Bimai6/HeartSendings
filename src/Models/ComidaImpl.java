/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;

/**
 *
 * @author Usuario
 */
@JsonTypeName("comida")
public class ComidaImpl extends EstablecimientoImpl implements Comida {

    @JsonProperty("tipoComida")
    protected TipoComida tipoComida;

    public ComidaImpl() {
        this.tipoComida = null;
    }

    @Override
    public TipoComida getTipoComida() {
        return tipoComida;
    }

    @Override
    public void setTipoComida(TipoComida tipoComida) {
        this.tipoComida = tipoComida;
    }

    @Override
    public void setTipoComida(String tipoComida) {
        this.tipoComida = TipoComida.valueOf(tipoComida.toUpperCase());
    }

    @Override
    public String toString() {
        return "Comida: {" + super.toString() + " tipoComida=" + tipoComida + '}';
    }

}