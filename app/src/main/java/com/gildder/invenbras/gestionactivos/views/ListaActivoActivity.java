package com.gildder.invenbras.gestionactivos.views;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.Utils.DialogoEliminacion;
import com.gildder.invenbras.gestionactivos.Utils.DialogoOpcionActivos;
import com.gildder.invenbras.gestionactivos.Utils.DialogoPersonalizado;
import com.gildder.invenbras.gestionactivos.adapters.ActivoAdapter;
import com.gildder.invenbras.gestionactivos.clases.Activo;
import com.gildder.invenbras.gestionactivos.interfaces.RecyclerViewOnItemListener;
import com.gildder.invenbras.gestionactivos.models.CActivo;
import com.gildder.invenbras.gestionactivos.models.DBHelper;

import java.util.ArrayList;

public class ListaActivoActivity extends Activity {
    private final String ID_TIPO = "ID_TIPO";
    private final String ID_INVENTARIO = "ID_INVENTARIO";
    private final String NOMBRE = "NOMBRE";
    private String inventario_id;
    private boolean isServidor = true;
    private CActivo cActivo;
    private DBHelper dbHelper;
    private ArrayList<Activo> activos = new ArrayList<Activo>();
    private Activo activo = new Activo();

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_activo);

        IniciarRecyclerView();

        //obtenemos los parametros enviado del activity anterior
        Intent intent = getIntent();

        inventario_id = intent.getStringExtra(ID_INVENTARIO);
        String nombre = intent.getStringExtra(NOMBRE);

        //cambiamos el titulo de la actividad
        setTitle(nombre);

        //lanzamos el hilo de conexion al servidor
        dbHelper = new DBHelper(getBaseContext());
        cActivo = CActivo.getInstance();
        cActivo.inicialize(dbHelper);

        ObtenerActivos();

    }

    private void ObtenerActivos() {
        activos = cActivo.GetAllInventario(inventario_id);

        IniciarRecyclerView();
    }

    public void onClick(View v){
        GuardarIdInventario();

        MostrarDialogoTipoActivo();

        //startActivity(new Intent(this, ActivoActivity.class));
    }

    private void MostrarDialogoTipoActivo() {
        FragmentManager fragmentManager = getFragmentManager();
        DialogoPersonalizado dialogoPersonalizado = new DialogoPersonalizado();
        dialogoPersonalizado.show(fragmentManager, "Alerta");
    }

    public void GuardarIdInventario()
    {
        SharedPreferences prefs =
                getSharedPreferences("LoginPreferences", getBaseContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("idInventario", inventario_id);
        editor.commit();

    }

    private void IniciarRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.RclListaActivos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ActivoAdapter(getBaseContext(), activos, new RecyclerViewOnItemListener() {

            @Override
            public void onClick(View v, int position) {

                //sacamos el activo seleccionado
                activo = activos.get(position);

                MostrarDialogoOpciones();
            }
        }));

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void MostrarDialogoOpciones() {
        FragmentManager fragmentManager = getFragmentManager();
        DialogoOpcionActivos dialogoOpcionActivos = new DialogoOpcionActivos();
        dialogoOpcionActivos.show(fragmentManager, "Opciones");
    }

    public void doEliminarActivo() {
        FragmentManager fragmentManager = getFragmentManager();
        DialogoEliminacion dialogoEliminacion = new DialogoEliminacion();
        dialogoEliminacion.show(fragmentManager, "Eliminacion");
    }

    public void doNegativeClick() {
    }

    public void doPositiveClick() {
        cActivo.Eliminar(activo.getId());
        ObtenerActivos();

        //mostrar Notificacion
    }

    public void doActualizarActivo() {
        Intent intent = new Intent(ListaActivoActivity.this, ActivoActivity.class);
        intent.putExtra("TIPO", "Actualizar");
        intent.putExtra("ACTIVO", String.valueOf(activo.getId()));

        startActivity(intent);
    }

    public void doObservarActivo() {
    }

    @Override
    protected void onResume() {
        super.onResume();

        ObtenerActivos();
    }


    public void doTipoActivoClick(String tipoActivo) {
        Intent intent = new Intent(ListaActivoActivity.this, ActivoActivity.class);
        intent.putExtra("TIPO", "Registrar");
        intent.putExtra("NOMBRE", tipoActivo);

        startActivity(intent);
    }


    /********* Clase interna *************************/
    public class TaskActivo extends AsyncTask<String,Integer,Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            try {
                activos = cActivo.GetAllInventario(params[0]);
            }catch (Exception e)
            {
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result)
            {
                Log.e("RESULT...", "Request con Exito!");
                IniciarRecyclerView();

            }
            else
            {
                Log.e("ERROR...", "Problema en la consulta");
            }
        }


    }//fin clase Task
}















