package com.example.taqueriaapp2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DomicilioActivity extends AppCompatActivity {

    String total="";
    String descripcion="";
    String userName="";

    private TextView txtTotal;
    private TextView txtPedido;
    private  TextView txtID;
    private ImageButton imgBtnCam;
    private EditText edCalle;
    private ImageView imgFoto;
    private Button btnPedido;
    private AutoCompleteTextView edLocalidad;
    String url1 = "http://192.168.0.106/tacoapp/actualizar.php";
    String url2 = "http://192.168.0.106/tacoapp/insertarPedido.php";
    int id=0;
    private static final String[] localidades = new String[]{
            "Huatlatlauca","Tepanacitla","Tepetzicintla","Chimala", "Atlalpa","Copalcotitla","Santo Tomas", "Tochmatzintla",
            "San Juan Atzompa", "Quetzalapa","Pemex","Copal","Tempesquixtla","Colonia","Santa Maria","San Miguel","San Pablo",
            "San Nicolás"
    } ;

    private final int PICTURE_FROM_CAMERA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domicilio);

        getSupportActionBar().setDisplayShowHomeEnabled(true); // activar toolbar
        getSupportActionBar().setIcon(R.drawable.aguacate); //agregar icono a toolbar
        //cambiar color de toolbar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolb)));

        imgBtnCam = (ImageButton) findViewById(R.id.imgBtnCam);
        edCalle = (EditText) findViewById(R.id.editTextCalle);
        imgFoto = (ImageView) findViewById(R.id.imageFoto);
        txtTotal = (TextView) findViewById(R.id.textViewTotal);
        txtPedido = (TextView) findViewById(R.id.textViewPedido);
        btnPedido = (Button) findViewById(R.id.btnPedido);
        edLocalidad = ( AutoCompleteTextView) findViewById(R.id.mtcLocalidad);
        txtID = (TextView) findViewById(R.id.textViewID);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DomicilioActivity.this, android.R.layout.simple_list_item_1,localidades);
        edLocalidad.setAdapter(adapter);
        //edLocalidad.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        //recibe el nombre del usuario, total a pagar y descripciòn del pedido
        Bundle bundle = getIntent().getExtras();
        if(bundle != null ){
            userName = bundle.getString("Mensaje_userName");
            descripcion = bundle.getString("Mensaje_pedido");
            total = bundle.getString("Mensaje_total");

            Random ran = new Random();
            id= ran.nextInt(1+200)+1;
            txtTotal.setText("Total del pedido: $"+total);
            txtPedido.setText("Descripcion del pedido: "+descripcion);
            txtID.setText("ID de pedido:  PE"+String.valueOf(id));
            //txtTotal.setEnabled(false);
            Toast.makeText(DomicilioActivity.this,userName+"Su ID de pedido es:  "+"PE"+String.valueOf(id)+"  conservelo para futuras consultas", Toast.LENGTH_LONG).show();

        }else{
            //Toast.makeText(DomicilioActivity.this,"Mensaje vacio", Toast.LENGTH_LONG).show();
        }

      imgBtnCam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //abrir la mara directamente, no una aplicacion de camara
                Intent intentCamara = new Intent("android.media.action.IMAGE_CAPTURE");
                //startActivity(intentCamara);
                startActivityForResult(intentCamara, PICTURE_FROM_CAMERA);
            }
        });

        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edCalle.getText().toString().length() != 0 && edLocalidad.getText().toString().length() != 0){
                    actualizar(); // se actualizara bd de clientes
                    AlertDialog.Builder alert = new AlertDialog.Builder(DomicilioActivity.this);
                    alert.setMessage("¡Le pedimos un poco de paciencia su pedido llegará lo mas pronto posible, GRACIAS POR SU PREFERENCIA!")
                            .setCancelable(false)
                            .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    insertarPedido(); //insertara pedidos en la tabla de pedidos
                                }
                            })
                            .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog title = alert.create();
                    title.setTitle("CONFIRME");
                    title.show();
                    
                }else{
                    Toast.makeText(DomicilioActivity.this,userName+" Ingrese todo los datos necesarios ", Toast.LENGTH_LONG).show();

                }
            }
        });


    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            switch(requestCode){
                case PICTURE_FROM_CAMERA:
                    if( resultCode== Activity.RESULT_OK){
                        Toast.makeText(DomicilioActivity.this,"foto tomada con exito", Toast.LENGTH_LONG).show();
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imgFoto.setImageBitmap(imageBitmap);

                    }else{
                        Toast.makeText(DomicilioActivity.this,"foto no", Toast.LENGTH_LONG).show();
                    }
                    break;

                default:
                    super.onActivityResult(requestCode,resultCode,data);
            }
        }

    public void actualizar(){
        final String direccion = edCalle.getText().toString();
        final String localidad = edLocalidad.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(response.equalsIgnoreCase("Domicilio insertado")){

                        Toast.makeText(DomicilioActivity.this, "Domicilio ingresado correctamente", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                    }
                    else{
                        //Toast.makeText(DomicilioActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //Toast.makeText(RegistrarActivity.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DomicilioActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                   // Toast.makeText(DomicilioActivity.this,"ERROR on error response",Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {//se guarda en un mapa para formar una entrada de valores

                    Map<String,String> params = new HashMap<>();

                    params.put("usuario",userName);
                    params.put("localidad",localidad );
                    params.put("direccion",direccion);
                    params.put("fotoRef","foto.png");
                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(DomicilioActivity.this);
            requestQueue.add(request);



        }

    public void insertarPedido(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url2,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equalsIgnoreCase("inserto pedido")){

                    Toast.makeText(DomicilioActivity.this, "Se ha registrado correctamente, GRACIAS POR SU PREFERENCIA", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                    // Intent intent=new Intent(RegistrarActivity.this,login.class);
                    //startActivity(intent);
                }
                else{
                    Toast.makeText(DomicilioActivity.this,"Ya existe un pedido con ese ID", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    //Toast.makeText(RegistrarActivity.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DomicilioActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Toast.makeText(DomicilioActivity.this,"ERROR on error response",Toast.LENGTH_SHORT).show();
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {//se guarda en un mapa para formar una entrada de valores

                Map<String,String> params = new HashMap<>();
                String ID ="PE"+String.valueOf(id);
                params.put("id",ID);
                params.put("usuario",userName);
                params.put("descripcion",descripcion);
                params.put("total",total);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(DomicilioActivity.this);
        requestQueue.add(request);



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /*String currentPhotoPath;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "FotoRef_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //Toast.makeText(OrdenarActivity.this, StrstorageDir, Toast.LENGTH_SHORT).show();

        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      //directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    */
}