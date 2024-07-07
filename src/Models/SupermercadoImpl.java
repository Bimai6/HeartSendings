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
@JsonTypeName("supermercado")
public class SupermercadoImpl extends EstablecimientoImpl implements Supermercado{

    @JsonProperty("tipoSupermercado")
    protected TipoSupermercado tipoSupermercado;

    public SupermercadoImpl() {
        this.tipoSupermercado = null;
    }

    @Override
    public TipoSupermercado getTipoSupermercado() {
        return tipoSupermercado;
    }

    @Override
    public void setTipoSupermercado(TipoSupermercado tipoSupermercado) {
        this.tipoSupermercado = tipoSupermercado;
    }

    @Override
    public void setTipoSupermercado(String tipoSupermercado) {
        this.tipoSupermercado = TipoSupermercado.valueOf(tipoSupermercado.toUpperCase());
    }

    @Override
    public String toString() {
        return "Supermercado: {" + super.toString() + " tipoSupermercado=" + tipoSupermercado + '}';
    }

}