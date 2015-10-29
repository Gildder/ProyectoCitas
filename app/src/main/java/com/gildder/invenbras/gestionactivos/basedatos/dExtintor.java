package com.gildder.invenbras.gestionactivos.basedatos;

/**
 * Created by gildder on 15/10/2015.
 */
public class dExtintor {
    public static String _Tabla = "extintor";
    public static String _id = "id";
    public static String _codigo  = "codigo";
    public static String _numero = "numero";
    public static String _contenido = "contenido";
    public static String _peso = "peso";
    public static String _fechaMantenimiento = "fechaMantenimiento";

    private dExtintor   instance;
    private DBHelper dbHelper;

    public dExtintor getInstance() {
        if (instance == null){
            instance = new dExtintor();
        }

        return instance ;
    }

    public void inicialize(DBHelper helper){
        this.dbHelper = helper;
    }


    public void Extintor(String codigo, String numero, String contenido, String peso, String fechaMantenimiento) {

    }

}