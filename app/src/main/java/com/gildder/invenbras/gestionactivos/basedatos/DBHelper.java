package com.gildder.invenbras.gestionactivos.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gildder on 22/10/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NOMBRE = "activofijo.db";
    public static final int    DB_VERSION  = 1;
    private final Context _context;

    private static final String CLASSNAME = DBHelper.class.getSimpleName();

    /**
     * Contructor
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);

        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(TB_ACTIVO);
            db.execSQL(TB_CAJA_FUERTE);
            db.execSQL(TB_COMPUTACION);
            db.execSQL(TB_EXTINTOR);
            db.execSQL(TB_MATERIAL);
            db.execSQL(TB_MUEBLE);
        }catch (Exception e){
            Log.e(getClass().getName(), CLASSNAME, e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS activo");
        db.execSQL("DROP TABLE IF EXISTS caja_fuerte");
        db.execSQL("DROP TABLE IF EXISTS computacion");
        db.execSQL("DROP TABLE IF EXISTS extintor");
        db.execSQL("DROP TABLE IF EXISTS material");
        db.execSQL("DROP TABLE IF EXISTS mueble");
        onCreate(db);
    }

    /* TABLAS DE LA BASE DATOS */
    private final String TB_ACTIVO =
            "CREATE TABLE activo ( " +
                    "    id             INTEGER (10) PRIMARY KEY AUTOINCREMENT " +
                    "                                NOT NULL, " +
                    "    caracteristica STRING, " +
                    "    tipo           STRING       NOT NULL, " +
                    "    modelo         STRING       NOT NULL, " +
                    "    marca          STRING       NOT NULL, " +
                    "    serie          STRING       NOT NULL, " +
                    "    estado         STRING       NOT NULL " +
                    "                                DEFAULT ('sin estado'), " +
                    "    codigoTic      STRING       NOT NULL, " +
                    "    codigoPat      STRING       NOT NULL, " +
                    "    codigoAf       STRING       NOT NULL, " +
                    "    codigoGer      STRING       NOT NULL, " +
                    "    otroCod        STRING, " +
                    "    observacion    TEXT,   " +
                    "    imagen         TEXT         NOT NULL" +
                    "                                DEFAULT ('foto.png') " +
                    "); ";

    private final String TB_CAJA_FUERTE =
            "CREATE TABLE caja_fuerte ( " +
            "    id          INTEGER(10) NOT NULL " +
            "                        PRIMARY KEY, " +
            "    alto        DECIMAL NOT NULL, " +
            "    ancho       DECIMAL NOT NULL, " +
            "    profundidad DECIMAL NOT NULL " +
            "); ";

    private final String TB_COMPUTACION =
            "CREATE TABLE computacion ( " +
                    "    id        INTEGER(10) PRIMARY KEY " +
                    "                      NOT NULL, " +
                    "    nroCaja   STRING  NOT NULL, " +
                    "    nroEquipo STRING  NOT NULL " +
                    "); ";

    private final String TB_EXTINTOR =
            "CREATE TABLE extintor ( " +
                    "    id                 INTEGER (10) PRIMARY KEY " +
                    "                                    NOT NULL, " +
                    "    codigo             STRING       NOT NULL, " +
                    "    numero             STRING       NOT NULL, " +
                    "    contenido          STRING       NOT NULL, " +
                    "    peso               STRING       NOT NULL, " +
                    "    fechaMantenimiento DATE         NOT NULL " +
                    ");";

    private final String TB_MATERIAL =
            "CREATE TABLE material ( " +
                    "    id       INTEGER (10) PRIMARY KEY " +
                    "                          NOT NULL, " +
                    "    nombre   STRING       NOT NULL, " +
                    "    cantidad INTEGER (10) NOT NULL " +
                    "                          DEFAULT (0), " +
                    "    unidad   STRING       NOT NULL " +
                    ");";

    private final String TB_MUEBLE =
            "CREATE TABLE mueble ( " +
                    "    id          INTEGER (10)    PRIMARY KEY " +
                    "                                NOT NULL, " +
                    "    alto        DECIMAL (10, 2) NOT NULL, " +
                    "    ancho       DECIMAL (10, 2) NOT NULL, " +
                    "    profundidad DECIMAL (10, 2) NOT NULL, " +
                    "    nroMueble   STRING          NOT NULL, " +
                    "    color       STRING          NOT NULL " +
                    ");";

    



}
