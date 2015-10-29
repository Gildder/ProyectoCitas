package com.gildder.invenbras.gestionactivos.basedatos;

/**
 * Created by gildder on 15/10/2015.
 */
public class dComputacion {
    public static String _Tabla = "computacion";
    public static String _id  ="id";
    public static String _nroCaja  ="nroCaja";
    public static String _nroEquipo = "nroEquipo";

    private DBHelper dbHelper;
    private static dComputacion instance;

    public static dComputacion getInstance() {
        if (instance == null){
            instance = new dComputacion();
        }
        return instance;
    }

    public void inicialize(DBHelper helper){
        this.dbHelper = helper;
    }

    public void adicionar( String nroCaja, String nroEquipo) {

    }




}
