package com.gildder.invenbras.gestionactivos.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gildder.invenbras.gestionactivos.clases.Inventario;
import com.gildder.invenbras.gestionactivos.clases.TipoActivo;

import java.util.ArrayList;

/**
 * Esta clase el el controlador de modelo inventario
 * utilizamos el patron singleton
 * Created by gildder on 29/10/2020.
 */
public class CTipoActivo {
    public static String _Tabla = "tipoactivo";
    public static String _id    = "id";
    public static String _tipo = "tipo";
    public static String _nombre = "nombre";
    public static String _descripcion = "descripcion";



    private static CTipoActivo instance;
    private DBHelper dbHelper;

    /**
     * Metodo singleton
     * @return
     */
    public static CTipoActivo getInstance() {
        if (instance == null){
            instance = new CTipoActivo();
        }

        return instance ;
    }

    /***
     * inicializacion de la clase DBHelper
     * @param helper
     */
    public void inicialize(DBHelper helper){
        this.dbHelper = helper;

        Log.i("INF:","Se inicializo el helper de tipo");
    }


    public Long Insertar(TipoActivo tipoActivo) {
        if (!Existe(String.valueOf(tipoActivo.getId()))){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(_id,tipoActivo.getId());
            values.put(_tipo, tipoActivo.getTipo());
            values.put(_nombre,tipoActivo.getNombre());
            values.put(_descripcion,tipoActivo.getDescripcion());

            Long resultID = db.insert(_Tabla, null, values);

            Log.i("INF:", "Se adiciono correctamente el tipo "+ tipoActivo.getId());
            db.close();
            return resultID;
        }else{
            Log.i("INF:", "NO Se adiciono correctamente el tipo "+ tipoActivo.getId());

            return (long) -1;
        }
    }



    public Long Actualizar(TipoActivo tipoActivo) {

        if (Existe((String.valueOf(tipoActivo.getId())))){
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(_id,tipoActivo.getId());
            values.put(_tipo, tipoActivo.getTipo());
            values.put(_nombre,tipoActivo.getNombre());
            values.put(_descripcion, tipoActivo.getDescripcion());

            Long resultID = (long) db.update(_Tabla, values, "id=?", new String[]{String.valueOf(tipoActivo.getId())});

            Log.i("INF:", "Se adiciono correctamente el tipo " + tipoActivo.getId());
            db.close();
            return resultID;
        }else{
            Log.e("ERROR:", "No se actualizo correctamente porque el tipo activo No existeo " + tipoActivo.getId());

            return (long) -1;
        }
    }

    public Long Eliminar(int id)
    {
        if (Existe((String.valueOf(id)))){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Long resultID = (long) db.delete(_Tabla, _id + "=?", new String[]{String.valueOf(id)});
            db.close();

            return resultID;
        }else{
            Log.e("ERROR:", "No se elimino correctamente porque el tipo activo No existe " + id);

            return (long) -1;
        }
    }



    public TipoActivo Get(String id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[]{_id, _tipo, _nombre, _descripcion},
                _id + "=?",
                new String[]{id}, null, null, null);

        TipoActivo tipoActivo = null;

        if (cursor != null && cursor.moveToNext()){
            tipoActivo = new TipoActivo();
            tipoActivo.setId(cursor.getInt(0));
            tipoActivo.setTipo(cursor.getString(1));
            tipoActivo.setNombre(cursor.getString(2));
            tipoActivo.setDescripcion(cursor.getString(3));
        }

        cursor.close();
        db.close();

        Log.i("INF:", "Se obtubo el tipo " + id);
        return  tipoActivo;
    }


   // db.query (tabla, campos array, condicion, argumrntos where array, group by, having, order by );
    public ArrayList<TipoActivo> GetAll(){
        ArrayList<TipoActivo> listaTipos = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[] {_id, _tipo, _nombre, _descripcion},
                null, null, null, null, null );

        if (cursor != null){
            while (cursor.moveToNext()) {
                TipoActivo tipoActivo = new TipoActivo();
                tipoActivo.setId(cursor.getInt(0));
                tipoActivo.setTipo(cursor.getString(1));
                tipoActivo.setNombre(cursor.getString(2));
                tipoActivo.setDescripcion(cursor.getString(3));

                listaTipos.add(tipoActivo);
            }
        }

        cursor.close();
        db.close();

        Log.i("INF:", "Se obtuvo todos los tipos ");

        return  listaTipos;
    }

    public ArrayList<TipoActivo> GetOneTipo(){
        ArrayList<TipoActivo> listaTipos = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[] {_id, _tipo, _nombre, _descripcion},
                null, null, null, null, null );

        if (cursor != null){
            while (cursor.moveToNext()) {
                TipoActivo tipoActivo = new TipoActivo();
                tipoActivo.setId(cursor.getInt(0));
                tipoActivo.setTipo(cursor.getString(1));
                tipoActivo.setNombre(cursor.getString(2));
                tipoActivo.setDescripcion(cursor.getString(3));

                if(cursor.isFirst()) {
                        listaTipos.add(tipoActivo);
                }else{
                    if (!tipoActivo.getTipo().toString().equals(listaTipos.get(listaTipos.size()-1).getTipo().toString()))
                        listaTipos.add(tipoActivo);
                }
            }
        }

        cursor.close();
        db.close();

        Log.i("INF:", "Se obtuvo todos los tipos ");

        return  listaTipos;
    }

    public ArrayList<TipoActivo> GetAllTipo(String tipo){
        ArrayList<TipoActivo> listaTipos = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[] {_id, _tipo, _nombre, _descripcion},
                _tipo + "=?",
                new String[]{tipo}, null, null, null);

        if (cursor != null){
            while (cursor.moveToNext()) {
                TipoActivo tipoActivo = new TipoActivo();
                tipoActivo.setId(cursor.getInt(0));
                tipoActivo.setTipo(cursor.getString(1));
                tipoActivo.setNombre(cursor.getString(2));
                tipoActivo.setDescripcion(cursor.getString(3));

                listaTipos.add(tipoActivo);
            }
        }

        cursor.close();
        db.close();

        Log.i("INF:", "Se obtuvo todos los tipos ");

        return  listaTipos;
    }

    public int Count(){
        int count = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if(db != null) {
            count = (int) DatabaseUtils.queryNumEntries(db, _Tabla);
        }
        db.close();

        return count;
    }


    private boolean Existe(String id) {
        boolean result = false;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if(db != null) {
            Cursor cursor = db.query(_Tabla, new String[]{_id, _tipo, _nombre, _descripcion},
                    _id + "=?",new String[]{id}, null, null, null);
            result = (cursor.getCount() > 0);
            cursor.close();
            dbHelper.close();

            Log.i("INF:", "El Tipo " + id + "Ya existe");
        }
        db.close();

        return result;


    }


}
