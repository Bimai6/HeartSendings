/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Models.*;
import Models.ProductoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for mapping data from URLs to objects.
 * 
 * @author Mario Lebrero García
 */
public class URLMapper {

    /**
     * List of URLs for matching.
     */
    private static final List<String> DATA = Arrays.asList("http://demo4746359.mockable.io/productos/belleza", "http://demo4746359.mockable.io/productos/supermercado", "http://demo4746359.mockable.io/productos/comida", "http://demo4746359.mockable.io/establecimientos");

    /**
     * Parses the provided string URL into a URL object.
     * 
     * @param stringUrl The URL string to parse.
     * @return The URL object if the string is a valid URL and exists in the list of product URLs, throw RuntimeException otherw.
     */
    public static URL readAsURL(String stringUrl) {
        try {
            if (DATA.contains(stringUrl)) {
                return new URL(stringUrl);
            } else {
                throw new IllegalArgumentException("URL is not in the database");
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * Maps the product data from the provided URL to a list of products.
     * 
     * @param url The URL containing the product data.
     * @return A list of products mapped from the URL data.
     */
    public static List<Producto> mapProductos(URL url) {
        List<Producto> productos = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            productos = Arrays.asList(mapper.readValue(url, ProductoImpl[].class));
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return productos;
    }

    /**
     * Maps the beauty products data from the provided URL to a list of beauty products.
     * 
     * @param url The URL containing the beauty products data.
     * @return A list of beauty products mapped from the URL data.
     */
    public static List<Belleza> mapBellezas(URL url) {
        List<Belleza> bellezas = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            EstablecimientoImpl[] establecimientosArray = mapper.readValue(url, EstablecimientoImpl[].class);
            for (EstablecimientoImpl establecimiento : establecimientosArray) {
                if (establecimiento instanceof Belleza) {
                    bellezas.add((Belleza) establecimiento);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return bellezas;
    }

    /**
     * Maps the food products data from the provided URL to a list of food products.
     * 
     * @param url The URL containing the food products data.
     * @return A list of food products mapped from the URL data.
     */
    public static List<Comida> mapComidas(URL url) {
        List<Comida> comidas = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            EstablecimientoImpl[] establecimientosArray = mapper.readValue(url, EstablecimientoImpl[].class);
            for (EstablecimientoImpl establecimiento : establecimientosArray) {
                if (establecimiento instanceof Comida) {
                    comidas.add((Comida) establecimiento);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return comidas;
    }

    /**
     * Maps the supermarket products data from the provided URL to a list of supermarket products.
     * 
     * @param url The URL containing the supermarket products data.
     * @return A list of supermarket products mapped from the URL data.
     */
    public static List<Supermercado> mapSupermercados(URL url) {
        List<Supermercado> supermercados = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            EstablecimientoImpl[] establecimientosArray = mapper.readValue(url, EstablecimientoImpl[].class);
            for (EstablecimientoImpl establecimiento : establecimientosArray) {
                if (establecimiento instanceof Supermercado) {
                    supermercados.add((Supermercado) establecimiento);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return supermercados;
    }
}