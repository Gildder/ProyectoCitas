package com.gildder.invenbras.gestionactivos.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gildder.invenbras.gestionactivos.ListaActivoActivity;
import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.models.Activo;
import com.gildder.invenbras.gestionactivos.models.Inventario;

import java.util.ArrayList;

/**
 * Created by gildder on 15/10/2015.
 */
public class ActivoAdapter extends RecyclerView.Adapter<vhRowActivo> {
    private ArrayList<Activo> listaActivos;
    private int itemLayout;
    private Context context;

    public ActivoAdapter(Context context, ArrayList<Activo> listaActivos, int itemLayout) {
        this.listaActivos = listaActivos;
        this.itemLayout = itemLayout;
        this.context = context;
    }

    @Override
    public vhRowActivo onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        vhRowActivo holder = new vhRowActivo(v);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListaActivoActivity.class); //cambiar por la vista de del activo
                context.startActivity(intent);
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(vhRowActivo viewHolder, int position) {
        Activo activo = listaActivos.get(position);

        viewHolder.itemView.setSelected(itemLayout == position);
        viewHolder.getLayoutPosition();

        viewHolder.modelo.setText(activo.getModelo());
        viewHolder.marca.setText(activo.getMarca());
        viewHolder.serie.setText(activo.getSerie());
        viewHolder.estado.setText(activo.getEstado());
        viewHolder.tipo.setText(activo.getTipo());
    }

    @Override
    public int getItemCount() {
        return listaActivos.size();
    }
}
