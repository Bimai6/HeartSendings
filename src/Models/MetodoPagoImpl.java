/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;



/**
 *
 * @author Usuario
 */
public class MetodoPagoImpl implements MetodoPago {

    private int numTarjeta;
    private TipoTarjeta tipoTarjeta;
    private int pin;

    @Override
    public int getNumTarjeta() {
        return numTarjeta;
    }

    @Override
    public void setNumTarjeta(int numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    @Override
    public TipoTarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    @Override
    public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    @Override
    public int getPin() {
        return pin;
    }

    @Override
    public void setPin(int pin) {
        this.pin = pin;
    }

    public MetodoPagoImpl(int numTarjeta, TipoTarjeta tipoTarjeta, int pin) {
        this.numTarjeta = numTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "MetodoPago: {" + "numTarjeta=" + numTarjeta + ", tipoTarjeta=" + tipoTarjeta + ", pin=" + pin + '}';
    }

}