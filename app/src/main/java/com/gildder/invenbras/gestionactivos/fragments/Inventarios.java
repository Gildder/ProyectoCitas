package com.gildder.invenbras.gestionactivos.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.adapters.InventarioAdapter;
import com.gildder.invenbras.gestionactivos.models.Inventario;

import java.util.ArrayList;


public class Inventarios extends Fragment {

    public Inventarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventarios, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Inventario> inventarios = new ArrayList<Inventario>();

        for (int i=0;i<10;i++) {
            Inventario inventario = new Inventario();
            inventario.setNombre("Juan pable");
            inventario.setDescripcion("Juan pable");
            inventario.setPrioridad("Juan pable");


            inventarios.add(inventario);
        }


        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.RclInventario);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new InventarioAdapter(inventarios, R.layout.row_inventario));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
