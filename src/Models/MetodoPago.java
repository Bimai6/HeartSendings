/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Models;


/**
 *
 * @author Usuario
 */
public interface MetodoPago {

    int getNumTarjeta();

    int getPin();

    TipoTarjeta getTipoTarjeta();

    void setNumTarjeta(int numTarjeta);

    void setPin(int pin);

    void setTipoTarjeta(TipoTarjeta tipoTarjeta);
    
}
