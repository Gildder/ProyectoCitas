package com.gildder.invenbras.gestionactivos.Utils;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.codec.digest.DigestUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by gildder on 08/10/2015.
 */
public class TaskValidacionUsuario extends AsyncTask<String,Integer,Boolean> {
    private String usuario;
    private String contrasenia;
    public boolean result = true;

    final static String METHOD_NAME = "ValidarUsuario";
    final static String SOAP_ACTION = "http://inventario.pre/ValidarUsuario";


    @Override
    protected Boolean doInBackground(String... params) {


        //modelo del request
        SoapObject request = new SoapObject(Util.NAMESPACE,Util.URL);
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


        }catch (Exception ex){
            Log.e("ERROR...", ex.getMessage());
        }
        return result;
    }

    //Getter y Setter
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
        this.contrasenia = DigestUtils.md5Hex(contrasenia);
    }
}
