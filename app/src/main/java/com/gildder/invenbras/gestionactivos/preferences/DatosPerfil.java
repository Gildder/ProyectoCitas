package com.gildder.invenbras.gestionactivos.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by gildder on 04/11/2015.
 */
public class DatosPerfil {
    private static String preferences_name = "PerfilPreferences";
    private static String id = "Id";
    private static String persona = "Persona";
    private static String usuario = "Usuario";
    private static String empleado_id = "Empleado_id";
    private static String persona_id = "Persona_id";
    private static String almacen_id = "Persona_id";
    private static String contrasenia = "Contrasenia";

    public static boolean PrimerIngreso(Context context){
        SharedPreferences perfilPreferences = context.getSharedPreferences(preferences_name, context.MODE_PRIVATE);
        return !perfilPreferences.contains(id);
    }

    public static SharedPreferences getPerfilPreferences(Context context)
    {
        return context.getSharedPreferences(preferences_name, context.MODE_PRIVATE);
    }

    public static Editor getPerfilPreferencesEditor(Context context)
    {
        return context.getSharedPreferences(preferences_name, context.MODE_PRIVATE).edit();
    }

    public static String getId(Context context)
    {
        return getPerfilPreferences(context).getString(id,"");
    }



}
