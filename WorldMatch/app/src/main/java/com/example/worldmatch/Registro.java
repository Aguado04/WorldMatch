package com.example.worldmatch;

import static com.example.worldmatch.direcciones.Direccion.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Cliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CRUDinterface crudInterface = retrofit.create(CRUDinterface.class);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = ((EditText) findViewById(R.id.username)).getText().toString();
                String contrasena = ((EditText) findViewById(R.id.password)).getText().toString();
                String email = ((EditText) findViewById(R.id.correo)).getText().toString();

                Cliente cliente = new Cliente(nombre, contrasena, email);

                Call<Cliente> call = crudInterface.insertData(cliente);

                call.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        if(!nombre.isEmpty() && !contrasena.isEmpty() && !email.isEmpty()){
                            if (response.isSuccessful()) {
                                Log.e("Bien: ", "Cliente insertado");
                                Toast.makeText(getApplicationContext(), "Registrado correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Registro.this, Login.class);
                                startActivity(intent);
                            } else {
                                Log.e("Error: ", "Error al insertar cliente");
                                Log.d("Error: ", response.message());
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Campos vacíos", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {
                        Log.e("Response error: ", t.getMessage());
                    }
                });

            }
        });


    }
}