package com.gildder.invenbras.gestionactivos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.interfaces.RecyclerViewOnItemListener;
import com.gildder.invenbras.gestionactivos.clases.Activo;

import java.util.ArrayList;

/**
 * Created by gildder on 15/10/2015.
 */
public class ActivoAdapter extends RecyclerView.Adapter<vhRowActivo> {
    private ArrayList<Activo> activos;
    private int itemLayout;
    private Context context;
    //interface definida en interfaces
    private RecyclerViewOnItemListener recyclerViewOnItemListener;

    public ActivoAdapter(Context context, ArrayList<Activo> activos, RecyclerViewOnItemListener recyclerViewOnItemListener) {
        this.activos = activos;
        this.recyclerViewOnItemListener = recyclerViewOnItemListener;
        this.context = context;
    }

    @Override
    public vhRowActivo onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_activo, viewGroup, false);
        vhRowActivo holder = new vhRowActivo(v,recyclerViewOnItemListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(vhRowActivo viewHolder, int position) {
        Activo activo = activos.get(position);

        viewHolder.itemView.setSelected(itemLayout == position);
        viewHolder.getLayoutPosition();

        viewHolder.modelo.setText(activo.getModelo());
        viewHolder.marca.setText(activo.getMarca());
        viewHolder.serie.setText(activo.getSerie());
        switch (activo.getEstado()){
            case "S":
                viewHolder.estado.setBackgroundResource(R.drawable.estado_sin);
                viewHolder.estado.setText("Revisar");

                break;
            case "N":
                viewHolder.estado.setBackgroundResource(R.drawable.estado_no);
                viewHolder.estado.setText("No funciona");

                break;
            case "F":
                viewHolder.estado.setBackgroundResource(R.drawable.estado_fun);
                viewHolder.estado.setText("Funciona");

                break;
        }
        viewHolder.tipo.setText(activo.getModelo());
    }

    @Override
    public int getItemCount() {
        return activos.size();
    }

    /**
     * Este metodo limpias la lista de activo
     */
    public void clearAdapter(){
        activos.clear();
        notifyDataSetChanged();
    }

}
