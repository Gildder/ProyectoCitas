package com.gildder.invenbras.gestionactivos.models;

/**
 * Created by gildder on 15/10/2015.
 */
public class CajaFuerte extends Activo {
    private float alto;
    private float ancho;
    private float profundidad;

    /*   Contructor */

    public CajaFuerte(float alto, float ancho, float profundidad) {
        this.alto = alto;
        this.ancho = ancho;
        this.profundidad = profundidad;
    }

    public CajaFuerte(int id, String caracteristicas, String tipo, String modelo, String marca, String serie, String estado, String codigoTIC, String codigoPAT, String codigoAF, String codigoGER, String otroCodigo, String imagen, String observacion, float alto, float ancho, float profundidad) {
        super(id,   caracteristicas,tipo, modelo, marca, serie, estado, codigoTIC, codigoPAT, codigoAF, codigoGER, otroCodigo, imagen, observacion);
        this.alto = alto;
        this.ancho = ancho;
        this.profundidad = profundidad;
    }

    /*  Getter y Setter */

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
}
