package com.gildder.invenbras.gestionactivos.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by gildder on 22/10/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.GestionActivo/databases/";
    public static final String DB_NOMBRE = "MiActivo.db";
    public static final int    DB_VERSION  = 17;
    private final Context _context;

    private static final String CLASSNAME = DBHelper.class.getSimpleName();

    /**
     * Contructor
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);

        this._context = context;
    }

    /**
     * This constructor copies the database file if the copyDatabase argument is
     * <code>true</code>. It keeps a reference to the passed context in order to
     * access the application's assets.
     *
     * @param context
     *            Context to be used
     * @param copyDatabase
     *            If <code>true</code>, the database file is copied (if it does
     *            not already exist)
     */
    public DBHelper(Context context, boolean copyDatabase) {
        // call overloaded constructor
        this(context);
        // copy database file in case desired
        if (copyDatabase) {
            copyDatabaseToFile();
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(TB_NOTIFICACION);
            db.execSQL(TB_INVENTARIO);
            db.execSQL(TB_UBICACION);
            db.execSQL(TB_TIPOACTIVO);
            db.execSQL(TB_ACTIVO);
        }catch (Exception e){
            Log.e(getClass().getName(), CLASSNAME, e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notificacion");
        db.execSQL("DROP TABLE IF EXISTS inventario");
        db.execSQL("DROP TABLE IF EXISTS activo");
        db.execSQL("DROP TABLE IF EXISTS ubicacion");
        db.execSQL("DROP TABLE IF EXISTS tipoactivo");
        onCreate(db);
    }

    public void copyDatabaseToFile() {

        // variables
        InputStream myInput  = null;
        OutputStream myOutput = null;
        SQLiteDatabase  database = null;

        // only proceed in case the database does not exist
        if (!isDataBaseExiste()) {
            // get the database
            database = this.getReadableDatabase();
            try {
                // Open your local db as the input stream
                myInput = _context.getAssets().open(DB_NOMBRE);

                // Path to the just created empty db
                String outFileName = DB_PATH + DB_NOMBRE;

                // Open the empty db as the output stream
                myOutput = new FileOutputStream(outFileName);

                // transfer bytes from the input file to the output file
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
            } catch (FileNotFoundException e) {
                // handle your exception here
            } catch (IOException e) {
                // handle your exception here
            } finally {
                try {
                    // Close the streams
                    myOutput.flush();
                    myOutput.close();
                    myInput.close();
                    // close the database in case it is opened
                    if (database != null && database.isOpen()) {
                        database.close();
                    }

                } catch (Exception e) {
                    // handle your exception here
                }
            }
        }
    }

    /**
     * Verifica si existe la base de datos
     * @return
     * 		<code>True</code>, Si existe la base datos.
     */
    private boolean isDataBaseExiste() {

        // database to be verified
        SQLiteDatabase dbToBeVerified = null;

        try {
            // get database path
            String dbPath = DB_PATH + DB_NOMBRE;
            // try to open the database
            dbToBeVerified = SQLiteDatabase.openDatabase(dbPath, null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            // do nothing since the database does not exist
        }

        // in case it exists, close it
        if (dbToBeVerified != null) {
            // close DB
            dbToBeVerified.close();

        }

        // in case there is a DB entity, the DB exists
        return dbToBeVerified != null ? true : false;
    }

    /* TABLAS DE LA BASE DATOS */
    private final String TB_INVENTARIO =
            "CREATE TABLE inventario (" +
                    "    id          INTEGER (10) PRIMARY KEY" +
                    "                             NOT NULL," +
                    "    nombre      VARCHAR (30) NOT NULL," +
                    "    descripcion TEXT         NOT NULL," +
                    "    prioridad   CHAR (1)     NOT NULL" +
                    "); ";

    private final String TB_ACTIVO =
            "CREATE TABLE activo ( " +
                    "   id                 INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                    "    descripcion        TEXT         NOT NULL,\n" +
                    "    marca              VARCHAR (20) NOT NULL,\n" +
                    "    modelo             VARCHAR (20) NOT NULL,\n" +
                    "    serie              VARCHAR (20) NOT NULL,\n" +
                    "    estado             CHAR (1)     NOT NULL\n" +
                    "                                    DEFAULT ('s'),\n" +
                    "    color              VARCHAR (30) NOT NULL\n" +
                    "                                    DEFAULT ('ninguno'),\n" +
                    "    alto               REAL         NOT NULL\n" +
                    "                                    DEFAULT (0),\n" +
                    "    ancho              REAL         NOT NULL\n" +
                    "                                    DEFAULT (0),\n" +
                    "    profundidad        REAL         NOT NULL\n" +
                    "                                    DEFAULT (0),\n" +
                    "    contenido          VARCHAR (45) NOT NULL\n" +
                    "                                    DEFAULT ('ninguno'),\n" +
                    "    peso               REAL         NOT NULL\n" +
                    "                                    DEFAULT (0),\n" +
                    "    nro                VARCHAR (20) NOT NULL\n" +
                    "                                    DEFAULT ('0'),\n" +
                    "    fechaMantenimiento VARCHAR (45) NOT NULL\n" +
                    "                                    DEFAULT ('ninguno'),\n" +
                    "    unidad             VARCHAR (30) NOT NULL\n" +
                    "                                    DEFAULT ('unidad'),\n" +
                    "    cantidad           INTEGER (10) NOT NULL\n" +
                    "                                    DEFAULT (1),\n" +
                    "    material           VARCHAR (45) NOT NULL\n" +
                    "                                    DEFAULT ('ninguno'),\n" +
                    "    codigoTIC          VARCHAR (45) NOT NULL,\n" +
                    "    codigoPatrimonio   VARCHAR (45) NOT NULL,\n" +
                    "    codigoActivoFijo   VARCHAR (45) NOT NULL,\n" +
                    "    codigoGerencia     VARCHAR (45) NOT NULL,\n" +
                    "    otroCodigo         VARCHAR (45) NOT NULL,\n" +
                    "    imagen             VARCHAR (200) NOT NULL,\n" +
                    "    observacion        VARCHAR (200) NOT NULL,\n" +
                    "    tipoActivo_id      INTEGER (10) NOT NULL,\n" +
                    "    empleado_id        INTEGER (10) NOT NULL,\n" +
                    "    ubicacion_id       INTEGER (10) NOT NULL,\n" +
                    "    inventario_id      INTEGER (10) NOT NULL" +
                    "); ";

    private final String TB_UBICACION =
            "CREATE TABLE ubicacion (\n" +
                    "    id     INTEGER (10) PRIMARY KEY\n" +
                    "                        NOT NULL,\n" +
                    "    sector VARCHAR (20) NOT NULL,\n" +
                    "    area   VARCHAR (45) NOT NULL,\n" +
                    "    lugar  VARCHAR (45) NOT NULL\n" +
                    "); ";

    private final String TB_TIPOACTIVO=
            "CREATE TABLE tipoactivo (\n" +
                    "    id          INTEGER (10) PRIMARY KEY\n" +
                    "                             NOT NULL,\n" +
                    "    tipo        VARCHAR (45) NOT NULL,\n" +
                    "    nombre      VARCHAR (20) NOT NULL,\n" +
                    "    descripcion TEXT         NOT NULL\n" +
                    ");\n ";

    private final String TB_NOTIFICACION =
            "CREATE TABLE notificacion (" +
                    "    id          INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    titulo      VARCHAR (20) NOT NULL," +
                    "    descripcion TEXT      NOT NULL," +
                    "    fecha       VARCHAR (30)     NOT NULL" +
                    "                             DEFAULT ('08/12/2015')," +
                    "    tipo        VARCHAR (20) NOT NULL" +
                    "); ";

}
