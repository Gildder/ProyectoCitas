package com.gildder.invenbras.gestionactivos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.models.Inventario;

import java.util.ArrayList;

/**
 * Created by gildder on 03/10/2015.
 */
public class InventarioAdapter extends RecyclerView.Adapter<InventarioAdapter.ViewHolder> {
    private ArrayList<Inventario> inventarios;
    private int itemLayout;

    public InventarioAdapter(ArrayList<Inventario> inventarios, int itemLayout) {
        this.inventarios = inventarios;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Inventario inventario = inventarios.get(position);

        viewHolder.nombre.setText(inventario.getNombre());
        viewHolder.descripcion.setText(inventario.getDescripcion());
        viewHolder.priori.setText(inventario.getPrioridad());
    }

    @Override
    public int getItemCount() {
        return inventarios.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder{
        public int id;
        public TextView nombre;
        public TextView descripcion;
        public TextView priori;

        public ViewHolder(View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.TxvNombre);
            descripcion = (TextView) itemView.findViewById(R.id.TxvDescripcion);
            priori = (TextView) itemView.findViewById(R.id.TxvPriori);

        }
    }

}
