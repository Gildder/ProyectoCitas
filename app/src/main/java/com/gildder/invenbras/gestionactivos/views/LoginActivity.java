package com.gildder.invenbras.gestionactivos.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.Utils.Util;
import com.gildder.invenbras.gestionactivos.clases.Inventario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends Activity {
    private EditText etxUsuario;
    private EditText etxContrasenia;
    private TextView txvMensaje;
    private ProgressDialog pgdProceso;

    private boolean isServidor = true;
    private boolean res = false;
    boolean result = false;

    private TaskValidacionUsuario tarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    public  void init(){
        etxUsuario = (EditText) findViewById(R.id.EtxUsuario);
        etxContrasenia = (EditText) findViewById(R.id.EtxContrasenia);
        txvMensaje = (TextView) findViewById(R.id.TxvMensaje);
        txvMensaje.setText("");
    }


    /**
     * Este metodo maneja el evento Click
     *
     * @param v Evento de click
     */
    public void onClick(View v) {

        if (etxUsuario.getText().toString().equals("") || etxContrasenia.getText().toString().equals("")) {
            txvMensaje.setText("Por favos, Llene los campos");
            return;
        }

        //Proceso de dialogo
        pgdProceso = new ProgressDialog(LoginActivity.this);
        pgdProceso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pgdProceso = ProgressDialog.show(this,"Espere por favor","Procesando...");
        pgdProceso.setCancelable(false);


        tarea = new TaskValidacionUsuario();
        tarea.execute(etxUsuario.getText().toString(),etxContrasenia.getText().toString());

        //TaskValidacion tareaRest = new TaskValidacion();
        //tareaRest.execute(etxUsuario.getText().toString(),etxContrasenia.getText().toString());

    }


    //********************************** CLASE INTERNA ***********************************************************
    /**
     * Clase Tarea
     */
    public class TaskValidacionUsuario extends AsyncTask<String, Integer, Boolean> {

        private final String SOAP_ACTION = Util.NAMESPACE + Util.METHOD_VAL_USUARIO;
        private SoapPrimitive resSoap;
        //Declaracion de variables para serealziar y deserealizar
        //objetos y cadenas JSON
        Gson gson ;

        @Override
        protected void onPreExecute() {

            pgdProceso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    TaskValidacionUsuario.this.cancel(true);
                }
            });

            pgdProceso.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {


            //modelo del request
            SoapObject request = new SoapObject(Util.NAMESPACE, Util.METHOD_VAL_USUARIO);
            request.addProperty("usuario",   params[0] );      //Paso de parametro
            request.addProperty("contrasenia",  params[1] );

            //modelo envolepe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            //modo de transporte con URL y Tiempo espera
            HttpTransportSE transport = new HttpTransportSE(Util.URL,60000);

            try {
                transport.call(SOAP_ACTION, envelope);

                resSoap = (SoapPrimitive) envelope.getResponse();

                if (resSoap.toString().equals("vacio")){
                    result = false;
                }else {

                    result = true;
                }

            } catch (IOException ex) {
                isServidor = false;
                Log.e("ERROR...", ex.getMessage());
                ex.printStackTrace();
            } catch (XmlPullParserException e) {
                isServidor = false;
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            JSONArray jArray = new JSONArray();

            if (result) {

                String responseJSON = resSoap.toString();
                try {
                    jArray = new JSONArray(responseJSON);



                    //usuario correcto guardamos los datos
                    SharedPreferences usuarioPrefs = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = usuarioPrefs.edit();
                    editor.putString("id", jArray.getJSONObject(0).getString("Id"));
                    editor.putString("persona", jArray.getJSONObject(0).getString("Persona"));
                    editor.putString("usuario", jArray.getJSONObject(0).getString("Usuario"));
                    editor.putString("empleado_id", jArray.getJSONObject(0).getString("Empleado_id"));
                    editor.putString("persona_id", jArray.getJSONObject(0).getString("Persona_id"));
                    editor.putString("almacen_id", jArray.getJSONObject(0).getString("Almacen_id"));
                    editor.putString("contrasenia", etxContrasenia.getText().toString());

                    //confirmamos el almacenamiento
                    editor.commit();


                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.e("ERROR:", "No se pudo guardar en Preferencias");
                }

                Intent intent = new Intent(LoginActivity.this, FragmentTabsActivity.class);
                try {
                    intent.putExtra("ID_ALMACEN",jArray.getJSONObject(0).getString("Almacen_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //paso al otro activity
                startActivity(intent);

                finish();
            } else {
                if (!isServidor)
                    //Toast.makeText(getApplicationContext(),"No se pudo conectar al servidor",Toast.LENGTH_SHORT).show();
                    MensajeToats("No se pudo conectar al servidor");
                else
                    txvMensaje.setText("Usuario o contraseña incorrectos");
            }

            pgdProceso.dismiss();
        }



    }//fin clase Task





    /**
     * Este metodo permite realizar Toast configurable
     *
     * @param sms mensaje de entrada
     */
    private void MensajeToats(String sms) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_root, (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(sms);

        ImageView img = (ImageView) layout.findViewById(R.id.imgLogo);
        img.setImageResource(R.drawable.ic_stat_servidor);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /**
     * Metodo que recibe una cadena JSON y la convierte en una lista
     * de objetos AndroidOS para despues cargarlos en la lista
     * @param strJson (String) Cadena JSON
     */


}
