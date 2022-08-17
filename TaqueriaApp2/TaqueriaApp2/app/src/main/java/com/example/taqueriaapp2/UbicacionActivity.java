package com.example.taqueriaapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class UbicacionActivity extends AppCompatActivity {

    private ImageButton imgBtnEmail;
    private EditText editTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);
        imgBtnEmail = (ImageButton) findViewById(R.id.imgBtnEmail);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        getSupportActionBar().setDisplayShowHomeEnabled(true); // activar toolbar
        getSupportActionBar().setIcon(R.drawable.aguacate); //agregar icono a toolbar

        //cambiar color de toolbar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolb)));

        imgBtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String url = editTextEmail.getText().toString();
                    //Intent intentWEB = new Intent(Intent.ACTION_VIEW, uri);
                    Intent intentWEB = new Intent();
                    intentWEB.setAction(Intent.ACTION_VIEW);
                    intentWEB.setData(Uri.parse("http://"+url));

                    Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("areherrrera59@gmail.com"));
                    intent.setType("message/rfc822");

                    //Intent contactos
                    //-Intent intentMail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+correo));
                    //-startActivity(intentMail);
                    //startActivity(intentWEB);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"areherrrera59@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "test");
                    intent.putExtra(Intent.EXTRA_TEXT,"hello this a message send from my demo app :-)");
                    startActivity(Intent.createChooser(intent, "elige aplicacion"));
            }
        });
    }
}