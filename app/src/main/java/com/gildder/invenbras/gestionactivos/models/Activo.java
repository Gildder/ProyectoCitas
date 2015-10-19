package com.gildder.invenbras.gestionactivos.models;

/**
 * Created by gildder on 15/10/2015.
 */
public class Activo {
    private int id;
    private String caracteristicas;
    private String tipo;
    private String modelo;
    private String marca;
    private String serie;
    private String estado;
    private String codigoTIC;   //codigo TIC
    private String codigoPAT;   //codigo patrimnio
    private String codigoAF;    //codigo Activo Fijo
    private String codigoGER;    //codigo Grencia
    private String otroCodigo;
    private String imagen;
    private String Observacion;

    /* Contructores */
    public Activo() {
    }

    public Activo(int id, String caracteristicas, String tipo, String modelo, String marca, String serie, String estado, String codigoTIC, String codigoPAT, String codigoAF, String codigoGER, String otroCodigo, String imagen, String observacion) {
        this.id = id;
        this.caracteristicas = caracteristicas;
        this.tipo = tipo;
        this.modelo = modelo;
        this.marca = marca;
        this.serie = serie;
        this.estado = estado;
        this.codigoTIC = codigoTIC;
        this.codigoPAT = codigoPAT;
        this.codigoAF = codigoAF;
        this.codigoGER = codigoGER;
        this.otroCodigo = otroCodigo;
        this.imagen = imagen;
        Observacion = observacion;
    }

   /*  Getter y Setter */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return caracteristicas;
    }

    public void setNombre(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoTIC() {
        return codigoTIC;
    }

    public void setCodigoTIC(String codigoTIC) {
        this.codigoTIC = codigoTIC;
    }

    public String getCodigoPAT() {
        return codigoPAT;
    }

    public void setCodigoPAT(String codigoPAT) {
        this.codigoPAT = codigoPAT;
    }

    public String getCodigoAF() {
        return codigoAF;
    }

    public void setCodigoAF(String codigoAF) {
        this.codigoAF = codigoAF;
    }

    public String getCodigoGER() {
        return codigoGER;
    }

    public void setCodigoGER(String codigoGER) {
        this.codigoGER = codigoGER;
    }

    public String getOtroCodigo() {
        return otroCodigo;
    }

    public void setOtroCodigo(String otroCodigo) {
        this.otroCodigo = otroCodigo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }
}
