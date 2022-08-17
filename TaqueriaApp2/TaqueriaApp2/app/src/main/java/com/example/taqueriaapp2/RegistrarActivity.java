package com.example.taqueriaapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {
    private Button btnRegistrar;
    private EditText edName;
    private EditText edUser;
    private EditText edPass;
    private EditText edPhone;
    String url = "http://192.168.0.106/tacoapp/insertar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        edName = (EditText) findViewById(R.id.editTextName);
        edUser = (EditText) findViewById(R.id.editTextUser);
        edPass = (EditText) findViewById(R.id.editTextPass);
        edPhone = (EditText) findViewById(R.id.editTextPhone);

        getSupportActionBar().setDisplayShowHomeEnabled(true); // activar toolbar
        getSupportActionBar().setIcon(R.drawable.aguacate); //agregar icono a toolbar

        //cambiar color de toolbar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolb)));

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();
            }
        });

    }

    public void insertar(){

        final String name = edName.getText().toString().trim();
        final String user = edUser.getText().toString().trim();
        final String pass =edPass.getText().toString().trim();
        final String phone =edPhone.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando...");

        if(name.isEmpty() && user.isEmpty() && pass.isEmpty() && phone.isEmpty()){
            Toast.makeText(RegistrarActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }else if(phone.length() != 10){
            Toast.makeText(RegistrarActivity.this, "Ingrese telefono valido(10 digitos)", Toast.LENGTH_SHORT).show();

        }else{

                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equalsIgnoreCase("insercion satisfactoria")){

                                    Toast.makeText(RegistrarActivity.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();

                                    progressDialog.dismiss();

                                    Intent intent = new Intent(RegistrarActivity.this, SesionActivity.class);
                                    //intent.putExtra("Mensaje_MainActivity", Mensaje);
                                    startActivity(intent);
                                }
                                else{
                                    //Toast.makeText(RegistrarActivity.this, response, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Toast.makeText(RegistrarActivity.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrarActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {//se guarda en un mapa para formar una entrada de valores

                        Map<String,String> params = new HashMap<>();

                        params.put("usuario",user);
                        params.put("nombre",name);
                        params.put("contrasena",pass);
                        params.put("telefono",phone);
                        return params;
                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(RegistrarActivity.this);
                requestQueue.add(request);



            }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

