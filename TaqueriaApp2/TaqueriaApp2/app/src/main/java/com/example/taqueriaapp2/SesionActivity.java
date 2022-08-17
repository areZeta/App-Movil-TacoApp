package com.example.taqueriaapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
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

public class SesionActivity extends AppCompatActivity {
     private Button btnIniciar;
     private EditText etUsuario;
     private EditText etPass;
     String url = "http://192.168.0.106/tacoApp/consulta.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);
        btnIniciar = (Button) findViewById(R.id.btnIniciarSesion);
        etUsuario = (EditText) findViewById(R.id.editTextIDUsuario);
        etPass = (EditText) findViewById(R.id.editTextPass);

        etUsuario.setText("");
        etPass.setText("");
        getSupportActionBar().setDisplayShowHomeEnabled(true); // activar toolbar
        getSupportActionBar().setIcon(R.drawable.aguacate);
        //cambiar color de toolbar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolb)));

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUsuario.getText().toString().trim().length() == 0){
                    Toast.makeText(SesionActivity.this, "Ingrese usuario valido", Toast.LENGTH_SHORT).show();
                }else if(etPass.getText().toString().trim().length() == 0){
                    Toast.makeText(SesionActivity.this,"Ingrese contraseña valida",Toast.LENGTH_SHORT).show();
                }else{
                    busquedaDatos(view);


                }
            }
        });

    }


    public  void busquedaDatos(View view){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espera...");

            progressDialog.show();

            String usuario = etUsuario.getText().toString().trim();
            String pass = etPass.getText().toString().trim();



            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if(response.equalsIgnoreCase("Usuario existe en la BD")){
                        Toast.makeText(SesionActivity.this, response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SesionActivity.this, OrdenarActivity.class);
                        intent.putExtra("Mensaje_userName", etUsuario.getText().toString());
                        startActivity(intent);
                    }else{
                        Toast.makeText(SesionActivity.this, response, Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(SesionActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("usuario",usuario);
                    params.put("contrasena",pass);
                    params.put("opcion","1");
                    return params;
                }
            };
        RequestQueue requestQueue = Volley.newRequestQueue(SesionActivity.this);
        requestQueue.add(request);
    }
}