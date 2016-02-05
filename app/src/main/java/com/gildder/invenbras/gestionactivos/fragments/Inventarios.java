package com.gildder.invenbras.gestionactivos.fragments;

import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.Utils.Util;
import com.gildder.invenbras.gestionactivos.adapters.InventarioAdapter;
import com.gildder.invenbras.gestionactivos.clases.Activo;
import com.gildder.invenbras.gestionactivos.clases.Inventario;
import com.gildder.invenbras.gestionactivos.clases.Notificacion;
import com.gildder.invenbras.gestionactivos.clases.TipoActivo;
import com.gildder.invenbras.gestionactivos.clases.Ubicacion;
import com.gildder.invenbras.gestionactivos.interfaces.RecyclerViewOnItemListener;
import com.gildder.invenbras.gestionactivos.models.CActivo;
import com.gildder.invenbras.gestionactivos.models.CInventario;
import com.gildder.invenbras.gestionactivos.models.CNotificacion;
import com.gildder.invenbras.gestionactivos.models.CTipoActivo;
import com.gildder.invenbras.gestionactivos.models.CUbicacion;
import com.gildder.invenbras.gestionactivos.models.DBHelper;
import com.gildder.invenbras.gestionactivos.views.ListaActivoActivity;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;




public class Inventarios extends Fragment {
    private static final String APP_DIRECTORY = "myPictureApp/";
    private static final String MEDIA_DIRECTORY = APP_DIRECTORY + "activo" ;
    private static String TEMPORAL_PICTURE_NAME = "SinFoto.jpg";

    public static final int COUNT_INVENTARIO = 0;
    public static final String ID_ALMACEN = "ID_ALMACEN";
    private ArrayList<Inventario> inventarios = new ArrayList<Inventario>();
    private ArrayList<Activo> activos = new ArrayList<Activo>();
    private CActivo cActivo;
    private CInventario cInventario;
    private CTipoActivo cTipoActivo;
    private CUbicacion cUbicacion;
    private CNotificacion cNotificacion;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private ImageButton imbSync;
    private TextView txvCountInventario;
    private Activo activo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventarios, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new DBHelper(getActivity());


        CargarVista();


        txvCountInventario = (TextView) getActivity().findViewById(R.id.TxvCountInventario);

        //obtenemos el parametro enviado de la otra actividad
        final String almacen_id = getActivity().getIntent().getStringExtra(ID_ALMACEN);

        imbSync = (ImageButton) getActivity().findViewById(R.id.ImbSyncInventario);
        imbSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskUbicacion tareaUbicacion = new TaskUbicacion();
                tareaUbicacion.execute(almacen_id);

                Notificacion();

            }
        });

    }

    /**
     * Clase Tarea
     */

    public class TaskInventario extends AsyncTask<String,Integer,Boolean> {
        final static String SOAP_ACTION = Util.NAMESPACE + Util.METHOD_GET_INVENTARIO;
        SoapObject resSoapInventario;


        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            cInventario = CInventario.getInstance();
            cInventario.inicialize(dbHelper);

            //modelo del requestInventario inventario
            SoapObject request = new SoapObject(Util.NAMESPACE, Util.METHOD_GET_INVENTARIO);

            //modelo envolepe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            //modo de transporte
            HttpTransportSE transportSE = new HttpTransportSE(Util.URL,60000);

            try {
                transportSE.call(SOAP_ACTION, envelope);

                resSoapInventario = (SoapObject) envelope.getResponse();

                //validamos que la respuesta obtenida tiene valores
                if (resSoapInventario.getPropertyCount()<=0){
                    result = false;
                }else{
                    result = true;
                }



            }catch (Exception ex){
                result = false;

                //caso de no conectar a base central
                inventarios = cInventario.GetAll();

                if(inventarios.size()>0){
                    CargarVista();
                }

                Log.e("ERROR...", "Se encontraron problas al intentar conectarse Servidor -> " + ex.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result)
            {
                //reintanciamos la lista de inventarios
                inventarios = new ArrayList<Inventario>();

                //recoremos la lista de inventarios obtenidos
                for (int i = 0; i<resSoapInventario.getPropertyCount(); i++){
                    SoapObject ic = (SoapObject) resSoapInventario.getProperty(i);

                    Inventario inventario = new Inventario();
                    inventario.setId(Integer.parseInt(ic.getProperty(0).toString()));
                    inventario.setNombre(ic.getProperty(1).toString());
                    inventario.setDescripcion(ic.getProperty(2).toString());
                    inventario.setPrioridad(ic.getProperty(3).toString());

                    //añadimos a la lista de inventarios
                    inventarios.add(inventario);

                    //guardamos en base de datos sqlite
                    Long  resDB = cInventario.Insertar(inventario);


                    if (resDB==-1){
                        cInventario.Actualizar(inventario);
                    }


                }

                Log.e("RESULT...", "Request con Exito!");

            for(int i=0; i<inventarios.size(); i++) {

                TaskActivo taskActivo = new TaskActivo();
                taskActivo.execute(inventarios.get(i).getId());
            }

                CargarVista();
            }
            else
            {
                Log.e("ERROR...", "Problema en la consulta en inventario");
            }
        }


    }//fin clase Task

    public class TaskActivo extends AsyncTask<Integer,Integer,Boolean> {
        final static String SOAP_ACTION = Util.NAMESPACE + Util.METHOD_GET_ACTIVO;
        private SoapObject resSoapActivo;

        @Override
        protected Boolean doInBackground(Integer... params) {
            boolean result = true;
            cActivo = CActivo.getInstance();
            cActivo.inicialize(dbHelper);
/*
            int[] ids = new int[inventarios.size()];
            for(int i = 0; i<inventarios.size(); i++){
                ids[i] = inventarios.get(i).getId();
            }
*/
            //modelo del request
            SoapObject request = new SoapObject(Util.NAMESPACE, Util.METHOD_GET_ACTIVO);
            request.addProperty("ids",   params[0] );      //Paso de parametro

            //modelo envolepe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            //modo de transporte
            HttpTransportSE transportSE = new HttpTransportSE(Util.URL,60000);

            try {
                transportSE.call(SOAP_ACTION, envelope);

                resSoapActivo = (SoapObject) envelope.getResponse();

                //validamos que la respuesta obtenida tiene valores
                if (resSoapActivo.getPropertyCount()<=0){
                    result = false;
                }else{
                    result = true;
                }

            }catch (Exception ex){
                result = false;

                //caso de no conectar a base central
                activos = cActivo.GetAll();

                if(activos.size()>0){
                    CargarVista();
                }

                Log.e("ERROR...", "Se encontraron problemas en Activos al intentar conectarse Servidor -> " + ex.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result)
            {



                //reintanciamos la lista de inventarios
                activos = new ArrayList<Activo>();

                //recoremos la lista de inventarios obtenidos
                for (int i = 0; i<resSoapActivo.getPropertyCount(); i++){
                    SoapObject ic = (SoapObject) resSoapActivo.getProperty(i);

                    activo = new Activo();
                    activo.setId(Integer.parseInt(ic.getProperty(0).toString()));
                    activo.setDescripcion(ic.getProperty(1).toString());
                    activo.setMarca(ic.getProperty(2).toString());
                    activo.setModelo(ic.getProperty(3).toString());
                    activo.setSerie(ic.getProperty(4).toString());
                    activo.setEstado(ic.getProperty(5).toString());
                    activo.setColor(ic.getProperty(6).toString());
                    activo.setAlto(Float.parseFloat(ic.getProperty(7).toString().replace(",", ".")));
                    activo.setAncho(Float.parseFloat(ic.getProperty(8).toString().replace(",", ".")));
                    activo.setProfundidad(Float.parseFloat(ic.getProperty(9).toString().replace(",", ".")));
                    activo.setContenido(ic.getProperty(10).toString());
                    activo.setPeso(Float.parseFloat(ic.getProperty(11).toString().replace(",", ".")));
                    activo.setNro(ic.getProperty(12).toString());
                    activo.setFechaMantenimiento(ic.getProperty(13).toString());
                    activo.setUnidad(ic.getProperty(14).toString());
                    activo.setCantidad(Integer.parseInt(ic.getProperty(15).toString()));
                    activo.setMaterial(ic.getProperty(16).toString());
                    activo.setCodigoTIC(ic.getProperty(17).toString());
                    activo.setCodigoPAT(ic.getProperty(18).toString());
                    activo.setCodigoAF(ic.getProperty(19).toString());
                    activo.setCodigoGER(ic.getProperty(20).toString());
                    activo.setOtroCodigo(ic.getProperty(21).toString());

                    try {
                        DescargarImage((SoapPrimitive) ic.getProperty(22),activo.getIdInventario() + "_" + String.valueOf(activo.getId())  + "_" + activo.getSerie());
                        Log.e("IMG", ic.getProperty(22).toString());
                    } catch (Exception e) {
                        Log.e("ERROR:","Ocurrio un error al descargar la imagenes del servidor");
                        e.printStackTrace();
                    }

                    activo.setObservacion(ic.getProperty(23).toString());
                    activo.setIdTipo(Integer.parseInt(ic.getProperty(24).toString()));
                    activo.setIdEmpleado(Integer.parseInt(ic.getProperty(25).toString()));
                    activo.setIdUbicacion(Integer.parseInt(ic.getProperty(26).toString()));
                    activo.setIdInventario(Integer.parseInt(ic.getProperty(27).toString()));

                    //adicionamos a la lista de activos
                    activos.add(activo);

                    //guardamos en base de datos sqlite
                    Long  resDB = cActivo.Insertar(activo);

                    if (resDB==-1){
                        cActivo.Actualizar(activo);
                    }
                }

                Log.e("RESULT...", "Request con Exito!");

                //recoremos la lista de inventarios obtenidos
                for (int i = 0; i<inventarios.size(); i++){
                    inventarios.get(i).setTotal(String.valueOf(cActivo.CountId(inventarios.get(i).getId())));
                }

                //obtenemos los tipo de activos de la base de datos
                TaskTipoActivo tareaTipo = new TaskTipoActivo();
                tareaTipo.execute();


                CargarVista();
            }
            else
            {
                Log.e("ERROR...", "Problemas en la consulta");
            }
        }

    }//fin clase Task

    private void DescargarImage(Object param,String nombre) throws Exception {
        byte[] bytes = Base64.decode(param.toString(),Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        ImageView imageView = new ImageView(getActivity());
        imageView.setImageBitmap(bmp);

        TEMPORAL_PICTURE_NAME = nombre + ".jpg";


        saveToBitmap(bmp);



    }

    public void saveToBitmap(Bitmap outputImage){
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();

        String path = Environment.getExternalStorageDirectory() + file.separator
                + MEDIA_DIRECTORY + file.separator + TEMPORAL_PICTURE_NAME;


        File newFile = new File(path);
        activo.setImagen(path);

        try {
            FileOutputStream out = new FileOutputStream(newFile);
            outputImage.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }




    public class TaskUbicacion extends AsyncTask<String,Integer,Boolean> {
        final static String SOAP_ACTION = Util.NAMESPACE + Util.METHOD_GET_UBICACION;
        SoapObject resSoapUbicacion;

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            cUbicacion = CUbicacion.getInstance();
            cUbicacion.inicialize(dbHelper);

            //modelo del request
            SoapObject request = new SoapObject(Util.NAMESPACE, Util.METHOD_GET_UBICACION);
            request.addProperty("id",   params[0] );      //Paso de parametro

            //modelo envolepe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            //modo de transporte
            HttpTransportSE transportSE = new HttpTransportSE(Util.URL,60000);

            try {
                transportSE.call(SOAP_ACTION, envelope);

                resSoapUbicacion = (SoapObject) envelope.getResponse();

                //validamos que la respuesta obtenida tiene valores
                if (resSoapUbicacion.getPropertyCount()<=0){
                    result = false;
                }else{
                    result = true;
                }




            }catch (Exception ex){
                result = false;

                Log.e("ERROR...", "Se encontraron problas al intentar conectarse Servidor -> " + ex.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result)
            {
                //recoremos la lista de inventarios obtenidos
                for (int i = 0; i<resSoapUbicacion.getPropertyCount(); i++){
                    SoapObject ic = (SoapObject) resSoapUbicacion.getProperty(i);

                    Ubicacion ubicacion = new Ubicacion();
                    ubicacion.setId(Integer.parseInt(ic.getProperty(0).toString()));
                    ubicacion.setSector(ic.getProperty(1).toString());
                    ubicacion.setArea(ic.getProperty(2).toString());
                    ubicacion.setLugar(ic.getProperty(3).toString());


                    //guardamos en base de datos sqlite
                    Long  resDB = cUbicacion.Insertar(ubicacion);

                    if (resDB==-1){
                        cUbicacion.Actualizar(ubicacion);
                    }
                }


                Log.e("RESULT...", "Request con Exito!");

                //Obtenemos los datos de los inventarios
                TaskInventario tarea = new TaskInventario();
                tarea.execute();
            }
            else
            {
                Log.e("ERROR...", "Problemas en la consulta");
            }
        }


    }//fin clase Task




    public class TaskTipoActivo extends AsyncTask<String,Integer,Boolean> {
        final static String SOAP_ACTION = Util.NAMESPACE + Util.METHOD_GET_TIPOACTIVO;
        private SoapPrimitive resSoapTipo;



        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            cTipoActivo = CTipoActivo.getInstance();
            cTipoActivo.inicialize(dbHelper);


            //modelo del request
            SoapObject requestTipo = new SoapObject(Util.NAMESPACE, Util.METHOD_GET_TIPOACTIVO);

            //modelo envolepe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(requestTipo);

            //modo de transporte
            HttpTransportSE transportSE = new HttpTransportSE(Util.URL);

            try {
                transportSE.call(SOAP_ACTION, envelope);

                resSoapTipo = (SoapPrimitive) envelope.getResponse();

                //validamos que la respuesta obtenida tiene valores
                if (resSoapTipo.toString().equals("")){
                    result = false;
                }else{
                    result = true;
                }

            }catch (Exception ex){
                result = false;

                Log.e("ERROR...", "Se encontraron problemas al intentar conectarse Servidor -> " + ex.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            JSONArray jArray = new JSONArray();

            if (result)
            {
                try {
                    jArray = new JSONArray(resSoapTipo.toString());


                    //recoremos la lista de inventarios obtenidos
                    for (int i = 0; i<jArray.length(); i++){

                        TipoActivo tipoActivo = new TipoActivo();
                        tipoActivo.setId(Integer.parseInt(jArray.getJSONObject(i).getString("Id")));
                        tipoActivo.setTipo(jArray.getJSONObject(i).getString("Tipo"));
                        tipoActivo.setNombre(jArray.getJSONObject(i).getString("Nombre"));
                        tipoActivo.setDescripcion(jArray.getJSONObject(i).getString("Descripcion"));


                        //guardamos en base de datos sqlite
                        Long  resDB = cTipoActivo.Insertar(tipoActivo);

                        if (resDB==-1){
                            cTipoActivo.Actualizar(tipoActivo);
                        }
                    }

                    Log.e("RESULT...", "Request con Exito!");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Log.e("ERROR...", "Problema en la consulta");
            }
        }


    }//fin clase Task


    //**********************************************************************************************

    private void CargarVista(){
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.RclInventario);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new InventarioAdapter(getActivity(), inventarios, new RecyclerViewOnItemListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(getActivity(), inventarios.get(position).getNombre(), Toast.LENGTH_SHORT).show();

                Intent listaActivoIntent = new Intent(getActivity(), ListaActivoActivity.class);
                String id = String.valueOf(inventarios.get(position).getId());
                listaActivoIntent.putExtra("ID_INVENTARIO", id);
                listaActivoIntent.putExtra("NOMBRE", inventarios.get(position).getNombre());

                startActivityForResult(listaActivoIntent,COUNT_INVENTARIO);

            }



        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case COUNT_INVENTARIO:
                if (resultCode == getActivity().RESULT_OK) {
                    for (int i = 0; i<inventarios.size(); i++){
                        inventarios.get(i).setTotal(String.valueOf(cActivo.CountId(inventarios.get(i).getId())));
                    }
                    CargarVista();
                }
                break;
        }

    }

    public void Notificacion(){
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setLargeIcon((((BitmapDrawable) getResources()
                        .getDrawable(R.drawable.ic_cast_light)).getBitmap()))
                .setContentTitle("Sincronizacion..")
                .setContentText("Confirmar por favor")
                .setTicker("Inventario")
                .setContentInfo("1");

        Intent intent = new Intent(getActivity(), Inventarios.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),0,intent,0);

        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(10,notification.build());


        cNotificacion = CNotificacion.getInstance();
        cNotificacion.inicialize(dbHelper);

        Notificacion notificacion = new Notificacion();
        notificacion.setTitulo("Sincronizacion");
        notificacion.setDescripcion("El inventario se sincronizi correctamente...");
        notificacion.setTipo("S");

        long b = cNotificacion.Insertar(notificacion);


    }

    @Override
    public void onResume() {
        super.onResume();

        for (int i = 0; i<inventarios.size(); i++){
            inventarios.get(i).setTotal(String.valueOf(cActivo.CountId(inventarios.get(i).getId())));
        }

    }
}
