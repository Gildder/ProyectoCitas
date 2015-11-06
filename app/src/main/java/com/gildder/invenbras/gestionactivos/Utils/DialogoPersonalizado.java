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

import com.gildder.invenbras.gestionactivos.clases.Activo;
import com.gildder.invenbras.gestionactivos.clases.TipoActivo;
import com.gildder.invenbras.gestionactivos.models.CActivo;
import com.gildder.invenbras.gestionactivos.models.CTipoActivo;
import com.gildder.invenbras.gestionactivos.models.DBHelper;
import com.gildder.invenbras.gestionactivos.views.ActivoActivity;

import java.util.ArrayList;

/**
 * Created by gildder on 17/10/2015.
 */
public class DialogoPersonalizado extends DialogFragment {
    private CTipoActivo cTipoActivo;
    private DBHelper dbHelper;
    private ArrayList<TipoActivo> tipoActivos = new ArrayList<TipoActivo>();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //lanzamos el hilo de conexion al servidor
        dbHelper = new DBHelper(getActivity());
        cTipoActivo = CTipoActivo.getInstance();
        cTipoActivo.inicialize(dbHelper);



        builder.setTitle("Tipo Activo")
                .setItems(Arreglos.TIPO_ACTIVO,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogo", "Opcion elegida " + Arreglos.TIPO_ACTIVO[id]);

                        Intent intent = new Intent(getActivity(), ActivoActivity.class);
                        intent.putExtra("ID",id + 1);
                        intent.putExtra("NOMBRE",Arreglos.TIPO_ACTIVO[id].toString());
                        Toast.makeText(getActivity(), Arreglos.TIPO_ACTIVO[id], Toast.LENGTH_SHORT).show();

                        startActivity(intent);

                    }
                });
        return builder.create();
    }
}
