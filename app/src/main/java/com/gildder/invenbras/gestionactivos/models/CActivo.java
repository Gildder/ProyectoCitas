package com.gildder.invenbras.gestionactivos.models;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gildder.invenbras.gestionactivos.clases.Activo;

import java.util.ArrayList;

/**
 * Created by gildder on 15/10/2015.
 */
public class CActivo{
    public static String _Tabla = "activo";
    public static String _id    = "id";
    public static String _descripcion = "descripcion";
    public static String _marca = "marca";
    public static String _modelo = "modelo";
    public static String _serie = "serie";
    public static String _estado = "estado";
    public static String _color = "color";
    public static String _alto = "alto";
    public static String _ancho = "ancho";
    public static String _profundidad = "profundidad";
    public static String _contenido = "contenido";
    public static String _peso = "peso";
    public static String _nro = "nro";
    public static String _fechaMantenimiento = "fechaMantenimiento";
    public static String _unidad = "unidad";
    public static String _cantidad = "cantidad";
    public static String _material = "material";
    public static String _codigoTIC = "codigoTIC";
    public static String _codigoPAT = "codigoPatrimonio";
    public static String _codigoAF = "codigoActivoFijo";
    public static String _codigoGER = "codigoGerencia";
    public static String _otroCodigo = "otroCodigo";
    public static String _imagen = "imagen";
    public static String _Observacion = "observacion";
    public static String _TipoActivo_id = "tipoactivo_id";
    public static String _Empleado_id = "empleado_id";
    public static String _Ubicacion_id = "ubicacion_id";
    public static String _Inventario_id = "inventario_id";


    private static CActivo   instance;
    private DBHelper dbHelper;

    public static CActivo getInstance() {
        if (instance == null){
            instance = new CActivo();
        }

        return instance ;
    }

    public void inicialize(DBHelper helper){
        this.dbHelper = helper;
    }


    public Long Insertar(Activo activo) {
        if (!ExisteSerie(activo.getSerie())){

            ContentValues values = new ContentValues();
            values.put(_descripcion,activo.getDescripcion());
            values.put(_marca,activo.getMarca());
            values.put(_modelo,activo.getModelo());
            values.put(_serie,activo.getSerie());
            values.put(_estado,activo.getEstado());
            values.put(_color,activo.getColor());
            values.put(_alto,activo.getAlto());
            values.put(_ancho,activo.getAncho());
            values.put(_profundidad,activo.getProfundidad());
            values.put(_contenido,activo.getContenido());
            values.put(_peso,activo.getPeso());
            values.put(_nro,activo.getNro());
            values.put(_fechaMantenimiento,activo.getFechaMantenimiento());
            values.put(_unidad,activo.getUnidad());
            values.put(_cantidad,activo.getCantidad());
            values.put(_material,activo.getMaterial());
            values.put(_codigoTIC,activo.getCodigoTIC());
            values.put(_codigoPAT,activo.getCodigoPAT());
            values.put(_codigoAF,activo.getCodigoAF());
            values.put(_codigoGER,activo.getCodigoGER());
            values.put(_otroCodigo,activo.getOtroCodigo());
            values.put(_imagen,activo.getImagen());
            values.put(_Observacion,activo.getObservacion());
            values.put(_TipoActivo_id,activo.getIdTipo());
            values.put(_Empleado_id,activo.getIdEmpleado());
            values.put(_Ubicacion_id,activo.getIdUbicacion());
            values.put(_Inventario_id, activo.getIdInventario());

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Long resultID = db.insert(_Tabla, null, values);
            db.close();

            Log.i("INF:", "Se inserto correctamente el ubicacion " + activo.getId());
            return resultID;
        }else{
            Log.e("ERROR:", "No se inserto correctamente el activo porque ya existe "+ activo.getId());

            return (long) -1;
        }
    }

    public Long Actualizar(int id, byte[] imagen){
        if (Existe((String.valueOf(id)))){

            ContentValues values = new ContentValues();
            values.put(_imagen, imagen);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Long resultID = (long)db.update(_Tabla, values, _id + "=?", new String[]{String.valueOf(id)});
            db.close();

            Log.i("INF:", "Se actualizo correctamente el activo " + id);

            return resultID;
        }else{
            Log.e("ERROR:", "No se actualizo correctamente la ubicacion por No Existe. " + id);

            return (long) -1;
        }
    }

    public Long Actualizar(Activo activo) {

        if (Existe((String.valueOf(activo.getId())))){
            ContentValues values = new ContentValues();
            values.put(_descripcion,activo.getDescripcion());
            values.put(_marca,activo.getMarca());
            values.put(_modelo,activo.getModelo());
            values.put(_serie,activo.getSerie());
            values.put(_estado,activo.getEstado());
            values.put(_color,activo.getColor());
            values.put(_alto,activo.getAlto());
            values.put(_ancho,activo.getAncho());
            values.put(_profundidad,activo.getProfundidad());
            values.put(_contenido,activo.getContenido());
            values.put(_peso,activo.getPeso());
            values.put(_nro,activo.getNro());
            values.put(_fechaMantenimiento,activo.getFechaMantenimiento());
            values.put(_unidad,activo.getUnidad());
            values.put(_cantidad,activo.getCantidad());
            values.put(_material,activo.getMaterial());
            values.put(_codigoTIC,activo.getCodigoTIC());
            values.put(_codigoPAT,activo.getCodigoPAT());
            values.put(_codigoAF,activo.getCodigoAF());
            values.put(_codigoGER,activo.getCodigoGER());
            values.put(_otroCodigo,activo.getOtroCodigo());
            values.put(_imagen, activo.getImagen());
            values.put(_Observacion,activo.getObservacion());
            values.put(_TipoActivo_id,activo.getIdTipo());
            values.put(_Empleado_id,activo.getIdEmpleado());
            values.put(_Ubicacion_id,activo.getIdUbicacion());
            values.put(_Inventario_id,activo.getIdUbicacion());

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Long resultID = (long)db.update(_Tabla, values, _id + "=?", new String[]{String.valueOf(activo.getId())});
            db.close();

            return resultID;
        }else{
            Log.e("ERROR:", "No se actualizo correctamente la ubicacion por No Existe. " + activo.getId());

            return (long) -1;
        }
    }


    public Long Eliminar(int id) {
        if (Existe((String.valueOf(id)))){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Long resultID = (long) db.delete(_Tabla, _id + "=?", new String[]{String.valueOf(id)});
            db.close();

            Log.e("INF:", "Se elimino correctamente el activo. " + id);

            return resultID;
        }else{
            Log.e("ERROR:", "No se elimino correctamente el activo por No Existe. " + id);

            return (long) -1;
        }
    }



    public Activo Get(String serie){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[] {
                        _id, _descripcion, _marca,  _modelo,_serie, _estado,
                        _color, _alto, _ancho, _profundidad, _contenido, _peso, _nro, _fechaMantenimiento, _unidad, _cantidad, _material,
                        _codigoTIC, _codigoPAT, _codigoAF, _codigoGER, _otroCodigo, _Observacion, _imagen,
                        _TipoActivo_id, _Empleado_id,_Ubicacion_id, _Inventario_id
                },
                _serie + "=?",
                new String[]{serie}, null, null, null, null );

        Activo activo = null;

        if (cursor != null && cursor.moveToNext()){
            activo = new Activo();
            activo.setId(cursor.getInt(0));
            activo.setDescripcion(cursor.getString(1));
            activo.setMarca(cursor.getString(2));
            activo.setModelo(cursor.getString(3));
            activo.setSerie(cursor.getString(4));
            activo.setEstado(cursor.getString(5));
            activo.setColor(cursor.getString(6));
            activo.setAlto(Float.valueOf(cursor.getString(7)));
            activo.setAncho(Float.valueOf(cursor.getString(8)));
            activo.setProfundidad(Float.valueOf(cursor.getString(9)));
            activo.setContenido(cursor.getString(10));
            activo.setPeso(Float.valueOf(cursor.getString(11)));
            activo.setNro(cursor.getString(12));
            activo.setFechaMantenimiento(cursor.getString(13));
            activo.setUnidad(cursor.getString(14));
            activo.setPeso(Integer.valueOf(cursor.getString(15)));
            activo.setMaterial(cursor.getString(16));
            activo.setCodigoTIC(cursor.getString(17));
            activo.setCodigoPAT(cursor.getString(18));
            activo.setCodigoAF(cursor.getString(19));
            activo.setCodigoGER(cursor.getString(20));
            activo.setOtroCodigo(cursor.getString(21));
            activo.setImagen(cursor.getString(22));
            activo.setObservacion(cursor.getString(23));
            activo.setIdTipo(Integer.valueOf(cursor.getString(24)));
            activo.setIdEmpleado(Integer.valueOf(cursor.getString(25)));
            activo.setIdUbicacion(Integer.valueOf(cursor.getString(26)));
            activo.setIdInventario(Integer.valueOf(cursor.getString(27)));
        }

        cursor.close();
        db.close();

        return  activo;
    }

    public Activo Get(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[] {
                        _id, _descripcion, _marca,  _modelo,_serie, _estado,
                        _color, _alto, _ancho, _profundidad, _contenido, _peso, _nro, _fechaMantenimiento, _unidad, _cantidad, _material,
                        _codigoTIC, _codigoPAT, _codigoAF, _codigoGER, _otroCodigo, _Observacion, _imagen,
                        _TipoActivo_id, _Empleado_id,_Ubicacion_id, _Inventario_id
                },
                _id + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null );

        Activo activo = null;

        if (cursor != null && cursor.moveToNext()){
            activo = new Activo();
            activo.setId(cursor.getInt(0));
            activo.setDescripcion(cursor.getString(1));
            activo.setMarca(cursor.getString(2));
            activo.setModelo(cursor.getString(3));
            activo.setSerie(cursor.getString(4));
            activo.setEstado(cursor.getString(5));
            activo.setColor(cursor.getString(6));
            activo.setAlto(Float.valueOf(cursor.getString(7)));
            activo.setAncho(Float.valueOf(cursor.getString(8)));
            activo.setProfundidad(Float.valueOf(cursor.getString(9)));
            activo.setContenido(cursor.getString(10));
            activo.setPeso(Float.valueOf(cursor.getString(11)));
            activo.setNro(cursor.getString(12));
            activo.setFechaMantenimiento(cursor.getString(13));
            activo.setUnidad(cursor.getString(14));
            activo.setPeso(Integer.valueOf(cursor.getString(15)));
            activo.setMaterial(cursor.getString(16));
            activo.setCodigoTIC(cursor.getString(17));
            activo.setCodigoPAT(cursor.getString(18));
            activo.setCodigoAF(cursor.getString(19));
            activo.setCodigoGER(cursor.getString(20));
            activo.setOtroCodigo(cursor.getString(21));
            activo.setImagen(cursor.getString(22));
            activo.setObservacion(cursor.getString(23));
            activo.setIdTipo(Integer.valueOf(cursor.getString(24)));
            activo.setIdEmpleado(Integer.valueOf(cursor.getString(25)));
            activo.setIdUbicacion(Integer.valueOf(cursor.getString(26)));
            activo.setIdInventario(Integer.valueOf(cursor.getString(27)));
        }

        cursor.close();
        db.close();

        return  activo;
    }


    public ArrayList<Activo> GetAll(){
        ArrayList<Activo> listaActivos = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getReadableDatabase().query(_Tabla, new String[] {
                        _id, _descripcion, _marca,  _modelo,_serie, _estado,
                        _color, _alto, _ancho, _profundidad, _contenido, _peso, _nro, _fechaMantenimiento, _unidad, _cantidad, _material,
                        _codigoTIC, _codigoPAT, _codigoAF, _codigoGER, _otroCodigo, _Observacion, _imagen,
                        _TipoActivo_id, _Empleado_id,_Ubicacion_id, _Inventario_id
                },
                null,null, null, null, null );

        if (cursor != null){
            while (cursor.moveToNext()) {
                Activo activo = new Activo();
                activo.setId(cursor.getInt(0));
                activo.setDescripcion(cursor.getString(1));
                activo.setMarca(cursor.getString(2));
                activo.setModelo(cursor.getString(3));
                activo.setSerie(cursor.getString(4));
                activo.setEstado(cursor.getString(5));
                activo.setColor(cursor.getString(6));
                activo.setAlto(Float.valueOf(cursor.getString(7)));
                activo.setAncho(Float.valueOf(cursor.getString(8)));
                activo.setProfundidad(Float.valueOf(cursor.getString(9)));
                activo.setContenido(cursor.getString(10));
                activo.setPeso(Float.valueOf(cursor.getString(11)));
                activo.setNro(cursor.getString(12));
                activo.setFechaMantenimiento(cursor.getString(13));
                activo.setUnidad(cursor.getString(14));
                activo.setPeso(Integer.valueOf(cursor.getString(15)));
                activo.setMaterial(cursor.getString(16));
                activo.setCodigoTIC(cursor.getString(17));
                activo.setCodigoPAT(cursor.getString(18));
                activo.setCodigoAF(cursor.getString(19));
                activo.setCodigoGER(cursor.getString(20));
                activo.setOtroCodigo(cursor.getString(21));
                activo.setImagen(cursor.getString(22));
                activo.setObservacion(cursor.getString(23));
                activo.setIdTipo(Integer.valueOf(cursor.getString(24)));
                activo.setIdEmpleado(Integer.valueOf(cursor.getString(25)));
                activo.setIdUbicacion(Integer.valueOf(cursor.getString(26)));
                activo.setIdInventario(Integer.valueOf(cursor.getString(27)));

                listaActivos.add(activo);
            }
        }

        cursor.close();
        db.close();

        return  listaActivos;
    }

    public ArrayList<Activo> GetAllInventario(String inventario_id){
        ArrayList<Activo> listaActivos = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(_Tabla, new String[]{
                        _id, _descripcion, _marca, _modelo, _serie, _estado,
                        _color, _alto, _ancho, _profundidad, _contenido, _peso, _nro, _fechaMantenimiento, _unidad, _cantidad, _material,
                        _codigoTIC, _codigoPAT, _codigoAF, _codigoGER, _otroCodigo, _Observacion, _imagen,
                        _TipoActivo_id, _Empleado_id, _Ubicacion_id, _Inventario_id
                },
                _Inventario_id + "=?", new String[]{String.valueOf(inventario_id)}, null, null, null);

        if (cursor != null){
            while (cursor.moveToNext()) {
                Activo activo = new Activo();
                activo.setId(cursor.getInt(0));
                activo.setDescripcion(cursor.getString(1));
                activo.setMarca(cursor.getString(2));
                activo.setModelo(cursor.getString(3));
                activo.setSerie(cursor.getString(4));
                activo.setEstado(cursor.getString(5));
                activo.setColor(cursor.getString(6));
                activo.setAlto(Float.valueOf(cursor.getString(7)));
                activo.setAncho(Float.valueOf(cursor.getString(8)));
                activo.setProfundidad(Float.valueOf(cursor.getString(9)));
                activo.setContenido(cursor.getString(10));
                activo.setPeso(Float.valueOf(cursor.getString(11)));
                activo.setNro(cursor.getString(12));
                activo.setFechaMantenimiento(cursor.getString(13));
                activo.setUnidad(cursor.getString(14));
                activo.setPeso(Integer.valueOf(cursor.getString(15)));
                activo.setMaterial(cursor.getString(16));
                activo.setCodigoTIC(cursor.getString(17));
                activo.setCodigoPAT(cursor.getString(18));
                activo.setCodigoAF(cursor.getString(19));
                activo.setCodigoGER(cursor.getString(20));
                activo.setOtroCodigo(cursor.getString(21));
                activo.setImagen(cursor.getString(22));
                activo.setObservacion(cursor.getString(23));
                activo.setIdTipo(Integer.valueOf(cursor.getString(24)));
                activo.setIdEmpleado(Integer.valueOf(cursor.getString(25)));
                activo.setIdUbicacion(Integer.valueOf(cursor.getString(26)));
                activo.setIdInventario(Integer.valueOf(cursor.getString(27)));

                listaActivos.add(activo);
            }
        }

        cursor.close();
        db.close();
        return  listaActivos;
    }

    public long Count(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if(db != null) {
            return  (long) DatabaseUtils.queryNumEntries(db, _Tabla);
        }
        db.close();

        return (long) -1;
    }

    //cantidad de activos de iun inventario
    public long CountId(int inventario_id){
        long count = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if(db != null) {
            Cursor cursor = db.query(_Tabla, null,
                    _Inventario_id + "=?", new String[]{String.valueOf(inventario_id)}, null, null, null);
            count = cursor.getCount();
            cursor.close();
        }
        db.close();

        return count;
    }


    private boolean ExisteSerie(String serie) {
        boolean result = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if(db != null && Count()>0) {
            Cursor cursor = dbHelper.getReadableDatabase().query(_Tabla, new String[]{_serie}, _serie + "=?", new String[]{serie}, null, null, null, null);
            result = (cursor.getCount() > 0);
            cursor.close();
        }
        db.close();

        return result;
    }
    private boolean Existe(String id) {
        boolean result = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if(db != null && Count()>0) {
            Cursor cursor = dbHelper.getReadableDatabase().query(_Tabla, new String[]{_serie}, _id + "=?", new String[]{id}, null, null, null, null);
            result = (cursor.getCount() > 0);
            cursor.close();
        }
        db.close();
        return result;
    }

}
