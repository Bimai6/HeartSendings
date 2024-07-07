/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Models;


/**
 *
 * @author Usuario
 */
public interface Supermercado extends Establecimiento{

    TipoSupermercado getTipoSupermercado();

    void setTipoSupermercado(TipoSupermercado tipoSupermercado);

    void setTipoSupermercado(String tipoSupermercado);
    
}
