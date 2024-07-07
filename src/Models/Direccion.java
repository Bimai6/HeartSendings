/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Models;

/**
 *
 * @author Usuario
 */
public interface Direccion {

    String getCalle();

    String getCiudad();

    String getCodPostal();

    int getId();

    String getNumCalle();

    void setCalle(String calle);

    void setCiudad(String ciudad);

    void setCodPostal(String codPostal);

    void setId(int id);

    void setNumCalle(String numCalle);
    
}
