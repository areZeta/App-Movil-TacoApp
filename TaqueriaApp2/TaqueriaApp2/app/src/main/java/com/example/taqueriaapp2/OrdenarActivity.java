package com.example.taqueriaapp2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class OrdenarActivity extends AppCompatActivity {
    private Button btnOrdenar;
    private  Button btnCalcular;
    private EditText edPasa;
    private EditText edPlong;
    private EditText edPcam;
    private EditText edTasa;
    private EditText edTlong;
    private  EditText edTcam;
    private  EditText edCasa;
    private  EditText edClong;
    private  EditText edCcam;
    private TextView txTotal;
    private ImageButton imgBtnTel;
    private  EditText edTotal;

    private final int PHONE_CALL_CODE = 100;
    int total = 0;
    String descripPedido ="";
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenar);

        getSupportActionBar().setDisplayShowHomeEnabled(true); // activar toolbar
        getSupportActionBar().setIcon(R.drawable.aguacate); //agregar icono a toolbar

        //cambiar color de toolbar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolb)));

        btnOrdenar = (Button) findViewById(R.id.btnOrden);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        edPasa = (EditText) findViewById(R.id.editTextPasada);
        edPlong = (EditText) findViewById(R.id.editTextPLonga);
        edPcam = (EditText) findViewById(R.id.editTextPCampe);


        edTasa =  (EditText) findViewById(R.id.editTextTasada);
        edTlong =  (EditText) findViewById(R.id.editTextTLonga);
        edTcam = (EditText) findViewById(R.id.editTextTCampe);

        edCasa =  (EditText) findViewById(R.id.editTextCasada);
        edClong =  (EditText) findViewById(R.id.editTextCLonga);
        edCcam =  (EditText) findViewById(R.id.editTextCCampe);
        imgBtnTel = (ImageButton) findViewById(R.id.imgBtnTelefono);
        txTotal =  (TextView) findViewById(R.id.textTotal);

        //recibe el nombre del usuario para darle la bienvenida
        Bundle bundle = getIntent().getExtras();
        if(bundle != null ){
            userName = bundle.getString("Mensaje_userName");

            Toast.makeText(OrdenarActivity.this,"BIENVENIDO "+userName, Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(OrdenarActivity.this,"Mensaje vacio", Toast.LENGTH_LONG).show();
        }

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descripPedido = "";
                total=0;
                if(edPasa.getText().toString().length() != 0){
                    descripPedido +=edPasa.getText().toString()+" kg de asada, ";
                    total += Integer.parseInt(edPasa.getText().toString())* 110;
                }
                if(edPlong.getText().toString().length() != 0){
                    descripPedido +=edPlong.getText().toString()+" kg de longanizada, ";
                    total += Integer.parseInt(edPlong.getText().toString()) * 90;
                }
                if(edPcam.getText().toString().length() != 0){
                    descripPedido +=edPcam.getText().toString()+" kg de campechana, ";
                    total+=Integer.parseInt(edPcam.getText().toString()) * 140;
                }
                if(edTasa.getText().toString().length() != 0){
                    descripPedido +=edTasa.getText().toString()+" tacos de asada, ";
                    total+=Integer.parseInt(edTasa.getText().toString()) * 14;

                }
                if(edTlong.getText().toString().length() != 0){
                    descripPedido +=edTlong.getText().toString()+" tacos de longaniza, ";
                    total+=Integer.parseInt(edTlong.getText().toString()) * 14;
                }
                if(edTcam.getText().toString().length() != 0){
                    descripPedido +=edTcam.getText().toString()+" tacos campechanos, ";
                    total+=Integer.parseInt(edTcam.getText().toString()) * 14;
                }
                if(edCasa.getText().toString().length() != 0){
                    descripPedido +=edCasa.getText().toString()+" cemitas de asada, ";
                    total+=Integer.parseInt(edCasa.getText().toString()) * 25;
                }
                if(edClong.getText().toString().length() != 0){
                    descripPedido +=edClong.getText().toString()+" cemitas de longaniza, ";
                    total+=Integer.parseInt(edClong.getText().toString()) * 25;
                }
                if(edCcam.getText().toString().length() != 0){
                    descripPedido +=edCcam.getText().toString()+" cemitas campechanas, ";
                    total+=Integer.parseInt(edCcam.getText().toString()) * 25;
                }
                if(total > 0){
                    btnOrdenar.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(OrdenarActivity.this,"Usted no a ordenado",Toast.LENGTH_LONG).show();
                }
                //total= Integer.parseInt(edPasa.getText().toString())*110;
                //total= (Integer.parseInt(edPasa.getText().toString())* 110)+(Integer.parseInt(edPlong.getText().toString()) * 90)+ (Integer.parseInt(edPcam.getText().toString()) * 140)+((Integer.parseInt(edTasa.getText().toString())+ Integer.parseInt(edTlong.getText().toString()) +Integer.parseInt(edTcam.getText().toString()))* 14)+((Integer.parseInt(edCasa.getText().toString())+Integer.parseInt(edClong.getText().toString())+Integer.parseInt(edCcam.getText().toString())) *25);
               // Toast.makeText(OrdenarActivity.this,"TOTAL: $"+String.valueOf(total),Toast.LENGTH_LONG).show();
                txTotal.setText("TOTAL: $"+String.valueOf(total));
            }
        });

        btnOrdenar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(OrdenarActivity.this);
                alert.setMessage("¿Su pedido es correcto? : \n"+descripPedido+"\nTotal: "+String.valueOf(total))
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(OrdenarActivity.this, DomicilioActivity.class);
                                intent.putExtra("Mensaje_pedido", descripPedido);
                                intent.putExtra("Mensaje_userName", userName);
                                intent.putExtra("Mensaje_total", String.valueOf(total));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog title = alert.create();
                title.setTitle("CONFIRME");
                title.show();
                
                    


            }
        });

        imgBtnTel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String NumTel = "2231253548";
                    //comprobar version actual donde se ejecuta la aplicacion
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        //MEJORA, verificamos si el usuario ya concedio el permiso de la llamada
                        //no la a concedido
                        //o nunca se la a preguntado
                        if(VerificarPermiso(Manifest.permission.CALL_PHONE)){
                            //se acepto el permiso
                            Intent i= new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+NumTel));
                            startActivity(i);
                        }else{
                            //no se acepto el permiso, o no se a realizado la solicitud
                            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                //no se ha preguntado aun
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                            }else{
                                //no ha aceptado
                                Toast.makeText(OrdenarActivity.this, "Porfavor activa el permiso requerido", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName()));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);
                            }

                        }

                        //requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                    }else{
                        VersionesAntiguas(NumTel);
                    }

            }

            private void VersionesAntiguas(String NumTel){
                Intent intentLlama = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+NumTel));
                if(VerificarPermiso(Manifest.permission.CALL_PHONE)){
                    startActivity(intentLlama);
                }else{
                    Toast.makeText(OrdenarActivity.this, "Declinaste el permiso de llamada", Toast.LENGTH_SHORT).show();
                }
            }
       });
    }
    final String username2 =userName;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.btnPedidos:
                Intent intent3;
                intent3 = new Intent(OrdenarActivity.this, PedidosActivity.class);
                intent3.putExtra("userName",userName);
                startActivity(intent3);
                break;

            case R.id.btnInfor:
                Intent intent2;
                intent2 = new Intent(OrdenarActivity.this, UbicacionActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnLogOut:
                Intent intent;
                intent = new Intent(OrdenarActivity.this, SesionActivity.class);
                startActivity(intent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private boolean VerificarPermiso(String permiso){
        int resultado = this.checkCallingOrSelfPermission(permiso);
        return resultado == PackageManager.PERMISSION_GRANTED;
    }
}