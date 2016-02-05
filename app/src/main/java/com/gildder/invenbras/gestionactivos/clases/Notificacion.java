package com.gildder.invenbras.gestionactivos.clases;

import java.io.Serializable;

/**
 * Created by gildder on 15/10/2015.
 */
public class Notificacion implements Serializable{

    private int id;
    private String titulo;
    private String descripcion;
    private String fecha;
    private String tipo;



    /* Contructores */

    public Notificacion() {
    }

    public Notificacion(int id, String titulo, String descripcion, String fecha, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public Notificacion(Notificacion notificacion) {
        this.id = notificacion.getId();
        this.titulo = notificacion.getTitulo();
        this.descripcion = notificacion.getDescripcion();
        this.fecha = notificacion.getFecha();
        this.tipo = notificacion.getTipo();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
