package com.gildder.invenbras.gestionactivos.basedatos;

/**
 * Created by gildder on 15/10/2015.
 */
public class dMaterial {
    public static String _Tabla = "material";
    private String _id = "id";
    private String _nombre = "nombre";
    private String _cantidad = "cantidad";
    private String _unidad = "unidad";


    private dMaterial   instance;
    private DBHelper dbHelper;

    public dMaterial getInstance() {
        if (instance == null){
            instance = new dMaterial();
        }

        return instance ;
    }

    public void inicialize(DBHelper helper){
        this.dbHelper = helper;
    }

    public void MaterialCantidad(String nombre, int cantidad, String unidad) {
    }
}