package com.gildder.invenbras.gestionactivos.clases;

/**
 * Created by gildder on 03/11/2015.
 */
public class TipoActivo
{
    private int id;
    private String tipo;
    private String nombre;
    private String descripcion;

    public TipoActivo() {
    }

    public TipoActivo(int id, String tipo, String nombre, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
