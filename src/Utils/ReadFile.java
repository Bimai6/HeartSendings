/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Exceptions.UsuarioException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Usuario
 */
public class ReadFile {

    public static String readFile(String filePath) {

        BufferedReader br = null;
        String texto = "";
        try {

            br = new BufferedReader(new FileReader(new File(filePath)));

            String linea;
            while ((linea = br.readLine()) != null && linea.length() != 0) {
                Utils.Validator.checkUserFormer(linea);
                texto += "\n" + linea;
            }

            br.close();

        } catch (UsuarioException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return texto.trim();
    }
}
