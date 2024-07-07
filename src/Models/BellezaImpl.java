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
@JsonTypeName("belleza")
public class BellezaImpl extends EstablecimientoImpl implements Belleza {

    @JsonProperty("tipoCentroBelleza")
    protected TipoBelleza tipoBelleza;

    public BellezaImpl() {
        this.tipoBelleza = null;
    }

    @Override
    public TipoBelleza getTipoBelleza() {
        return tipoBelleza;
    }

    @Override
    public void setTipoBelleza(TipoBelleza tipoBelleza) {
        this.tipoBelleza = tipoBelleza;
    }

    @Override
    public void setTipoBelleza(String tipoBelleza) {
        this.tipoBelleza = TipoBelleza.valueOf(tipoBelleza.toUpperCase());
    }

    @Override
    public String toString() {
        return "Belleza: {" + super.toString() + " tipoBelleza=" + tipoBelleza + '}';
    }

}
