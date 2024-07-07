/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Models.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Usuario
 */
public class SpecificListManipulation {

    public static int incremento = 1;

    public static List<URL> readVariousURL(List<String> stringURLS) {
        List<URL> urls = new ArrayList<>();
        stringURLS.forEach(string -> urls.add(Utils.URLMapper.readAsURL(string)));
        return urls;
    }

    public static void addUnknownNumberOfList(List<List<Producto>> listOfList, List<URL> urls) {
        urls.stream().forEach(url -> listOfList.add(Utils.URLMapper.mapProductos(url)));
    }

    public static void addUnknowNumberOfUserToAList(List<Usuario> users, String list) {
        List<String> listWithN = Arrays.asList(list.split("\n"));
        listWithN.stream().forEach(line -> users.add(new UsuarioImpl(line)));
    }

    public static void setListProductosBellezaToListBellezas(List<Belleza> establecimientos, List<Producto> products) {
        establecimientos.forEach(establecimiento -> {
            String nombreEstablecimiento = establecimiento.getNombre();
            List<Producto> productos = products.stream()
                    .filter(producto -> producto.getEstablecimiento().equals(nombreEstablecimiento))
                    .collect(Collectors.toList());
            establecimiento.setProductos(productos);
        });
    }

    public static void setListProductosSupermercadoToListSupermercados(List<Supermercado> establecimientos, List<Producto> products) {
        establecimientos.forEach(establecimiento -> {
            String nombreEstablecimiento = establecimiento.getNombre();
            List<Producto> productos = products.stream()
                    .filter(producto -> producto.getEstablecimiento().equals(nombreEstablecimiento))
                    .collect(Collectors.toList());
            establecimiento.setProductos(productos);
        });
    }

    public static void setListProductosComidaToListComidas(List<Comida> establecimientos, List<Producto> products) {
        establecimientos.forEach(establecimiento -> {
            String nombreEstablecimiento = establecimiento.getNombre();
            List<Producto> productos = products.stream()
                    .filter(producto -> producto.getEstablecimiento().equals(nombreEstablecimiento))
                    .collect(Collectors.toList());
            establecimiento.setProductos(productos);
        });
    }

    public static void setIdToProducts(List<List<Producto>> listasProductos) {
        for (List<Producto> listaProducto : listasProductos) {
            for (Producto producto : listaProducto) {
                producto.setId(incremento);
                incremento++;
            }
        }
    }

    public static void setIdToDirections(List<Direccion> direcciones) {
        for (Direccion direccion : direcciones) {
            direccion.setId(incremento);
            incremento++;
        }
    }

    public static void setIdToEstablecimientos(List<? extends Establecimiento> establecimientos) {
        for (Establecimiento establecimiento : establecimientos) {
            establecimiento.setId(incremento);
            incremento++;
        }
    }

    public static void setTipoEstablecimientoToAList(List<? extends Establecimiento> establecimientos, String tipoEstablecimiento) {
        establecimientos.stream().forEach(establecimiento -> establecimiento.setTipoEstablecimiento(tipoEstablecimiento));
    }

    public static void updateCarrito(List<Producto> selectedProducts, Producto productoPorSeleccionar) {
        boolean productoActualizado = selectedProducts.stream()
                .filter(producto -> producto.getNombre().equals(productoPorSeleccionar.getNombre()) && producto.getEstablecimiento().equals(productoPorSeleccionar.getEstablecimiento()))
                .findFirst()
                .map(producto -> {
                    producto.setCantidad(producto.getCantidad() + productoPorSeleccionar.getCantidad());
                    return true;
                })
                .orElse(false);

        if (!productoActualizado) {
            selectedProducts.add(productoPorSeleccionar);
        }
    }
}
