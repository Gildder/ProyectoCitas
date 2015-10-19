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


    private TaskValidacionUsuario tarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
        pgdProceso.setCancelable(true);



        tarea = new TaskValidacionUsuario();
        tarea.execute();

    }





    //********************************** CLASE INTERNA ***********************************************************
    /**
     * Clase Tarea
     */
    public class TaskValidacionUsuario extends AsyncTask<Void, Integer, Boolean> {
        private String usuario;
        private String contrasenia;

        private boolean isServidor = true;

        @Override
        protected void onPreExecute() {


            tarea.setUsuario(etxUsuario.getText().toString());
            tarea.setContrasenia(etxContrasenia.getText().toString());

            pgdProceso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    TaskValidacionUsuario.this.cancel(true);
                }
            });

            pgdProceso.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = true;

            String NAMESPACE = "http://ibrasact.com.bo/";
            String URL = "http://192.168.56.1/InventarioWS/WebService.asmx";
            String METHOD_NAME = "ValidarUsuario";
            String SOAP_ACTION = "http://ibrasact.com.bo/ValidarUsuario";

            //modelo del request
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("usuario", usuario);      //Paso de parametro
            request.addProperty("contrasenia", contrasenia);

            //modelo envolepe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            //modo de transporte
            HttpTransportSE transport = new HttpTransportSE(URL);

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




        /* Metodos Getter y Setter */
        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public void setContrasenia(String contrasenia) {
            this.contrasenia = contrasenia;
        }

    }//fin clase Task

    //***************************** Fin Clase Interna *********************************


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

    /*

    Thread nt = new Thread() {
            boolean res;
            boolean isServidor = true;

            @Override
            public void run() {
                String NAMESPACE = "http://ibrasact.com.bo/";
                String URL = "http://192.168.56.1/InventarioWS/WebService.asmx";
                String METHOD_NAME = "ValidarUsuario";
                String SOAP_ACTION = "http://ibrasact.com.bo/ValidarUsuario";

                //modelo del request
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("usuario", etxUsuario.getText().toString());      //Paso de parametro
                request.addProperty("contrasenia", etxContrasenia.getText().toString());

                //modelo envolepe
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);

                //modo de transporte
                HttpTransportSE transport = new HttpTransportSE(URL);

                try {
                    transport.call(SOAP_ACTION, envelope);

                    SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

                    res = Boolean.parseBoolean(resSoap.toString());

                } catch (IOException ex) {
                    isServidor = false;
                    Log.e("ERROR...", ex.getMessage());
                    ex.printStackTrace();
                } catch (XmlPullParserException e) {
                    isServidor = false;
                    e.printStackTrace();
                }
                //return res;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (res) {
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
                            if (isServidor == false)
                                //Toast.makeText(getApplicationContext(),"No se pudo conectar al servidor",Toast.LENGTH_SHORT).show();
                                MensajeToats("No se pudo conectar al servidor");
                            else
                                txvMensaje.setText("Usuario o contraseña incorrectos");
                        }
                    }
                });
            }
        };
     */
}
