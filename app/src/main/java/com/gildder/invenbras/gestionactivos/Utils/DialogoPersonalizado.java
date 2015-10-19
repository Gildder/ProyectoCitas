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

import com.gildder.invenbras.gestionactivos.ActivoActivity;
import com.gildder.invenbras.gestionactivos.R;

/**
 * Created by gildder on 17/10/2015.
 */
public class DialogoPersonalizado extends DialogFragment {
    final String[] tipos = {"Computacion", "Telecomunicaciones","Red","Equipos","Materiales","Muebles"};


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        builder.setTitle("Tipo Activo")
                .setItems(tipos,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogo", "Opcion elegida " + tipos[id]);

                        Intent intent = new Intent(getActivity(), ActivoActivity.class);
                        intent.putExtra("ID",id + 1);
                        intent.putExtra("NOMBRE",tipos[id].toString());
                        Toast.makeText(getActivity(), tipos[id], Toast.LENGTH_SHORT).show();

                        startActivity(intent);

                    }
                });
        return builder.create();
    }
}
