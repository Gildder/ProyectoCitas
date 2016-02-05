package com.gildder.invenbras.gestionactivos.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.adapters.InventarioAdapter;
import com.gildder.invenbras.gestionactivos.adapters.NotificacionAdapter;
import com.gildder.invenbras.gestionactivos.clases.Notificacion;
import com.gildder.invenbras.gestionactivos.interfaces.RecyclerViewOnItemListener;
import com.gildder.invenbras.gestionactivos.models.CNotificacion;
import com.gildder.invenbras.gestionactivos.models.DBHelper;
import com.gildder.invenbras.gestionactivos.views.ListaActivoActivity;

import java.util.ArrayList;

public class Notificaciones extends Fragment {
    private RecyclerView recyclerView;
    public static ArrayList<Notificacion> lista = new ArrayList<Notificacion>();
    private CNotificacion cNotificacion;
    private DBHelper dbHelper;

     static Notificaciones intance;

    public static Notificaciones getIntance() {
        if(intance != null)
            return intance;
        else
            return new Notificaciones();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notificaciones, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new DBHelper(getActivity());

        cNotificacion = CNotificacion.getInstance();
        cNotificacion.inicialize(dbHelper);

        if(cNotificacion.Count()>0)
            lista = cNotificacion.GetAll();

        CargarVista();

    }


    private void CargarVista(){
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.RclNotificacion);
        recyclerView.setHasFixedSize(true);
             recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new NotificacionAdapter(getActivity(), lista, new RecyclerViewOnItemListener() {
            @Override
            public void onClick(View v, int position) {


            }


        }));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onStart() {
        super.onStart();
        CargarVista();

    }

    @Override
    public void onResume() {
        super.onResume();

        CargarVista();

    }
}
