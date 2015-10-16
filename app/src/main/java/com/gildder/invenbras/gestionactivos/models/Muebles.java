package com.gildder.invenbras.gestionactivos.models;

/**
 * Created by gildder on 15/10/2015.
 */
public class Muebles extends Activo {
    private float alto;
    private float ancho;
    private float profundidad;
    private String nroMueble;
    private String color;

    public Muebles(float alto, float ancho, float profundidad, String nroMueble, String color) {
        this.alto = alto;
        this.ancho = ancho;
        this.profundidad = profundidad;
        this.nroMueble = nroMueble;
        this.color = color;
    }

    public Muebles(int id, String tipo, String modelo, String marca, String serie, String estado, String codigoTIC, String codigoPAT, String codigoAF, String codigoGER, String otroCodigo, String imagen, String observacion, float alto, float ancho, float profundidad, String nroMueble, String color) {
        super(id, tipo, modelo, marca, serie, estado, codigoTIC, codigoPAT, codigoAF, codigoGER, otroCodigo, imagen, observacion);
        this.alto = alto;
        this.ancho = ancho;
        this.profundidad = profundidad;
        this.nroMueble = nroMueble;
        this.color = color;
    }


    public float getAlto() {
        return alto;
    }

    public void setAlto(float alto) {
        this.alto = alto;
    }

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public float getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(float profundidad) {
        this.profundidad = profundidad;
    }

    public String getNroMueble() {
        return nroMueble;
    }

    public void setNroMueble(String nroMueble) {
        this.nroMueble = nroMueble;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
