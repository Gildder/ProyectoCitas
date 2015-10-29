package com.gildder.invenbras.gestionactivos.models;

/**
 * Created by gildder on 15/10/2015.
 */
public class Material extends Activo {
    private String nombre;
    private int cantidad;
    private String unidad;

    public Material(String nombre, int cantidad, String unidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public Material(int id, String caracteristicas, String tipo,  String modelo, String marca, String serie, String estado, String codigoTIC, String codigoPAT, String codigoAF, String codigoGER, String otroCodigo, String imagen, String observacion, String nombre, int cantidad, String unidad) {
        super(id, caracteristicas, tipo,  modelo, marca, serie, estado, codigoTIC, codigoPAT, codigoAF, codigoGER, otroCodigo, imagen, observacion);
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
