package com.gildder.invenbras.gestionactivos.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gildder.invenbras.gestionactivos.clases.Activo;
import com.gildder.invenbras.gestionactivos.clases.TipoActivo;
import com.gildder.invenbras.gestionactivos.clases.Ubicacion;
import com.gildder.invenbras.gestionactivos.models.CActivo;
import com.gildder.invenbras.gestionactivos.models.CInventario;
import com.gildder.invenbras.gestionactivos.models.CTipoActivo;
import com.gildder.invenbras.gestionactivos.models.CUbicacion;
import com.gildder.invenbras.gestionactivos.models.DBHelper;
import com.gildder.invenbras.gestionactivos.views.ListaActivoActivity;
import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.Utils.Util;
import com.gildder.invenbras.gestionactivos.adapters.InventarioAdapter;
import com.gildder.invenbras.gestionactivos.interfaces.RecyclerViewOnItemListener;
import com.gildder.invenbras.gestionactivos.clases.Inventario;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;



public class Inventarios extends Fragment {
    public static final int COUNT_INVENTARIO = 0;
    public static final String ID_ALMACEN = "ID_ALMACEN";
    private ArrayList<Inventario> inventarios = new ArrayList<Inventario>();
    private ArrayList<Activo> activos = new ArrayList<Activo>();
    private CActivo cActivo;
    private CInventario cInventario;
    private CTipoActivo cTipoActivo;
    private CUbicacion cUbicacion;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private ImageButton imbSync;
    private TextView txvCountInventario;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventarios, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CargarVista();

        //instanciando los modelos de la base de datos.
        dbHelper = new DBHelper(getActivity());
        cInventario = CInventario.getInstance();
        cInventario.inicialize(dbHelper);

        cTipoActivo = CTipoActivo.getInstance();
        cTipoActivo.inicialize(dbHelper);

        cActivo = CActivo.getInstance();
        cActivo.inicialize(dbHelper);

        cUbicacion = CUbicacion.getInstance();
        cUbicacion.inicialize(dbHelper);



        txvCountInventario = (TextView) getActivity().findViewById(R.id.TxvCountInventario);

        //obtenemos el parametro enviado de la otra actividad
        final String almacen_id = getActivity().getIntent().getStringExtra(ID_ALMACEN);

        imbSync = (ImageButton) getActivity().findViewById(R.id.ImbSyncInventario);
        imbSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskInventario tarea = new TaskInventario();
                tarea.execute();

                TaskTipoActivo tareaTipo = new TaskTipoActivo();
                tareaTipo.execute();

                TaskUbicacion tareaUbicacion = new TaskUbicacion();
                tareaUbicacion.execute(almacen_id);

            }
        });







    }




    /**
     * Clase Tarea
     */

    public class TaskInventario extends AsyncTask<String,Integer,Boolean> {
        final static String SOAP_ACTION = Util.NAMESPACE + Util.METHOD_GET_INVENTARIO;



        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            //modelo del request inventario
            SoapObject request = new SoapObject(Util.NAMESPACE, Util.METHOD_GET_INVENTARIO);

            //modelo envolepe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            //modo de transporte
            HttpTransportSE transportSE = new HttpTransportSE(Util.URL,60000);

            try {
                transportSE.call(SOAP_ACTION, envelope);

                SoapObject resSoap = (SoapObject) envelope.getResponse();

                //validamos que la respuesta obtenida tiene valores
                if (resSoap.getPropertyCount()<=0){
                    result = false;
                }

                //reintanciamos la lista de inventarios
                inventarios = new ArrayList<Inventario>();

                //recoremos la lista de inventarios obtenidos
                for (int i = 0; i<resSoap.getPropertyCount(); i++){
                    SoapObject ic = (SoapObject) resSoap.getProperty(i);

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
                Log.e("RESULT...", "Request con Exito!");

                //recoremos la lista de inventarios obtenidos
                for (int i = 0; i<inventarios.size(); i++){
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

    public class TaskUbicacion extends AsyncTask<String,Integer,Boolean> {
        final static String SOAP_ACTION = Util.NAMESPACE + Util.METHOD_GET_UBICACION;


        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            //modelo del request
            SoapObject request = new SoapObject(Util.NAMESPACE, Util.METHOD_GET_UBICACION);
            request.addProperty("almacen",   params[0] );      //Paso de parametro

            //modelo envolepe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            //modo de transporte
            HttpTransportSE transportSE = new HttpTransportSE(Util.URL,60000);

            try {
                transportSE.call(SOAP_ACTION, envelope);

                SoapObject resSoap = (SoapObject) envelope.getResponse();

                //validamos que la respuesta obtenida tiene valores
                if (resSoap.getPropertyCount()<=0){
                    result = false;
                }

                //recoremos la lista de inventarios obtenidos
                for (int i = 0; i<resSoap.getPropertyCount(); i++){
                    SoapObject ic = (SoapObject) resSoap.getProperty(i);

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
                Log.e("RESULT...", "Request con Exito!");

            }
            else
            {
                Log.e("ERROR...", "Problemas en la consulta");
            }
        }


    }//fin clase Task

    public class TaskTipoActivo extends AsyncTask<String,Integer,Boolean> {
        final static String SOAP_ACTION = Util.NAMESPACE + Util.METHOD_GET_TIPOACTIVO;


        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            //modelo del request
            SoapObject request = new SoapObject(Util.NAMESPACE, Util.METHOD_GET_TIPOACTIVO);

            //modelo envolepe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            //modo de transporte
            HttpTransportSE transportSE = new HttpTransportSE(Util.URL,60000);

            try {
                transportSE.call(SOAP_ACTION, envelope);

                SoapObject resSoap = (SoapObject) envelope.getResponse();

                //validamos que la respuesta obtenida tiene valores
                if (resSoap.getPropertyCount()<=0){
                    result = false;
                }

                //recoremos la lista de inventarios obtenidos
                for (int i = 0; i<resSoap.getPropertyCount(); i++){
                    SoapObject ic = (SoapObject) resSoap.getProperty(i);

                    TipoActivo tipoActivo = new TipoActivo();
                    tipoActivo.setId(Integer.parseInt(ic.getProperty(0).toString()));
                    tipoActivo.setTipo(ic.getProperty(1).toString());
                    tipoActivo.setNombre(ic.getProperty(2).toString());
                    tipoActivo.setDescripcion(ic.getProperty(3).toString());


                    //guardamos en base de datos sqlite
                    Long  resDB = cTipoActivo.Insertar(tipoActivo);

                    if (resDB==-1){
                        cTipoActivo.Actualizar(tipoActivo);
                    }
                }



            }catch (Exception ex){
                result = false;

                Log.e("ERROR...", "Se encontraron problemas al intentar conectarse Servidor -> " + ex.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result)
            {
                Log.e("RESULT...", "Request con Exito!");

            }
            else
            {
                Log.e("ERROR...", "Problema en la consulta");
            }
        }


    }//fin clase Task

    public class TaskActivo extends AsyncTask<Integer,Integer,Boolean> {
        final static String SOAP_ACTION = Util.NAMESPACE + Util.METHOD_GET_ACTIVO;


        @Override
        protected Boolean doInBackground(Integer... params) {
            boolean result = true;

            //modelo del request
            SoapObject request = new SoapObject(Util.NAMESPACE, Util.METHOD_GET_ACTIVO);
            request.addProperty("id",   params[0] );      //Paso de parametro

            //modelo envolepe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            //modo de transporte
            HttpTransportSE transportSE = new HttpTransportSE(Util.URL,60000);

            try {
                transportSE.call(SOAP_ACTION, envelope);

                SoapObject resSoap = (SoapObject) envelope.getResponse();

                //validamos que la respuesta obtenida tiene valores
                if (resSoap.getPropertyCount()<=0){
                    result = false;
                }

                //reintanciamos la lista de inventarios
                activos = new ArrayList<Activo>();

                //recoremos la lista de inventarios obtenidos
                for (int i = 0; i<resSoap.getPropertyCount(); i++){
                    SoapObject ic = (SoapObject) resSoap.getProperty(i);

                    Activo activo = new Activo();
                    activo.setId(Integer.parseInt(ic.getProperty(0).toString()));
                    activo.setDescripcion(ic.getProperty(1).toString());
                    activo.setMarca(ic.getProperty(2).toString());
                    activo.setModelo(ic.getProperty(3).toString());
                    activo.setSerie(ic.getProperty(4).toString());
                    activo.setEstado(ic.getProperty(5).toString());
                    activo.setColor(ic.getProperty(6).toString());
                    activo.setAlto(Float.parseFloat(ic.getProperty(7).toString().replace(",",".")));
                    activo.setAncho(Float.parseFloat(ic.getProperty(8).toString().replace(",", ".")));
                    activo.setProfundidad(Float.parseFloat(ic.getProperty(9).toString().replace(",", ".")));
                    activo.setContenido(ic.getProperty(10).toString());
                    activo.setPeso(Float.parseFloat(ic.getProperty(11).toString().replace(",",".")));
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
                    activo.setImagen(ic.getProperty(22).toString());
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
                Log.e("RESULT...", "Request con Exito!");
                //recoremos la lista de inventarios obtenidos
                for (int i = 0; i<inventarios.size(); i++){
                    inventarios.get(i).setTotal(String.valueOf(cActivo.CountId(inventarios.get(i).getId())));
                }
                CargarVista();
            }
            else
            {
                Log.e("ERROR...", "Problemas en la consulta");
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
                listaActivoIntent.putExtra("ID_INVENTARIO", inventarios.get(position).getId());
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


}
