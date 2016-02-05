package com.gildder.invenbras.gestionactivos.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gildder.invenbras.gestionactivos.clases.Notificacion;
import com.gildder.invenbras.gestionactivos.clases.TipoActivo;

import java.util.ArrayList;

/**
 * Esta clase el el controlador de modelo inventario
 * utilizamos el patron singleton
 * Created by gildder on 29/10/2020.
 */
public class CNotificacion {
    public static String _Tabla = "notificacion";
    public static String _id    = "id";
    public static String _titulo = "titulo";
    public static String _descripcion = "descripcion";
    public static String _fecha = "fecha";
    public static String _tipo = "tipo";



    private static CNotificacion instance;
    private DBHelper dbHelper;

    /**
     * Metodo singleton
     * @return
     */
    public static CNotificacion getInstance() {
        if (instance == null){
            instance = new CNotificacion();
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


    public Long Insertar(Notificacion notificacion) {
        if (!Existe(String.valueOf(notificacion.getId()))){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(_id,notificacion.getId());
            values.put(_titulo, notificacion.getTitulo());
            values.put(_descripcion,notificacion.getDescripcion());
            values.put(_fecha,notificacion.getFecha());
            values.put(_tipo,notificacion.getTipo());

            Long resultID = db.insert(_Tabla, null, values);

            Log.i("INF:", "Se adiciono correctamente el tipo "+ notificacion.getId());
            db.close();
            return resultID;
        }else{
            Log.i("INF:", "NO Se adiciono correctamente el tipo " + notificacion.getId());

            return (long) -1;
        }
    }



    public Long Actualizar(Notificacion notificacion) {

        if (Existe((String.valueOf(notificacion.getId())))){
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(_id,notificacion.getId());
            values.put(_titulo, notificacion.getTitulo());
            values.put(_descripcion,notificacion.getDescripcion());
            values.put(_fecha,notificacion.getFecha());
            values.put(_tipo,notificacion.getTipo());

            Long resultID = (long) db.update(_Tabla, values, "id=?", new String[]{String.valueOf(notificacion.getId())});

            Log.i("INF:", "Se adiciono correctamente el tipo " + notificacion.getId());
            db.close();
            return resultID;
        }else{
            Log.e("ERROR:", "No se actualizo correctamente porque el tipo activo No existeo " + notificacion.getId());

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



    public Notificacion Get(String id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[]{_id, _titulo, _descripcion, _fecha, _tipo},
                _id + "=?",
                new String[]{id}, null, null, null);

        Notificacion notificacion = null;

        if (cursor != null && cursor.moveToNext()){
            notificacion = new Notificacion();
            notificacion.setId(cursor.getInt(0));
            notificacion.setTitulo(cursor.getString(1));
            notificacion.setDescripcion(cursor.getString(2));
            notificacion.setTipo(cursor.getString(4));
        }

        cursor.close();
        db.close();

        Log.i("INF:", "Se obtubo el tipo " + id);
        return  notificacion;
    }


   // db.query (tabla, campos array, condicion, argumrntos where array, group by, having, order by );
    public ArrayList<Notificacion> GetAll(){
        ArrayList<Notificacion> notificacions = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[] {_id,  _titulo, _descripcion, _fecha, _tipo},
                null, null, null, null, null );

        if (cursor != null){
            while (cursor.moveToNext()) {
                Notificacion notificacion = new Notificacion();
                notificacion.setId(cursor.getInt(0));
                notificacion.setTitulo(cursor.getString(1));
                notificacion.setDescripcion(cursor.getString(2));
                notificacion.setFecha(cursor.getString(3));
                notificacion.setTipo(cursor.getString(4));

                notificacions.add(notificacion);
            }
        }

        cursor.close();
        db.close();

        Log.i("INF:", "Se obtuvo todos los tipos ");

        return  notificacions;
    }

    public ArrayList<Notificacion> GetOneTipo(){
        ArrayList<Notificacion> notificacions = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[] {_id, _titulo, _descripcion, _fecha, _tipo},
                null, null, null, null, null );

        if (cursor != null){
            while (cursor.moveToNext()) {
                Notificacion notificacion = new Notificacion();
                notificacion.setId(cursor.getInt(0));
                notificacion.setTitulo(cursor.getString(1));
                notificacion.setDescripcion(cursor.getString(2));
                notificacion.setFecha(cursor.getString(3));
                notificacion.setTipo(cursor.getString(4));

                if(cursor.isFirst()) {
                        notificacions.add(notificacion);
                }else{
                    if (!notificacion.getTipo().toString().equals(notificacions.get(notificacions.size()-1).getTipo().toString()))
                        notificacions.add(notificacion);
                }
            }
        }

        cursor.close();
        db.close();

        Log.i("INF:", "Se obtuvo todos los tipos ");

        return  notificacions;
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
            Cursor cursor = db.query(_Tabla, new String[]{_id, _titulo, _descripcion, _fecha, _tipo},
                    _id + "=?",new String[]{id}, null, null, null);
            result = (cursor.getCount() > 0);
            cursor.close();
            dbHelper.close();

            Log.i("INF:", "El Notificacion " + id + "Ya existe");
        }
        db.close();

        return result;


    }



}
