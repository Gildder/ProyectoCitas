package com.gildder.invenbras.gestionactivos.basedatos;

import android.content.ContentValues;
import android.database.Cursor;

import com.gildder.invenbras.gestionactivos.models.Activo;

/**
 * Created by gildder on 15/10/2015.
 */
public class dCajaFuerte {
    public static String _Tabla = "caja_fuerte";
    public static String _id  = "id";
    public static String _alto = "alto";
    public static String _ancho = "ancho";
    public static String _profundidad = "profundidad";

    private dCajaFuerte   instance;
    private DBHelper dbHelper;

    public dCajaFuerte getInstance() {
        if (instance == null){
            instance = new dCajaFuerte();
        }

        return instance ;
    }

    public void inicialize(DBHelper helper){
        this.dbHelper = helper;
    }

    public Long adicionar(String id, String alto, String ancho, String profundidad) {
        if (!Existe(id)){
            ContentValues values = new ContentValues();
            values.put(_id,id);
            values.put(_alto,alto);
            values.put(_ancho,ancho);
            values.put(_profundidad,profundidad);

            Long resultID = dbHelper.getWritableDatabase().insert(_Tabla, null, values);
            dbHelper.close();

            return resultID;
        }else{
            return (long) -1;
        }
    }

    public Long actualizar(int id, String alto, String ancho, String profundidad) {

        ContentValues values = new ContentValues();
        values.put(_alto,alto);
        values.put(_ancho,ancho);
        values.put(_profundidad,profundidad);

        Long resultID = (long)dbHelper.getWritableDatabase().update(_Tabla, values, _id + "=?", new String[]{String.valueOf(id)});
        dbHelper.close();

        return resultID;
    }

    public Long eliminar(int id) {
        Long resultID = (long) dbHelper.getWritableDatabase().delete(_Tabla, _id + "=?", new String[]{String.valueOf(id)});
        dbHelper.close();

        return resultID;
    }


    public Activo get(String id){
        Cursor cursor = dbHelper.getWritableDatabase().query(_Tabla, new String[] {_id},
                _id + "=?",
                new String[]{id}, null, null, null, null );

        Activo activo = null;

        if (cursor != null && cursor.moveToNext()){
            activo = new Activo();
            activo.setId(cursor.getInt(0));
            activo.setCaracteristicas(cursor.getString(1));
            activo.setTipo(cursor.getString(2));
            activo.setModelo(cursor.getString(3));
            activo.setMarca(cursor.getString(4));
            activo.setSerie(cursor.getString(5));
            activo.setEstado(cursor.getString(6));
            activo.setCodigoTIC(cursor.getString(7));
            activo.setCodigoPAT(cursor.getString(8));
            activo.setCodigoAF(cursor.getString(9));
            activo.setCodigoGER(cursor.getString(10));
            activo.setOtroCodigo(cursor.getString(11));
            activo.setObservacion(cursor.getString(12));
            activo.setImagen(cursor.getString(13));
        }

        cursor.close();
        dbHelper.close();
        return  activo;
    }

    private boolean Existe(String id) {
        return false;
    }

}
