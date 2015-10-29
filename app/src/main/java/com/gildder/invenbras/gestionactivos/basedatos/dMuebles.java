package com.gildder.invenbras.gestionactivos.basedatos;

/**
 * Created by gildder on 15/10/2015.
 */
public class dMuebles {
    public static String _Tabla = "mueble";
    public static String _id = "id";
    public static String _alto = "alto";
    public static String _ancho = "ancho";
    public static String _profundidad = "profundidad";
    public static String _nroMueble = "nroMueble";
    public static String _color = "color";


    private dMuebles   instance;
    private DBHelper dbHelper;

    public dMuebles getInstance() {
        if (instance == null){
            instance = new dMuebles();
        }

        return instance ;
    }

    public void inicialize(DBHelper helper){
        this.dbHelper = helper;
    }


    public void Muebles(String alto, String ancho, String profundidad, String nroMueble, String color) {

    }


}