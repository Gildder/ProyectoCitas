package com.gildder.invenbras.gestionactivos.models;

/**
 * Created by gildder on 15/10/2015.
 */
public class Ubicacion {
    private int id;
    private String sector;
    private String area;
    private String lugar;
    private int idAlmacen;

    public Ubicacion() {
    }

    public Ubicacion(int id, String sector, String area, String lugar, int idAlmacen) {
        this.id = id;
        this.sector = sector;
        this.area = area;
        this.lugar = lugar;
        this.idAlmacen = idAlmacen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }
}
