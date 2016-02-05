package com.gildder.invenbras.gestionactivos.Utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.gildder.invenbras.gestionactivos.clases.TipoActivo;
import com.gildder.invenbras.gestionactivos.models.CTipoActivo;
import com.gildder.invenbras.gestionactivos.models.DBHelper;
import com.gildder.invenbras.gestionactivos.views.ActivoActivity;
import com.gildder.invenbras.gestionactivos.views.ListaActivoActivity;

import java.util.ArrayList;

/**
 * Created by gildder on 17/10/2015.
 */
public class DialogoOpcionActivos extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();



        builder.setTitle("Opciones")
                .setItems(Arreglos.OPCION_ACTIVO, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogo", "Opcion elegida " + Arreglos.OPCION_ACTIVO[id].toString());

                        switch (id){
                            case 0:
                                ((ListaActivoActivity)getActivity()).doEliminarActivo();
                                break;
                            case 1:
                                ((ListaActivoActivity)getActivity()).doActualizarActivo();
                                break;
                            case 2:
                                ((ListaActivoActivity)getActivity()).doObservarActivo();
                                break;

                        }


                    }
                });
        return builder.create();
    }


}





















