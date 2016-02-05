package com.gildder.invenbras.gestionactivos.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.interfaces.RecyclerViewOnItemListener;

/**
 * Esta clase relacion los view del Xml row_activo
 *
 * Created by gildder on 15/10/2015.
 */
public class vhRowNotificacion extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    protected TextView titulo;
    protected TextView descripcion;
    protected TextView fecha;
    protected TextView estado;
    protected ImageView foto;
    protected LinearLayout linearLayout;
    protected CardView cardView;

    private RecyclerViewOnItemListener recyclerViewOnItemListener;


    public vhRowNotificacion(View itemView, RecyclerViewOnItemListener recyclerViewOnItemListener) {
        super(itemView);

        this.titulo = (TextView) itemView.findViewById(R.id.TxvTitulo);
        this.descripcion = (TextView) itemView.findViewById(R.id.TxvDescripcion);
        this.fecha = (TextView) itemView.findViewById(R.id.TxvFecha);
        this.estado = (TextView) itemView.findViewById(R.id.TxvEstado);
        this.foto = (ImageView) itemView.findViewById(R.id.imagen);

        this.linearLayout = (LinearLayout) itemView.findViewById(R.id.LytDisplay);
        this.cardView = (CardView) itemView.findViewById(R.id.cardViewInventario);

        this.recyclerViewOnItemListener = recyclerViewOnItemListener;
        //itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        itemView.setClickable(true);

    }

    @Override
    public void onClick(View v) {
        recyclerViewOnItemListener.onClick(v, getAdapterPosition());
    }


    @Override
    public boolean onLongClick(View v) {
        recyclerViewOnItemListener.onClick(v, getAdapterPosition());

        return false;
    }
}
