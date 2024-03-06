package com.example.worldmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.fondo));

        ImageView cesped1 = findViewById(R.id.cesped1);
        cesped1.setAlpha(0.4f);

        ImageView cesped2 = findViewById(R.id.cesped2);
        cesped2.setAlpha(0.4f);

        Button aceptar = findViewById(R.id.aceptRegistro);
        aceptar.setBackgroundColor(Color.TRANSPARENT);

        Button registrate = findViewById(R.id.registrate);
        registrate.setBackgroundColor(Color.TRANSPARENT);
        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });


    }
}