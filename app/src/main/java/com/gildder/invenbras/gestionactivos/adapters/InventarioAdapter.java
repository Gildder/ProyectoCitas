package com.gildder.invenbras.gestionactivos.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gildder.invenbras.gestionactivos.ListaActivoActivity;
import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.models.Inventario;

import java.util.ArrayList;

/**
 * Esta clase Gestionar la lista Adapter de los inventarios
 * Created by gildder on 03/10/2015.
 */
public class InventarioAdapter extends RecyclerView.Adapter<vhRowInventario> {
    private ArrayList<Inventario> inventarios;
    private int itemLayout;
    private Context context;


    /**
     * Este metodo es el contructor de la clase.
     *
     * @param context El context de la actividad
     * @param inventarios La lista de inventarios
     * @param itemLayout    El item seleccionado
     */
    public InventarioAdapter(Context context, ArrayList<Inventario> inventarios, int itemLayout) {
        this.context = context;
        this.inventarios = inventarios;
        this.itemLayout = itemLayout;
    }

    @Override
    public vhRowInventario onCreateViewHolder(ViewGroup viewGroup, final int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        vhRowInventario holder = new vhRowInventario(v);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, ListaActivoActivity.class);
                intent.putExtra("ID",inventarios.get(position).getId());
                intent.putExtra("NOMBRE",inventarios.get(position).getNombre());
                context.startActivity(intent);
            }
        });


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
            case "A":
                viewHolder.prioridad.setBackgroundResource(R.drawable.alta_prio_rect);
                break;
            case "M":
                viewHolder.prioridad.setBackgroundResource(R.drawable.media_prio_rect);
                break;
            case "B":
                viewHolder.prioridad.setBackgroundResource(R.drawable.baja_prio_rect);
                break;
        }
        viewHolder.prioridad.setText(inventario.getPrioridad());



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
