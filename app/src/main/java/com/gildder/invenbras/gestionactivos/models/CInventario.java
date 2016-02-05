package com.gildder.invenbras.gestionactivos.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gildder.invenbras.gestionactivos.clases.Inventario;

import java.util.ArrayList;

/**
 * Esta clase el el controlador de modelo inventario
 * utilizamos el patron singleton
 * Created by gildder on 29/10/2020.
 */
public class CInventario {
    public static String _Tabla = "inventario";
    public static String _id    = "id";
    public static String _nombre = "nombre";
    public static String _descripcion = "descripcion";
    public static String _prioridad = "prioridad";



    private static CInventario   instance;
    private DBHelper dbHelper;

    /**
     * Metodo singleton
     * @return
     */
    public static CInventario getInstance() {
        if (instance == null){
            instance = new CInventario();
        }

        return instance ;
    }

    /***
     * inicializacion de la clase DBHelper
     * @param helper
     */
    public void inicialize(DBHelper helper){
        this.dbHelper = helper;

        Log.i("INF:","Se inicializo el helper de invnetrio");
    }


    public Long Insertar(String id, String nombre, String descripcion, String prioridad) {
        if (!Existe(id)){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(_id,id);
            values.put(_nombre,nombre);
            values.put(_descripcion,descripcion);
            values.put(_prioridad, prioridad);

            Long resultID = db.insert(_Tabla, null, values);

            Log.i("INF:", "Se adiciono correctamente el inventario "+ id);
            db.close();

            return resultID;
        }else{
            Log.i("INF:", "NO Se adiciono correctamente el inventario "+ id);

            return (long) -1;
        }
    }

    public Long Insertar(Inventario inventario) {

        if (!Existe((String.valueOf(inventario.getId())))){
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(_id,String.valueOf(inventario.getId()));
            values.put(_nombre,inventario.getNombre());
            values.put(_descripcion,inventario.getDescripcion());
            values.put(_prioridad, inventario.getPrioridad());

            Long resultID = db.insert(_Tabla, null, values);

            Log.i("INF:", "Se inserto correctamente el inventario " + inventario.getId());
            db.close();
            return resultID;
        }else{
            Log.e("ERROR:", "No se inserto correctamente porque el inventario ya existe" + inventario.getId());

            return (long) -1;
        }
    }

    public Long Actualizar(Inventario inventario) {
        if (Existe((String.valueOf(inventario.getId())))){
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(_nombre,inventario.getNombre());
            values.put(_descripcion,inventario.getDescripcion());
            values.put(_prioridad, inventario.getPrioridad());

            Long resultID = (long) db.update(_Tabla, values, "id=?", new String[]{String.valueOf(inventario.getId())});

            Log.i("INF:", "Se Actualizo correctamente el inventario " + inventario.getId());
            db.close();

            return resultID;
        }else{
            Log.e("ERROR:", "No se actualizo correctamente porque el inventario No existeo " + inventario.getId());

            return (long) -1;
        }
    }

    public Long Eliminar(int id)
    {
        if (Existe((String.valueOf(id)))){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Long resultID = (long) db.delete(_Tabla,  _id + "=?", new String[]{String.valueOf(id)});
            db.close();

            return resultID;
        }else{
            Log.e("ERROR:", "No se elimino correctamente porque el inventario No existe " + id);

            return (long) -1;
        }
    }



    public Inventario Get(String id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[]{_id, _nombre, _descripcion, _prioridad},
                _id + "=?",
                new String[]{id}, null, null, null);

        Inventario inventario = null;

        if (cursor != null && cursor.moveToNext()){
            inventario = new Inventario();
            inventario.setId(cursor.getInt(0));
            inventario.setNombre(cursor.getString(1));
            inventario.setDescripcion(cursor.getString(2));
            inventario.setPrioridad(cursor.getString(3));
        }

        cursor.close();
        db.close();

        Log.i("INF:", "Se obtubo el inventario " + id);
        return  inventario;
    }


   // db.query (tabla, campos array, condicion, argumrntos where array, group by, having, order by );
    public ArrayList<Inventario> GetAll(){
        ArrayList<Inventario> listaInventarios = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[] {_id, _nombre, _descripcion, _prioridad},
                null, null, null, null, null );

        if (cursor != null){
            while (cursor.moveToNext()) {
                Inventario inventario = new Inventario();
                inventario.setId(cursor.getInt(0));
                inventario.setNombre(cursor.getString(1));
                inventario.setDescripcion(cursor.getString(2));
                inventario.setPrioridad(cursor.getString(3));

                listaInventarios.add(inventario);
            }
        }

        cursor.close();
        db.close();

        Log.i("INF:", "Se obtuvo todos los inventario ");
        db.close();

        return  listaInventarios;
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
            Cursor cursor = db.query(_Tabla, null,
                    _id + "=?",new String[]{id}, null, null, null);
            result = (cursor.getCount() > 0);
            cursor.close();

            Log.i("INF:", "El inventario " + id + "Ya existe");
        }
        db.close();

        return result;
    }


}
