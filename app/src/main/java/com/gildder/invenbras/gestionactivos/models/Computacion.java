package com.gildder.invenbras.gestionactivos.models;

/**
 * Created by gildder on 15/10/2015.
 */
public class Computacion extends Activo {
    private String nroCaja;
    private String nroEquipo;

    public Computacion( String nroCaja, String nroEquipo) {
        this.nroCaja = nroCaja;
        this.nroEquipo = nroEquipo;
    }

    public Computacion(int id, String tipo,  String modelo, String marca, String serie, String estado, String codigoTIC, String codigoPAT, String codigoAF, String codigoGER, String otroCodigo, String imagen, String observacion, String estado1, String nroCaja, String nroEquipo) {
        super(id, tipo,  modelo, marca, serie, estado, codigoTIC, codigoPAT, codigoAF, codigoGER, otroCodigo, imagen, observacion);
        estado = estado1;
        this.nroCaja = nroCaja;
        this.nroEquipo = nroEquipo;
    }



    public String getNroCaja() {
        return nroCaja;
    }

    public void setNroCaja(String nroCaja) {
        this.nroCaja = nroCaja;
    }

    public String getNroEquipo() {
        return nroEquipo;
    }

    public void setNroEquipo(String nroEquipo) {
        this.nroEquipo = nroEquipo;
    }
}
