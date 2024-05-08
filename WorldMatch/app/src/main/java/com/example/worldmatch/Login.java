package com.example.worldmatch;

import static com.example.worldmatch.direcciones.Direccion.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Cliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    static int idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.fondo));

        CheckBox acepto = findViewById(R.id.terminos);

        ImageView cesped1 = findViewById(R.id.cesped1);
        cesped1.setAlpha(0.4f);

        ImageView cesped2 = findViewById(R.id.cesped2);
        cesped2.setAlpha(0.4f);

        Button aceptar = findViewById(R.id.aceptLogin);
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

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = ((EditText) findViewById(R.id.username)).getText().toString();
                String contrasena = ((EditText) findViewById(R.id.password)).getText().toString();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CRUDinterface crudInterface = retrofit.create(CRUDinterface.class);
                Call<List<Cliente>> call = crudInterface.getAllClientes();
                call.enqueue(new Callback<List<Cliente>>() {
                    @Override
                    public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                        if(acepto.isChecked()){
                            if (!nombre.isEmpty() && !contrasena.isEmpty()) {
                                if (response.isSuccessful()) {
                                    List<Cliente> clientes = response.body();
                                    boolean credencialesValidas = false;
                                    for (Cliente cliente : clientes) {
                                        if (cliente.getNombre().equals(nombre) && cliente.getContrasena().equals(contrasena)) {
                                            idCliente = cliente.getId();
                                            credencialesValidas = true;
                                            break;
                                        }
                                    }
                                    if (credencialesValidas) {
                                        if (nombre.equals("admin")) {
                                            Intent intent = new Intent(Login.this, MenuAdmin.class);
                                            startActivity(intent);
                                            Toast.makeText(getApplicationContext(), "Administrador", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Intent intent = new Intent(Login.this, MenuCliente.class);
                                            startActivity(intent);
                                            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Acepta los términos", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Cliente>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Credenciales mal introducidas", Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });


    }
}