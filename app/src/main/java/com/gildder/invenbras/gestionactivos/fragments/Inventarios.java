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
import android.widget.Toast;

import com.gildder.invenbras.gestionactivos.ListaActivoActivity;
import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.Utils.Util;
import com.gildder.invenbras.gestionactivos.adapters.InventarioAdapter;
import com.gildder.invenbras.gestionactivos.interfaces.RecyclerViewOnItemListener;
import com.gildder.invenbras.gestionactivos.models.Inventario;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Random;


public class Inventarios extends Fragment {
    final ArrayList<Inventario> inventarios = new ArrayList<Inventario>();

    public Inventarios() {

        TaskInventario tarea = new TaskInventario();
        tarea.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventarios, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Clase Tarea
     */

    public class TaskInventario extends AsyncTask<String,Integer,Boolean> {
        final static String SOAP_ACTION = Util.NAMESPACE + Util.METHOD_GET_INVENTARIO;


        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            //modelo del request
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

                if (resSoap.getPropertyCount()<=0){
                    result = false;
                }


                for (int i = 0; i<resSoap.getPropertyCount(); i++){
                    SoapObject ic = (SoapObject) resSoap.getProperty(i);

                    Inventario inventario = new Inventario();
                    inventario.setId(Integer.parseInt(ic.getProperty(0).toString()));
                    inventario.setNombre(ic.getProperty(1).toString());
                    inventario.setDescripcion(ic.getProperty(2).toString());
                    inventario.setPrioridad(ic.getProperty(3).toString());

                    //añadimos a la lista de inventarios
                    inventarios.add(inventario);
                }


            }catch (Exception ex){
                result = false;
                Log.e("ERROR...", ex.getMessage());
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result)
            {
                Log.e("RESULT...", "Request con Exito!");


                RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.RclInventario);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(new InventarioAdapter(getActivity(), inventarios, new RecyclerViewOnItemListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Toast toast = Toast.makeText(getActivity(), inventarios.get(position).getNombre(), Toast.LENGTH_SHORT);
                        toast.show();

                        Intent intent = new Intent(getActivity(), ListaActivoActivity.class);
                        intent.putExtra("ID",inventarios.get(position).getId());
                        intent.putExtra("NOMBRE",inventarios.get(position).getNombre());

                        int cantidad = 0;
                        startActivityForResult(intent,cantidad);

                    }
                }));
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());


            }
            else
            {
                Log.e("ERROR...", "Problema en la consulta");
            }
        }


    }//fin clase Task

}
