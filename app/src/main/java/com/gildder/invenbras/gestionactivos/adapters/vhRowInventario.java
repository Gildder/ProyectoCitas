package com.gildder.invenbras.gestionactivos.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.interfaces.RecyclerViewOnItemListener;

/**
 * Esta clase relacion los view del Xml row_inventario
 *
 * Created by gildder on 15/10/2015.
 */
public class vhRowInventario extends RecyclerView.ViewHolder implements View.OnClickListener{
    protected TextView nombre;
    protected TextView descripcion;
    protected TextView prioridad;
    protected TextView nro;
    protected RelativeLayout relativeLayout;
    protected CardView cardView;

    private RecyclerViewOnItemListener recyclerViewOnItemListener;

    public vhRowInventario(View itemView, RecyclerViewOnItemListener recyclerViewOnItemListener) {
        super(itemView);

        this.nombre = (TextView) itemView.findViewById(R.id.TxvModelo);
        this.descripcion = (TextView) itemView.findViewById(R.id.TxvMarca);
        this.prioridad = (TextView) itemView.findViewById(R.id.TxvPrioridadInventario);
        this.nro = (TextView) itemView.findViewById(R.id.TxvCountInventario);

        this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.RltContentInventario);
        this.cardView = (CardView) itemView.findViewById(R.id.cardViewInventario);

        this.recyclerViewOnItemListener = recyclerViewOnItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        recyclerViewOnItemListener.onClick(v, getAdapterPosition());
    }
}
