package com.gildder.invenbras.gestionactivos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.interfaces.RecyclerViewOnItemListener;
import com.gildder.invenbras.gestionactivos.clases.Inventario;

import java.util.ArrayList;

/**
 * Esta clase Gestionar la lista Adapter de los inventarios
 * Created by gildder on 03/10/2015.
 */
public class InventarioAdapter extends RecyclerView.Adapter<vhRowInventario> {
    private ArrayList<Inventario> inventarios;
    private int itemLayout;
    private Context context;

    //interface definida en interfaces
    private RecyclerViewOnItemListener recyclerViewOnItemListener;


    /**
     * Este metodo es el contructor de la clase.
     *
     * @param context El context de la actividad
     * @param inventarios La lista de inventarios
     * @param recyclerViewOnItemListener    El Evento seleccionado
     */
    public InventarioAdapter(Context context, ArrayList<Inventario> inventarios,
                             RecyclerViewOnItemListener recyclerViewOnItemListener) {
        this.context = context;
        this.inventarios = inventarios;
        this.recyclerViewOnItemListener = recyclerViewOnItemListener;
    }


    @Override
    public vhRowInventario onCreateViewHolder(ViewGroup viewGroup, final int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_inventario, viewGroup, false);
        vhRowInventario holder = new vhRowInventario(v,recyclerViewOnItemListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(vhRowInventario viewHolder, int position) {
        Inventario inventario = inventarios.get(position);

        viewHolder.itemView.setSelected(itemLayout == position);
        viewHolder.getLayoutPosition();

        viewHolder.nombre.setText(inventario.getNombre());
        viewHolder.descripcion.setText(inventario.getDescripcion());

        //color de prioridad
        switch (inventario.getPrioridad()){
            case "Alta":
                viewHolder.prioridad.setBackgroundResource(R.drawable.alta_prio_rect);
                break;
            case "Media":
                viewHolder.prioridad.setBackgroundResource(R.drawable.media_prio_rect);
                break;
            case "Baja":
                viewHolder.prioridad.setBackgroundResource(R.drawable.baja_prio_rect);
                break;
        }
        viewHolder.prioridad.setText(inventario.getPrioridad());

        viewHolder.nro.setText(inventario.getTotal());

    }

    @Override
    public int getItemCount() {
        return inventarios.size();
    }

    /**
     * Este metodo limpias la lista de inventarios
     */
    public void clearAdapter(){
        inventarios.clear();
        notifyDataSetChanged();
    }


}
