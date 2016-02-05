package com.gildder.invenbras.gestionactivos.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.clases.Activo;
import com.gildder.invenbras.gestionactivos.clases.Notificacion;
import com.gildder.invenbras.gestionactivos.interfaces.RecyclerViewOnItemListener;

import java.util.ArrayList;

/**
 * Created by gildder on 15/10/2015.
 */
public class NotificacionAdapter extends RecyclerView.Adapter<vhRowNotificacion>  {
    private ArrayList<Notificacion> notificaciones;
    private int itemLayout;
    private Context context;
    private Bitmap bmp;
    private Notificacion notificacion;
    //interface definida en interfaces
    private RecyclerViewOnItemListener recyclerViewOnItemListener;

    public NotificacionAdapter(Context context, ArrayList<Notificacion> notificaciones, RecyclerViewOnItemListener recyclerViewOnItemListener) {
        this.notificaciones = notificaciones;
        this.recyclerViewOnItemListener = recyclerViewOnItemListener;
        this.context = context;
    }

    @Override
    public vhRowNotificacion onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_activo, viewGroup, false);
        vhRowNotificacion holder = new vhRowNotificacion(v,recyclerViewOnItemListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(vhRowNotificacion viewHolder, int position) {
        notificacion = new Notificacion(notificaciones.get(position));

        viewHolder.itemView.setSelected(itemLayout == position);
        viewHolder.getLayoutPosition();

        viewHolder.titulo.setText(notificacion.getTitulo());
        viewHolder.descripcion.setText(notificacion.getDescripcion());
        viewHolder.fecha.setText(notificacion.getFecha());
        switch (notificacion.getTipo()){
            case "S":
                viewHolder.foto.setBackgroundResource(R.drawable.ic_action_sync);
                viewHolder.estado.setText("Revisar");

                break;
            case "N":
                viewHolder.foto.setBackgroundResource(R.drawable.ic_alert_dialog_inf);

                break;
            case "F":
                viewHolder.foto.setBackgroundResource(R.drawable.ic_stat_servidor);

                break;
        }



    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }

    /**
     * Este metodo limpias la lista de activo
     */
    public void clearAdapter(){
        notificaciones.clear();
        notifyDataSetChanged();
    }


}
