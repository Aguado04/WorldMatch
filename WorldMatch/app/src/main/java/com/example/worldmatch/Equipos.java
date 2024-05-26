package com.example.worldmatch;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

        getAllEquipos();
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
                            if (equipo.getIdLigaEquipo() == ligaId) {
                                filteredEquipos.add(equipo);
                            }
                        }
                        equipoAdapter = new EquipoAdapter(filteredEquipos, Equipos.this);
                        recyclerView.setAdapter(equipoAdapter);
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
}