package com.gildder.invenbras.gestionactivos;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.gildder.invenbras.gestionactivos.Utils.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class LoginActivity extends Activity {
    private EditText etxUsuario;
    private EditText etxContrasenia;
    private TextView txvMensaje;
    private ProgressDialog pgdProceso;

    private boolean isServidor = true;
    private boolean res = false;

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
        boolean result = false;
        private final String SOAP_ACTION = Util.NAMESPACE + Util.METHOD_VAL_USUARIO;


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

                SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

                result = Boolean.parseBoolean(resSoap.toString());

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
            pgdProceso.dismiss();

            if (result) {
                //usuario correcto guardamos los datos
                SharedPreferences settings = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("usuario", etxUsuario.getText().toString());
                editor.putString("contrasenia", etxContrasenia.getText().toString());

                //confirmamos el almacenamiento
                editor.commit();


                //paso al otro activity
                startActivity(new Intent(LoginActivity.this, FragmentTabsActivity.class));

                finish();
            } else {
                if (!isServidor)
                    //Toast.makeText(getApplicationContext(),"No se pudo conectar al servidor",Toast.LENGTH_SHORT).show();
                    MensajeToats("No se pudo conectar al servidor");
                else
                    txvMensaje.setText("Usuario o contraseña incorrectos");
            }
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


}
