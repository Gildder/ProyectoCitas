package com.gildder.invenbras.gestionactivos.clases;

/**
 * Created by gildder on 03/10/2015.
 */
public class Inventario {
    private int id;
    private String nombre;
    private String descripcion;
    private String prioridad;
    private String total;

    public Inventario() {
    }

    public Inventario(int id, String nombre, String descripcion, String prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.total = "0";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        String priori="X";
        switch (prioridad){
            case "0":
                priori = "Alta";
                break;
            case "1":
                priori = "Media";
                break;
            case "2":
                priori = "Baja";
                break;
        }
        this.prioridad = priori;
    }


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
