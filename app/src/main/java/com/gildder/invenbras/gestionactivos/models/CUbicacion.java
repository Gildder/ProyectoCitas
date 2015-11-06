package com.gildder.invenbras.gestionactivos.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gildder.invenbras.gestionactivos.clases.Inventario;
import com.gildder.invenbras.gestionactivos.clases.Ubicacion;

import java.util.ArrayList;

/**
 * Esta clase el el controlador de modelo inventario
 * utilizamos el patron singleton
 * Created by gildder on 29/10/2020.
 */
public class CUbicacion {
    public static String _Tabla = "ubicacion";
    public static String _id    = "id";
    public static String _sector = "sector";
    public static String _area = "area";
    public static String _lugar = "lugar";



    private static CUbicacion instance;
    private DBHelper dbHelper;

    /**
     * Metodo singleton
     * @return
     */
    public static CUbicacion getInstance() {
        if (instance == null){
            instance = new CUbicacion();
        }

        return instance ;
    }

    /***
     * inicializacion de la clase DBHelper
     * @param helper
     */
    public void inicialize(DBHelper helper){
        this.dbHelper = helper;

        Log.i("INF:","Se inicializo el helper de ubicacion");
    }


    public Long Insertar(Ubicacion ubicacion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (!Existe(String.valueOf(ubicacion.getId()))){
            ContentValues values = new ContentValues();
            values.put(_id,ubicacion.getId());
            values.put(_sector,ubicacion.getSector());
            values.put(_area,ubicacion.getArea());
            values.put(_lugar, ubicacion.getLugar());

            Long resultID = db.insert(_Tabla, null, values);

            Log.i("INF:", "Se adiciono correctamente el ubicacion " + ubicacion.getId());

            return resultID;
        }else{
            Log.i("INF:", "NO Se adiciono correctamente el ubicacion "+ ubicacion.getId());

            return (long) -1;
        }
    }


    public Long Actualizar(Ubicacion ubicacion) {

        if (!Existe((String.valueOf(ubicacion.getId())))){
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(_sector,ubicacion.getSector());
            values.put(_area,ubicacion.getArea());
            values.put(_lugar, ubicacion.getLugar());

            Long resultID = (long) db.update(_Tabla, values, "id=?", new String[]{String.valueOf(ubicacion.getId())});

            Log.i("INF:", "Se adiciono correctamente el ubicacion " + ubicacion.getId());

            return resultID;
        }else{
            Log.i("INF:", "NO Se adiciono correctamente el ubicacion "+ ubicacion.getId());

            return (long) -1;
        }
    }

    public Long Eliminar(int id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Long resultID = (long) db.delete(_Tabla,  _id + "=?", new String[]{String.valueOf(id)});

        return resultID;
    }



    public Ubicacion Get(String id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[]{_id, _sector, _area, _lugar},
                _id + "=?",
                new String[]{id}, null, null, null);

        Ubicacion ubicacion = null;

        if (cursor != null && cursor.moveToNext()){
            ubicacion = new Ubicacion();
            ubicacion.setId(cursor.getInt(0));
            ubicacion.setSector(cursor.getString(1));
            ubicacion.setArea(cursor.getString(2));
            ubicacion.setLugar(cursor.getString(3));
        }

        cursor.close();

        Log.e("INF:", "Se obtubo el inventario " + id);
        return  ubicacion;
    }


   // db.query (tabla, campos array, condicion, argumrntos where array, group by, having, order by );
    public ArrayList<Ubicacion> GetAll(){
        ArrayList<Ubicacion> listaUbicacions = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[] {_id,  _sector, _area, _lugar},
                null, null, null, null, null );

        if (cursor != null){
            while (cursor.moveToNext()) {
                Ubicacion ubicacion = new Ubicacion();
                ubicacion.setId(cursor.getInt(0));
                ubicacion.setSector(cursor.getString(1));
                ubicacion.setArea(cursor.getString(2));
                ubicacion.setLugar(cursor.getString(3));

                listaUbicacions.add(ubicacion);
            }
        }

        cursor.close();

        Log.i("INF:", "Se obtuvo todos los ubicacion ");

        return  listaUbicacions;
    }

    public int Count(){
        int count = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if(db != null) {
            count = (int) DatabaseUtils.queryNumEntries(db, _Tabla);
        }
        return count;
    }


    private boolean Existe(String id) {
        boolean result = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if(db != null && Count()>0) {
            Cursor cursor = db.query(_Tabla, new String[]{_id,  _sector, _area, _lugar},
                    _id + "=?",new String[]{id}, null, null, null);
            result = (cursor.getCount() > 0);
            cursor.close();

            Log.i("INF:", "El ubicacion " + id + "Ya existe");
        }
        return result;
    }


}
