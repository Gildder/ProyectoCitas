package com.gildder.invenbras.gestionactivos.models;

/**
 * Created by gildder on 15/10/2015.
 */
public class Extintor extends Activo {
    private String codigo;
    private String numero;
    private String contenido;
    private String peso;
    private String fechaMantenimiento;

    public Extintor(String codigo, String numero, String contenido, String peso, String fechaMantenimiento) {
        this.codigo = codigo;
        this.numero = numero;
        this.contenido = contenido;
        this.peso = peso;
        this.fechaMantenimiento = fechaMantenimiento;
    }

    public Extintor(int id,  String caracteristicas,String tipo,  String modelo, String marca, String serie, String estado, String codigoTIC, String codigoPAT, String codigoAF, String codigoGER, String otroCodigo, String imagen, String observacion, String codigo, String numero, String contenido, String peso, String fechaMantenimiento) {
        super(id,  caracteristicas, tipo, modelo, marca, serie, estado, codigoTIC, codigoPAT, codigoAF, codigoGER, otroCodigo, imagen, observacion);
        this.codigo = codigo;
        this.numero = numero;
        this.contenido = contenido;
        this.peso = peso;
        this.fechaMantenimiento = fechaMantenimiento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getFechaMantenimiento() {
        return fechaMantenimiento;
    }

    public void setFechaMantenimiento(String fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }
}
