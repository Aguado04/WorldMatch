package com.example.worldmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.fondo));

        ImageView cesped1 = findViewById(R.id.cesped1);
        cesped1.setAlpha(0.4f);

        ImageView cesped2 = findViewById(R.id.cesped2);
        cesped2.setAlpha(0.4f);

        Button aceptar = findViewById(R.id.aceptRegistro);
        aceptar.setBackgroundColor(Color.TRANSPARENT);
    }
}