package com.example.worldmatch;

import static com.example.worldmatch.Login.isAdmin;
import static com.example.worldmatch.direcciones.Direccion.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.worldmatch.adapters.LigaAdapter;
import com.example.worldmatch.direcciones.Direccion;
import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Liga;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuAdmin extends AppCompatActivity {

    private List<Liga> ligas;
    private CRUDinterface crudInterface;
    private RecyclerView recyclerView;
    private LigaAdapter ligaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.fondo));

        Button perfil = findViewById(R.id.Perfil);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, Perfil.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllLigas();

        Button anadirLiga = findViewById(R.id.anadirLiga);
        anadirLiga.setBackgroundColor(Color.TRANSPARENT);
        if(isAdmin == false){
            anadirLiga.setVisibility(View.GONE);
        }
        anadirLiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anadirLigas();
            }
        });

        Button favoritos = findViewById(R.id.Favoritos);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, Favoritos.class);
                startActivity(intent);
            }
        });

    }

    private void getAllLigas() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        crudInterface = retrofit.create(CRUDinterface.class);
        Call<List<Liga>> call = crudInterface.getAllLigas();
        call.enqueue(new Callback<List<Liga>>() {
            @Override
            public void onResponse(@NonNull Call<List<Liga>> call, @NonNull Response<List<Liga>> response) {
                if (response.isSuccessful()) {
                    ligas = response.body();
                    if (ligas != null) {
                        ligaAdapter = new LigaAdapter(ligas, MenuAdmin.this);
                        recyclerView.setAdapter(ligaAdapter);
                    } else {
                        Log.e("Response error: ", "Null products received");
                    }
                } else {
                    Log.e("Response error: ", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Liga>> call, @NonNull Throwable t) {
                Log.e("Throw error: ", t.getMessage());
            }
        });
    }

    private void anadirLigas() {
        // Crear los EditText para cada campo necesario
        final EditText editText1 = new EditText(this);
        final EditText editText2 = new EditText(this);

        editText1.setHint("Nombre de la liga...");
        editText2.setHint("Url (dirección de la imágen)");

        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.principal));
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(editText1);
        layout.addView(editText2);

        new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
                .setIcon(R.drawable.ic_logo)
                .setTitle("Añadir Liga")
                .setView(layout)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String nombreLiga = editText1.getText().toString();
                        String urlLiga = editText2.getText().toString();

                        if (nombreLiga.isEmpty() || urlLiga.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Campos vacíos", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CRUDinterface crudInterface = retrofit.create(CRUDinterface.class);
                        Liga liga = new Liga(nombreLiga, urlLiga);

                        Call<Liga> call = crudInterface.insertDataLiga(liga);

                        call.enqueue(new Callback<Liga>() {
                            @Override
                            public void onResponse(Call<Liga> call, Response<Liga> response) {
                                if (response.isSuccessful()) {
                                    Liga nuevaLiga = response.body();
                                    if (nuevaLiga != null) {
                                        ligas.add(nuevaLiga); // Añade la nueva liga a la lista
                                        ligaAdapter.notifyItemInserted(ligas.size() - 1); // Notifica al adaptador
                                        Toast.makeText(getApplicationContext(), "Liga insertada", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("Error: ", "Liga insertada es nula");
                                    }
                                } else {
                                    Log.e("Error: ", "Error al insertar liga");
                                    Log.d("Error: ", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<Liga> call, Throwable t) {
                                Log.e("Throw error: ", t.getMessage());
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Mensaje", "Cancelado");
                    }
                })
                .show();
    }
}