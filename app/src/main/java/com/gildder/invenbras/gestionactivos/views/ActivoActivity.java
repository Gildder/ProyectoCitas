package com.gildder.invenbras.gestionactivos.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.Utils.Arreglos;
import com.gildder.invenbras.gestionactivos.clases.Activo;
import com.gildder.invenbras.gestionactivos.clases.TipoActivo;
import com.gildder.invenbras.gestionactivos.clases.Ubicacion;
import com.gildder.invenbras.gestionactivos.models.CActivo;
import com.gildder.invenbras.gestionactivos.models.CTipoActivo;
import com.gildder.invenbras.gestionactivos.models.CUbicacion;
import com.gildder.invenbras.gestionactivos.models.DBHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActivoActivity extends Activity  implements OnClickListener, TextWatcher, Validator.ValidationListener{
    public static final int PHOTO_CODE = 0;
    public static final int SELECT_PICTURE = 1;
    private static final String APP_DIRECTORY = "myPictureApp/";
    private static final String MEDIA_DIRECTORY = APP_DIRECTORY + "media" ;
    private static String TEMPORAL_PICTURE_NAME = "SinFoto.jpg";
    private EditText edtCaracteristicas;

    @NotEmpty( message = "El modelo es obligatorio")
    @Max(value = 20)
    private EditText edtModelo;

    @NotEmpty(message = "La marca es obligatorio")
    @Max(value = 20)
    private EditText edtMarca;

    @NotEmpty( message = "La serie es obligatorio")
    @Min(value = 6)
    @Max(value = 20)
    private EditText edtSerie;

    private AutoCompleteTextView edtColor;
    private EditText edtAlto;
    private EditText edtAncho;
    private EditText edtProfundidad;
    private EditText edtContenido;
    private EditText edtPeso;
    private EditText edtNumero;
    private EditText edtFechaMantenimiento;
    private EditText edtUnidad;
    private EditText edtCantidad;
    private EditText edtMaterial;
    private EditText edtCodTic;
    private EditText edtCodPat;
    private EditText edtCodAct;
    private EditText edtCodGer;
    private EditText edtObservacion;
    private EditText edtOtroCod;
    private ImageView imvFoto;
    private ImageButton btnCodTic;
    private ImageButton btnCodPat;
    private ImageButton btnCodAct;
    private ImageButton btnCodGer;
    private ImageButton btnTomarFoto;
    private ImageButton btnGuardar;
    private Intent camaraIntent;
    private View vBotonClick;
    private Spinner spnTipos;
    private Spinner spnEstado;
    private Spinner spnArea;
    private EditText edtSector;
    private EditText edtLugar;
    private TextView txvDescripcion;

    private String ID_INVENTARIO = "ID";
    private String TIPO = "TIPO";
    private String NOMBRE = "NOMBRE";
    private String ACTIVO = "ACTIVO";
    private String id_inventario;
    private int id_ubicacion;
    private int id_tipo;

    private String[] datos;
    private Bitmap bmp;
    private String imagenPath= "ninguna";

    //instanncias de modelos a la base de datos
    private CUbicacion cUbicacion;
    private CTipoActivo cTipoActivo;
    private CActivo cActivo;
    private DBHelper dbHelper;

    //Arreglos para los Spinner
    private int[] ids;
    private String[] area=new String[]{};
    private String[] tipo=new String[]{};

    private ArrayList<TipoActivo> tipoActivos;
    private ArrayList<Ubicacion> ubicacions;

    //nuevo activo
    private Activo activo;
    private TipoActivo tipoActivo;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activo);

        dbHelper = new DBHelper(getBaseContext());
        cActivo = CActivo.getInstance();
        cActivo.inicialize(dbHelper);

        cTipoActivo = CTipoActivo.getInstance();
        cTipoActivo.inicialize(dbHelper);

        //instaciamos el nuevo activo
        activo = new Activo();

        //obteniendo parametros enviados del actvity previo
        Intent intent = getIntent();
        TIPO = intent.getStringExtra(TIPO);
        if(TIPO.equals("Registrar")) {
            NOMBRE = intent.getStringExtra(NOMBRE);
        }else{
            ACTIVO = intent.getStringExtra(ACTIVO);

            activo = cActivo.Get(Integer.valueOf(ACTIVO));

            int idTipoActivo = activo.getIdTipo();
            tipoActivo = cTipoActivo.Get(String.valueOf(idTipoActivo));

            NOMBRE = tipoActivo.getTipo();

            cargarActivoActualizar();


        }

        //Colocamos el titutlo del activity
        setTitle(NOMBRE);



        //inicializamos conponentes basicos
        inicializeComponent();


        //cargamos los datos basicos
        cargarUbicacion();
        cargarTipo();
        getIdEmpleado();

        validator = new Validator(this);
        validator.setValidationListener(this);

        edtColor.addTextChangedListener(this);
        edtColor.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,Arreglos.COLORS_ACTIVO));

        spnTipos.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo));
        spnTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                cargarDatosTipo(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio
            }
        });


        //Spinner estado
        spnEstado.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Arreglos.ESTADO_ACTIVO));
        spnEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                activo.setEstado(spnEstado.getSelectedItem().toString().substring(0,1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio
            }
        });

        //Spinner area
        spnArea.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, area));
        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                cargarDatosUbicacion(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio
            }
        });

    }

    private void cargarActivoActualizar() {
        edtMarca.setText(activo.getMarca().toString());
        edtModelo.setText(activo.getModelo().toString());
        edtSerie.setText(activo.getSerie().toString());

        if(!activo.getImagen().equals("ninguno")) {
            bmp = BitmapFactory.decodeFile(activo.getImagen());
            imvFoto.setImageBitmap(bmp);
        }

        if(!activo.getDescripcion().equals("ninguno"))
            edtCaracteristicas.setText(activo.getDescripcion());

        if(!activo.getColor().equals("ninguno"))
            edtColor.setText(activo.getColor());

        if(activo.getAlto() != 0)
            edtAlto.setText(String.valueOf(activo.getAlto()));

        if(activo.getAncho() != 0)
            edtAncho.setText(String.valueOf(activo.getAncho()));

        if(activo.getProfundidad() != 0)
            edtProfundidad.setText(String.valueOf(activo.getProfundidad()));

        if(!activo.getNro().equals("ninguno"))
            edtNumero.setText(activo.getNro());

        if(!activo.getContenido().equals("ninguno"))
            edtContenido.setText(activo.getContenido());

        if(activo.getPeso() != 0)
            edtPeso.setText(String.valueOf(activo.getPeso()));

        if(!activo.getFechaMantenimiento().equals("ninguno"))
            edtFechaMantenimiento.setText(activo.getFechaMantenimiento());

        if(!activo.getObservacion().equals("ninguno"))
            edtObservacion.setText(activo.getObservacion());

        if(!activo.getCodigoTIC().equals("ninguno"))
            edtCodTic.setText(activo.getCodigoTIC());

        if(!activo.getCodigoPAT().equals("ninguno"))
            edtCodPat.setText(activo.getCodigoPAT());

        if(!activo.getCodigoAF().equals("ninguno"))
            edtCodAct.setText(activo.getCodigoAF());

        if(!activo.getCodigoGER().equals("ninguno"))
            edtCodGer.setText(activo.getCodigoGER());

        if(!activo.getOtroCodigo().equals("ninguno"))
            edtOtroCod.setText(activo.getOtroCodigo());

        switch (activo.getEstado()){
            case "S":
                spnEstado.setSelection(0);
                break;
            case "N":
                spnEstado.setSelection(1);
                break;
            case "F":
                spnEstado.setSelection(2);
                break;
        }

        spnArea.setSelection(activo.getIdUbicacion() - 1);

        for (int i = 0; i<tipoActivos.size(); i++){
            if(tipoActivo.getNombre()==tipoActivos.get(i).getNombre()){
                spnTipos.setSelection(i);
            }
        }
    }


    public void inicializeComponent(){
        edtCaracteristicas = (EditText) findViewById(R.id.EdtCaracteristica);
        edtModelo = (EditText) findViewById(R.id.EdtModelo);
        edtMarca = (EditText) findViewById(R.id.EdtMarca);
        edtSerie = (EditText) findViewById(R.id.EdtSerie);
        edtColor = (AutoCompleteTextView) findViewById(R.id.EdtColor);
        edtAlto = (EditText) findViewById(R.id.EdtAlto);
        edtAncho = (EditText) findViewById(R.id.EdtAncho);
        edtProfundidad = (EditText) findViewById(R.id.EdtProfundidad);
        edtContenido = (EditText) findViewById(R.id.EdtContenido);
        edtPeso = (EditText) findViewById(R.id.EdtPeso);
        edtNumero = (EditText) findViewById(R.id.EdtNro);
        edtFechaMantenimiento = (EditText) findViewById(R.id.EdtFecha);
        edtUnidad = (EditText) findViewById(R.id.EdtUnidad);
        edtCantidad = (EditText) findViewById(R.id.EdtCantidad);
        edtMaterial = (EditText) findViewById(R.id.EdtMaterial);
        edtCodTic = (EditText) findViewById(R.id.EdtCodTic);
        edtCodPat = (EditText) findViewById(R.id.EdtCodPat);
        edtCodAct = (EditText) findViewById(R.id.EdtCodAct);
        edtCodGer = (EditText) findViewById(R.id.EdtCodGer);
        edtObservacion = (EditText) findViewById(R.id.EdtObservacion);
        edtOtroCod = (EditText) findViewById(R.id.EdtOtroCod);


        txvDescripcion = (TextView) findViewById(R.id.TxvInvDescripcion);

        imvFoto = (ImageView) findViewById(R.id.ImvFoto);
        btnTomarFoto = (ImageButton) findViewById(R.id.ImbTomarFoto);
        btnGuardar = (ImageButton) findViewById(R.id.ImbGuardarActivo);

        btnCodTic = (ImageButton) findViewById(R.id.BtnCodTic);
        btnCodPat = (ImageButton) findViewById(R.id.BtnCodPat);
        btnCodAct = (ImageButton) findViewById(R.id.BtnCodAct);
        btnCodGer = (ImageButton) findViewById(R.id.BtnCodGer);

        btnCodTic.setOnClickListener(this);
        btnCodPat.setOnClickListener(this);
        btnCodAct.setOnClickListener(this);
        btnCodGer.setOnClickListener(this);
        btnTomarFoto.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);

        //Spinner estado
        spnEstado = (Spinner) findViewById(R.id.SpnEstado);
        spnArea = (Spinner) findViewById(R.id.SpnArea);
        edtSector = (EditText) findViewById(R.id.EdtSector);
        edtLugar = (EditText) findViewById(R.id.EdtLugar);
        spnTipos = (Spinner) findViewById(R.id.spinner);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ImbTomarFoto:
                onClickFoto(v);
                break;
            case R.id.ImbGuardarActivo:
                GuardarActivo(v);
                break;
            default:
                vBotonClick = v;
                IntentIntegrator.initiateScan(this);
        }
    }



    private void GuardarActivo(View v) {
        if(ValidarActivo()){
            long res = -1;
            if(TIPO.equals("Registrar")) {
                res = cActivo.Insertar(activo);
            }else{
                res = cActivo.Actualizar(activo);
            }

            if(res != -1){
                Limpiar();
            }else{
                Toast.makeText(getBaseContext(),"El proceso no realizo",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getBaseContext(),"Datos incorrectos",Toast.LENGTH_SHORT).show();
        }
    }

    private void Limpiar() {
        edtCaracteristicas.setText("");
        edtModelo.setText("");
        edtMarca.setText("");
        edtSerie.setText("");
        edtColor.setText("");
        edtAlto.setText("");
        edtAncho.setText("");
        edtProfundidad.setText("");
        edtContenido.setText("");
        edtPeso.setText("");
        edtNumero.setText("");
        edtFechaMantenimiento.setText("");
        edtUnidad.setText("");
        edtCantidad.setText("");
        edtMaterial.setText("");
        edtCodTic.setText("");
        edtCodPat.setText("");
        edtCodAct.setText("");
        edtCodGer.setText("");
        edtObservacion.setText("");
        edtOtroCod.setText("");
        imvFoto.setImageResource(R.drawable.ic_menu_media);
    }

    private boolean ValidarActivo() {
        boolean b = true;
        if(activo.getIdEmpleado() == 0) {
            Log.e("ERROR: ","idEmpleado es incorrecto...");
            finish();
        }

        if(activo.getIdInventario() == 0) {
            Log.e("ERROR: ","idInventario es incorrecto...");
            finish();
        }

        if (activo.getIdUbicacion() == 0){
            activo.setIdUbicacion(id_ubicacion);
        }

        if (activo.getIdTipo() == 0){
            activo.setIdTipo(id_tipo);
        }

        if (!edtCaracteristicas.getText().toString().equals("")){
            activo.setDescripcion(edtCaracteristicas.getText().toString());
        }else
            b=false;

        if (!edtModelo.getText().toString().equals("")){
            activo.setModelo(edtModelo.getText().toString());
        }else
            b=false;

        if (!edtMarca.getText().toString().equals("")){
            activo.setMarca(edtMarca.getText().toString());
        }else
            b=false;

        if (!edtSerie.getText().toString().equals("")){
            activo.setSerie(edtSerie.getText().toString());
        }else
            b = false;

        if (!edtColor.getText().toString().equals("")){
            activo.setColor(edtColor.getText().toString());
        }

        if (!edtAlto.getText().toString().equals("")){
            activo.setAlto(Float.parseFloat(edtAlto.getText().toString()));
        }

        if (!edtAncho.getText().toString().equals("")) {
            activo.setAncho(Float.parseFloat(edtAncho.getText().toString()));
        }

        if (!edtProfundidad.getText().toString().isEmpty()){
            activo.setProfundidad(Float.parseFloat(edtProfundidad.getText().toString()));
        }

        if (!edtContenido.getText().toString().equals("")){
            activo.setContenido(edtContenido.getText().toString());
        }

        if (!edtPeso.getText().toString().equals("")){
            activo.setPeso(Float.parseFloat(edtPeso.getText().toString()));
        }

        if (!edtNumero.getText().toString().equals("")){
            activo.setNro(edtNumero.getText().toString());
        }

        if (!edtFechaMantenimiento.getText().toString().equals("")){
            activo.setFechaMantenimiento(edtFechaMantenimiento.getText().toString());
        }

        if (!edtUnidad.getText().toString().equals("")){
            activo.setUnidad(edtUnidad.getText().toString());
        }

        if (!edtCantidad.getText().toString().equals("")){
            activo.setCantidad(Integer.parseInt(edtCantidad.getText().toString()));
        }

        if (!edtMaterial.getText().toString().equals("")){
            activo.setMaterial(edtMaterial.getText().toString());
        }

        if (!edtCodTic.getText().toString().equals("")){
            activo.setCodigoTIC(edtCodTic.getText().toString());
        }

        if (!edtCodPat.getText().toString().equals("")){
            activo.setCodigoPAT(edtCodPat.getText().toString());
        }

        if (!edtCodAct.getText().toString().equals("")){
            activo.setCodigoAF(edtCodAct.getText().toString());
        }

        if (!edtCodGer.getText().toString().equals("")){
            activo.setCodigoGER(edtCodGer.getText().toString());
        }

        if (!edtOtroCod.getText().toString().equals("")){
            activo.setOtroCodigo(edtOtroCod.getText().toString());
        }

        if (!edtObservacion.getText().toString().equals("")){
            activo.setObservacion(edtObservacion.getText().toString());
        }



        return b;
    }


    public void onClickFoto(View v){
        imvFoto = (ImageView) findViewById(R.id.ImvFoto);

        //cuadrod de dialog de opciones de imagenes
        final CharSequence[] options = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(ActivoActivity.this);
        builder.setTitle("Elige una opcion");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int seleccion) {
                if (options[seleccion] == "Tomar foto") {
                    openCamera();
                } else if (options[seleccion] == "Elegir de galeria") {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona imagen"), SELECT_PICTURE);
                } else if (options[seleccion] == "Cancelar") {
                    dialog.dismiss();
                }
            }
        });

        builder.show();

      //  camaraIntent = new  Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      //  startActivityForResult(camaraIntent, CAMARA_REQUEST);
    }

    private void openCamera() {
        TEMPORAL_PICTURE_NAME = String.valueOf(System.currentTimeMillis())  + ".jpg";

        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();

        String path = Environment.getExternalStorageDirectory() + file.separator
                + MEDIA_DIRECTORY + file.separator + TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(camaraIntent, PHOTO_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //controla foto
        switch (requestCode){
            case PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    String dir = Environment.getExternalStorageDirectory() + File.separator
                                + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

                    //guardamos la direccion para salvar la imagen del actio
                    activo.setImagen(dir);

                    decodeBitmap(dir);
                }
                break;
            case SELECT_PICTURE:
                if (resultCode == RESULT_OK){
                    Uri path = data.getData();


                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(path,filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    //guardamos la direccion para salvar la imagen del actio
                    activo.setImagen(filePath );

                    imvFoto.setImageURI(path);
                }
                break;
        }

        //Controla el registro de codigo de barras
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String contents = result.getContents();
            if (contents != null) {
                cargarCodigo(result.getContents(), vBotonClick);
            } else {
                Log.e("Codigo Barras","Fallo lectura de codigo de barras...");
            }
        }
    }

    private void decodeBitmap(String dir){
        bmp = BitmapFactory.decodeFile(dir);

        imvFoto.setImageBitmap(bmp);
    }

    public void cargarCodigo(String codigo, View v){
        switch (v.getId()){
            case R.id.BtnCodTic:
                edtCodTic = (EditText) findViewById(R.id.EdtCodTic);
                edtCodTic.setText(codigo);
                break;
            case R.id.BtnCodPat:
                edtCodPat = (EditText) findViewById(R.id.EdtCodPat);
                edtCodPat.setText(codigo);
                break;
            case R.id.BtnCodAct:
                edtCodAct = (EditText) findViewById(R.id.EdtCodAct);
                edtCodAct.setText(codigo);
                break;
            case R.id.BtnCodGer:
                edtCodGer = (EditText) findViewById(R.id.EdtCodGer);
                edtCodGer.setText(codigo);
                break;
        }

    }

    public void cargarUbicacion()
    {
        dbHelper = new DBHelper(getBaseContext());
        cUbicacion = CUbicacion.getInstance();
        cUbicacion.inicialize(dbHelper);

        ubicacions = cUbicacion.GetAll();

        area= new String[ubicacions.size()];

        for(int i=0; i<ubicacions.size(); i++){

            area[i] = ubicacions.get(i).getArea().toString();
        }
    }


    public void cargarTipo()
    {

        tipoActivos= cTipoActivo.GetAllTipo(NOMBRE);

        tipo= new String[tipoActivos.size()];

        for(int i=0; i<tipoActivos.size(); i++){

            tipo[i] = tipoActivos.get(i).getNombre().toString();
        }
    }

    private void cargarDatosUbicacion(int position) {
        Ubicacion ubicacion = ubicacions.get(position);

        id_ubicacion = ubicacion.getId();
        edtSector.setText(ubicacion.getSector());
        edtLugar.setText(ubicacion.getLugar());
    }

    private void cargarDatosTipo(int position) {

        TipoActivo tipoActivo = tipoActivos.get(position);

        id_tipo = tipoActivo.getId();
        txvDescripcion.setText(tipoActivo.getDescripcion().toString());

    }

    public void ObtenerTipoActivo(String tipo,String nombre,String descripcion){

    }

    public void getIdEmpleado()
    {
        SharedPreferences prefs =
                getSharedPreferences("LoginPreferences", getBaseContext().MODE_PRIVATE);

        String idEmpleado = prefs.getString("id", "0");
        String idInventario = prefs.getString("idInventario","0");

        activo.setIdEmpleado(Integer.valueOf(idEmpleado));
        activo.setIdInventario(Integer.valueOf(idInventario));
    }



/*Metodo de autocompletado*/
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /* Metodos de validacion */

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors)
        {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
            else
            {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
