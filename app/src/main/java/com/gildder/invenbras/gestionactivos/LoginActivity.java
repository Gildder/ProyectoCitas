package com.gildder.invenbras.gestionactivos;

import android.app.Activity;
import android.content.Context;
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

import com.gildder.invenbras.gestionactivos.Utils.TaskValidacionUsuario;
import com.gildder.invenbras.gestionactivos.Utils.Util;

import org.apache.commons.codec.digest.DigestUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class LoginActivity extends Activity {
    EditText etxUsuario;
    EditText etxContrasenia;
    TextView txvMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        txvMensaje = (TextView) findViewById(R.id.TxvMensaje);
        txvMensaje.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v)
    {
        etxUsuario = (EditText) findViewById(R.id.EtxUsuario);
        etxContrasenia = (EditText) findViewById(R.id.EtxContrasenia);




        if (etxUsuario.getText().toString().equals("")|| etxContrasenia.getText().toString().equals("")) {
            txvMensaje.setText("Por favos, Llene los campos");
            return;
        }

        TaskValidacionUsuario tarea = new TaskValidacionUsuario();
        tarea.setUsuario(etxUsuario.getText().toString());
        tarea.setContrasenia(etxContrasenia.getText().toString());
        tarea.execute();


    }


    public void MensajeToats(String sms)
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_root,(ViewGroup)findViewById(R.id.toast_layout_root));

        TextView text =  ( TextView ) layout . findViewById ( R . id . text );
        text . setText ( sms );

        ImageView img = (ImageView) layout.findViewById(R.id.imgLogo);
        img.setImageResource(R.drawable.ic_stat_servidor);

        Toast toast =  new Toast(getApplicationContext());
        toast.setGravity ( Gravity.CENTER_VERTICAL ,  0 ,  0 );
        toast.setDuration ( Toast . LENGTH_LONG );
        toast.setView ( layout );
        toast.show ();
    }

    /**
     * Clase Tarea
     */
    public class TaskValidacionUsuario extends AsyncTask<String,Integer,Boolean> {
        private String usuario;
        private String contrasenia;

        boolean isServidor = true;

        final static String METHOD_NAME = "ValidarUsuario";
        final static String SOAP_ACTION = "http://inventario.pre/ValidarUsuario";


        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;


            //modelo del request
            SoapObject request = new SoapObject(Util.NAMESPACE, METHOD_NAME);
            request.addProperty("nombre",usuario);      //Paso de parametro
            request.addProperty("contrasenia",contrasenia);

            //modelo envolepe
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            //modo de transporte
            HttpTransportSE transportSE = new HttpTransportSE(Util.URL);

            try {
                transportSE.call(SOAP_ACTION, envelope);

                SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();

                result = Boolean.parseBoolean(resSoap.toString());

            }catch (IOException ex){
                isServidor = false;
                Log.e("ERROR...", ex.getMessage());
            } catch (XmlPullParserException e) {
                isServidor = false;
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result)
            {
                //usuario correcto guardamos los datos
                SharedPreferences settings = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("usuario", etxUsuario.getText().toString());
                editor.putString("contrasenia", etxContrasenia.getText().toString());

                //confirmamos el almacenamiento
                editor.commit();

                //paso al otro activity
                startActivity(new Intent(LoginActivity.this, MyInventarioActivity.class));

                finish();
            }
            else
            {
                if(isServidor==false)
                    //Toast.makeText(getApplicationContext(),"No se pudo conectar al servidor",Toast.LENGTH_SHORT).show();
                    MensajeToats("No se pudo conectar al servidor");
                else
                    txvMensaje.setText("Usuario o contraseña incorrectos");
            }
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getContrasenia() {
            return contrasenia;
        }

        public void setContrasenia(String contrasenia) {
            this.contrasenia = contrasenia;
        }
    }//fin clase Task
}
