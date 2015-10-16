package com.gildder.invenbras.gestionactivos.models;

/**
 * Created by gildder on 15/10/2015.
 */
public class MaterialCantidad extends Activo {
    private String caracteristicas;
    private int cantidad;
    private String unidad;

    public MaterialCantidad(String caracteristicas, int cantidad, String unidad) {
        this.caracteristicas = caracteristicas;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public MaterialCantidad(int id, String tipo,  String modelo, String marca, String serie, String estado, String codigoTIC, String codigoPAT, String codigoAF, String codigoGER, String otroCodigo, String imagen, String observacion, String caracteristicas, int cantidad, String unidad) {
        super(id, tipo,  modelo, marca, serie, estado, codigoTIC, codigoPAT, codigoAF, codigoGER, otroCodigo, imagen, observacion);
        this.caracteristicas = caracteristicas;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
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
