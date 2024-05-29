package com.example.worldmatch;

import static com.example.worldmatch.Login.isAdmin;
import static com.example.worldmatch.direcciones.Direccion.BASE_URL;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldmatch.adapters.EquipoAdapter;
import com.example.worldmatch.direcciones.Direccion;
import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Equipo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Equipos extends AppCompatActivity {

    private List<Equipo> equipos;
    private CRUDinterface crudInterface;
    private RecyclerView recyclerView;
    private EquipoAdapter equipoAdapter;
    private int ligaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);

        String ligaNombre = getIntent().getStringExtra("LigaNombre");
        ligaId = getIntent().getIntExtra("LigaId", -1);

        TextView ligaNombreTextView = findViewById(R.id.ligaNombreTextView);
        ligaNombreTextView.setText(ligaNombre);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.fondo));

        recyclerView = findViewById(R.id.RecyclerViewEquipos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        equipos = new ArrayList<>();
        equipoAdapter = new EquipoAdapter(equipos, this);
        recyclerView.setAdapter(equipoAdapter);

        getAllEquipos();

        Button anadirEquipo = findViewById(R.id.anadirEquipo);
        anadirEquipo.setBackgroundColor(Color.TRANSPARENT);
        if(isAdmin == false){
            anadirEquipo.setVisibility(View.GONE);
        }
        anadirEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anadirEquipos();
            }
        });
    }

    private void getAllEquipos() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Direccion.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        crudInterface = retrofit.create(CRUDinterface.class);
        Call<List<Equipo>> call = crudInterface.getAllEquipos();
        call.enqueue(new Callback<List<Equipo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Equipo>> call, @NonNull Response<List<Equipo>> response) {
                if (response.isSuccessful()) {
                    equipos = response.body();
                    if (equipos != null) {
                        List<Equipo> filteredEquipos = new ArrayList<>();
                        for (Equipo equipo : equipos) {
                            if (equipo.getIdLigaEquipo() == ligaId) { // Filtrar por liga
                                filteredEquipos.add(equipo);
                            }
                        }
                        equipoAdapter.setEquipos(filteredEquipos); // Usar los equipos filtrados
                        equipoAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("Equipos", "No se recibieron equipos");
                    }
                } else {
                    Log.e("Equipos", "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Equipo>> call, @NonNull Throwable t) {
                Log.e("Equipos", "Error en la llamada: " + t.getMessage());
            }
        });
    }

    private void anadirEquipos() {

        final EditText editText1 = new EditText(this);
        editText1.setHint("Nombre del equipo...");

        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.principal));
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(editText1);

        new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
                .setIcon(R.drawable.ic_logo)
                .setTitle("Añadir Equipo")
                .setView(layout)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String nombreEquipo = editText1.getText().toString();

                        if (nombreEquipo.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Campos vacíos", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CRUDinterface crudInterface = retrofit.create(CRUDinterface.class);
                        Equipo equipo = new Equipo(nombreEquipo, ligaId);

                        Call<Equipo> call = crudInterface.insertDataEquipo(equipo);

                        call.enqueue(new Callback<Equipo>() {
                            @Override
                            public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                                if (response.isSuccessful()) {
                                    Equipo nuevoEquipo = response.body();
                                    if (nuevoEquipo != null) {
                                        equipos.add(nuevoEquipo); // Agregar el nuevo equipo a la lista
                                        // Filtrar equipos por la liga seleccionada
                                        List<Equipo> filteredEquipos = new ArrayList<>();
                                        for (Equipo equipo : equipos) {
                                            if (equipo.getIdLigaEquipo() == ligaId) {
                                                filteredEquipos.add(equipo);
                                            }
                                        }
                                        // Actualizar el adaptador con la lista filtrada actualizada
                                        equipoAdapter.setEquipos(filteredEquipos);
                                        equipoAdapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                                        Toast.makeText(getApplicationContext(), "Equipo insertado", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("Error: ", "Equipo insertado es nula");
                                    }
                                } else {
                                    Log.e("Error: ", "Error al insertar equipo");
                                    Log.d("Error: ", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<Equipo> call, Throwable t) {
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