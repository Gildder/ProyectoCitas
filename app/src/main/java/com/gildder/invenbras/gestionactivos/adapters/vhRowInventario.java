package com.gildder.invenbras.gestionactivos.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gildder.invenbras.gestionactivos.R;

/**
 * Esta clase relacion los view del Xml row_inventario
 *
 * Created by gildder on 15/10/2015.
 */
public class vhRowInventario extends RecyclerView.ViewHolder {
    protected TextView nombre;
    protected TextView descripcion;
    protected TextView prioridad;
    protected TextView nro;
    protected RelativeLayout relativeLayout;
    protected CardView cardView;

    public vhRowInventario(View itemView) {
        super(itemView);

        this.nombre = (TextView) itemView.findViewById(R.id.TxvModelo);
        this.descripcion = (TextView) itemView.findViewById(R.id.TxvMarca);
        this.prioridad = (TextView) itemView.findViewById(R.id.TxvPrioridadInventario);
        this.nro = (TextView) itemView.findViewById(R.id.TxvCountInventario);

        this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.RltContentInventario);
        this.cardView = (CardView) itemView.findViewById(R.id.cardViewInventario);

        itemView.setClickable(true);
    }

}
