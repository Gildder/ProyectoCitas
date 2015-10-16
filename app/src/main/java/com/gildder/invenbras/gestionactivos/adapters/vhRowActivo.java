package com.gildder.invenbras.gestionactivos.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gildder.invenbras.gestionactivos.R;

/**
 * Esta clase relacion los view del Xml row_inventario
 *
 * Created by gildder on 15/10/2015.
 */
public class vhRowActivo extends RecyclerView.ViewHolder {
    protected TextView modelo;
    protected TextView marca;
    protected TextView serie;
    protected TextView estado;
    protected TextView tipo;
    protected ImageView foto;
    protected LinearLayout linearLayout;
    protected CardView cardView;

    public vhRowActivo(View itemView) {
        super(itemView);

        this.modelo = (TextView) itemView.findViewById(R.id.TxvModeloA);
        this.marca = (TextView) itemView.findViewById(R.id.TxvMarcaA);
        this.serie = (TextView) itemView.findViewById(R.id.TxvSerieA);
        this.estado = (TextView) itemView.findViewById(R.id.TxvEstado);
        this.tipo = (TextView) itemView.findViewById(R.id.TxvTipo);

        this.linearLayout = (LinearLayout) itemView.findViewById(R.id.LytDisplay);
        this.cardView = (CardView) itemView.findViewById(R.id.cardViewInventario);

        itemView.setClickable(true);
    }

}
