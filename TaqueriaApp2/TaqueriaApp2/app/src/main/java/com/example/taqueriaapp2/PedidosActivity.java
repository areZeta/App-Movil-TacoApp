package com.example.taqueriaapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
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

public class PedidosActivity extends AppCompatActivity {

    private TextView txtId;
    private  TextView txtDescrp;
    private  TextView txtFecha;
    private TextView txtEstado;
    private Button btnBuscar;
    private Button btnRecibido;
    private String userName;
    private EditText edID;

    String url = "http://192.168.0.106/tacoApp/consulta.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        txtId = (TextView) findViewById(R.id.textViewID);
        txtDescrp = (TextView) findViewById(R.id.textViewDescrip);
        txtFecha = (TextView) findViewById(R.id.textViewFecha);
        txtEstado= (TextView) findViewById(R.id.textViewEstado);
        btnBuscar = (Button)  findViewById(R.id.btnBuscarID);
        btnRecibido =(Button) findViewById(R.id.btnRecibido);
        edID = (EditText) findViewById(R.id.editTextID);



        getSupportActionBar().setDisplayShowHomeEnabled(true); // activar toolbar
        getSupportActionBar().setIcon(R.drawable.aguacate); //agregar icono a toolbar
        //cambiar color de toolbar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolb)));
        edID.setText("");
        Bundle bundle = getIntent().getExtras();
        if(bundle != null ){
            userName = bundle.getString("userName");
            Toast.makeText(PedidosActivity.this,userName, Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(OrdenarActivity.this,"Mensaje vacio", Toast.LENGTH_LONG).show();
        }

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edID.getText().toString().length() != 0){
                    consulta(view,"2");
                }else{
                    Toast.makeText(PedidosActivity.this,"Ingrese el ID de su pedido",Toast.LENGTH_SHORT ).show();
                }

            }
        });

        btnRecibido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEstado.setText("Recibido");
                consulta(view, "3");

            }
        });


    }

    public  void consulta(View view, String opcion){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Por favor espera...");

        progressDialog.show();

        String id = edID.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if(opcion == "2"){
                    if(response.equalsIgnoreCase("No existen pedidos con tal ID")){
                        Toast.makeText(PedidosActivity.this, response, Toast.LENGTH_SHORT).show();

                    }else{
                        String[] dates = response.split("/");
                        txtId.setText(dates[0]);
                        txtDescrp.setText(dates[1]);
                        txtFecha.setText(dates[2]);
                        txtEstado.setText(dates[3]);
                        btnRecibido.setVisibility(View.VISIBLE); //pone visible el boton de marcar como recibido
                        Toast.makeText(PedidosActivity.this,"Su ID "+String.valueOf(id), Toast.LENGTH_SHORT).show();
                    }
                }else if(opcion=="3"){
                    if(response.equalsIgnoreCase("Estado de su pedido actualizado")){
                        Toast.makeText(PedidosActivity.this,response, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PedidosActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("usuario",userName);
                params.put("opcion",opcion);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PedidosActivity.this);
        requestQueue.add(request);
    }

}