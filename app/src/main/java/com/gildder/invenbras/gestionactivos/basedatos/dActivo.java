package com.gildder.invenbras.gestionactivos.basedatos;

import android.content.ContentValues;
import android.database.Cursor;

import com.gildder.invenbras.gestionactivos.models.Activo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gildder on 15/10/2015.
 */
public class dActivo{
    public static String _Tabla = "activo";
    public static String _id    = "id";
    public static String _caracteristicas = "caracteristica";
    public static String _tipo = "tipo";
    public static String _modelo = "modelo";
    public static String _marca = "marca";
    public static String _serie = "serie";
    public static String _estado = "estado";
    public static String _codigoTIC = "codigoTic";   //codigo TIC
    public static String _codigoPAT = "codigoPat";   //codigo patrimnio
    public static String _codigoAF = "codigoAf";    //codigo Activo Fijo
    public static String _codigoGER = "codigoGer";    //codigo Grencia
    public static String _otroCodigo = "otroCod";
    public static String _imagen = "imagen";
    public static String _Observacion = "observacion";


    private dActivo   instance;
    private DBHelper dbHelper;

    public dActivo getInstance() {
        if (instance == null){
            instance = new dActivo();
        }

        return instance ;
    }

    public void inicialize(DBHelper helper){
        this.dbHelper = helper;
    }


    public Long adicionar(String caracteristicas, String tipo, String modelo, String marca, String serie,
                  String estado, String codigoTIC, String codigoPAT, String codigoAF, String codigoGER,
                  String otroCodigo, String imagen, String observacion) {
        if (!Existe(serie)){
            ContentValues values = new ContentValues();
            values.put(_caracteristicas,caracteristicas);
            values.put(_tipo,tipo);
            values.put(_modelo,modelo);
            values.put(_marca,marca);
            values.put(_serie,serie);
            values.put(_estado,estado);
            values.put(_codigoTIC,codigoTIC);
            values.put(_codigoPAT,codigoPAT);
            values.put(_codigoAF,codigoAF);
            values.put(_codigoGER,codigoGER);
            values.put(_otroCodigo,otroCodigo);
            values.put(_Observacion,observacion);
            values.put(_imagen,imagen);

            Long resultID = dbHelper.getWritableDatabase().insert(_Tabla, null, values);
            dbHelper.close();

            return resultID;
        }else{
            return (long) -1;
        }
    }

    public Long actualizar(int id, byte[] imagen){
        ContentValues values = new ContentValues();
        values.put(_imagen, imagen);

        Long resultID = (long)dbHelper.getWritableDatabase().update(_Tabla, values, _id + "=?", new String[]{String.valueOf(id)});
        dbHelper.close();

        return resultID;
    }

    public Long actualizar(int id, String caracteristicas, String tipo, String modelo, String marca, String serie,
                  String estado, String codigoTIC, String codigoPAT, String codigoAF, String codigoGER,
                  String otroCodigo, String imagen, String observacion) {

            ContentValues values = new ContentValues();
            values.put(_caracteristicas,caracteristicas);
            values.put(_tipo,tipo);
            values.put(_modelo,modelo);
            values.put(_marca,marca);
            values.put(_serie,serie);
            values.put(_estado,estado);
            values.put(_codigoTIC,codigoTIC);
            values.put(_codigoPAT,codigoPAT);
            values.put(_codigoAF,codigoAF);
            values.put(_codigoGER,codigoGER);
            values.put(_otroCodigo,otroCodigo);
            values.put(_Observacion,observacion);
            values.put(_imagen, imagen);

            Long resultID = (long)dbHelper.getWritableDatabase().update(_Tabla, values, _id + "=?", new String[]{String.valueOf(id)});
            dbHelper.close();

            return resultID;

    }


    public Long eliminar(int id) {
        Long resultID = (long) dbHelper.getWritableDatabase().delete(_Tabla,  _id + "=?", new String[]{String.valueOf(id)});
        dbHelper.close();

        return resultID;
    }



    public Activo get(String serie){
        Cursor cursor = dbHelper.getWritableDatabase().query(_Tabla, new String[] {_id, _caracteristicas, _tipo, _modelo, _marca, _serie,
                _estado, _codigoTIC, _codigoPAT, _codigoAF, _codigoGER, _otroCodigo, _Observacion, _imagen},
                _serie + "=?",
                new String[]{serie}, null, null, null, null );

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

    public Activo get(int id){
        Cursor cursor = dbHelper.getWritableDatabase().query(_Tabla, new String[] {_id, _caracteristicas, _tipo, _modelo, _marca, _serie,
                _estado, _codigoTIC, _codigoPAT, _codigoAF, _codigoGER, _otroCodigo, _Observacion, _imagen},
                _serie + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null );

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
    public ArrayList<Activo> getAll(){
        ArrayList<Activo> listaActivos = new ArrayList<>();

        Cursor cursor = dbHelper.getWritableDatabase().query(_Tabla, new String[] {_id, _caracteristicas, _tipo, _modelo, _marca, _serie,
                _estado, _codigoTIC, _codigoPAT, _codigoAF, _codigoGER, _otroCodigo, _Observacion, _imagen},
                _serie + "=" + 0,
                 null, null, null, null, null );

        if (cursor != null){
            while (cursor.moveToNext()) {
                Activo activo = new Activo();
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

                listaActivos.add(activo);
            }
        }

        cursor.close();
        dbHelper.close();
        return  listaActivos;
    }



    private boolean Existe(String serie) {
        Cursor cursor = dbHelper.getWritableDatabase().query(_Tabla, new String[] {_serie}, _serie + "=?", new String[]{serie}, null, null, null, null );
        boolean result = (cursor.getCount()>0);
        cursor.close();
        dbHelper.close();
        return result;
    }

}
