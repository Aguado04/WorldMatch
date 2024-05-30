package com.example.worldmatch;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldmatch.adapters.EquipoAdapter;
import com.example.worldmatch.adapters.PartidoAdapter;
import com.example.worldmatch.direcciones.Direccion;
import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Equipo;
import com.example.worldmatch.model.Partido;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Partidos extends AppCompatActivity {

    private static final String TAG = "Partidos";

    private RecyclerView recyclerView;
    private PartidoAdapter partidoAdapter;
    private EquipoAdapter equipoAdapter;

    private List<Equipo> equipos = new ArrayList<>();
    private List<Partido> partidos = new ArrayList<>();

    private CRUDinterface crudInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidos);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.fondo));

        recyclerView = findViewById(R.id.RecyclerViewPartidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        partidoAdapter = new PartidoAdapter();
        recyclerView.setAdapter(partidoAdapter);

        equipoAdapter = new EquipoAdapter(equipos, this);
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
                    List<Equipo> responseEquipos = response.body();
                    if (responseEquipos != null) {
                        equipos.addAll(responseEquipos);
                        equipoAdapter.notifyDataSetChanged();

                        // Generar partidos
                        generateMatches(equipos);
                    } else {
                        Log.e(TAG, "No se recibieron equipos");
                    }
                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Equipo>> call, @NonNull Throwable t) {
                Log.e(TAG, "Error en la llamada: " + t.getMessage());
            }
        });
    }

    private void generateMatches(List<Equipo> equipos) {
        if (equipos.isEmpty()) {
            Log.e(TAG, "No hay equipos disponibles para generar partidos.");
            return;
        }

        partidos.clear();
        Random random = new Random();


        for (int i = 0; i < equipos.size() - 1; i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                Equipo equipoLocal = equipos.get(i);
                Equipo equipoVisitante = equipos.get(j);


                if (equipoLocal.getIdLigaEquipo() == equipoVisitante.getIdLigaEquipo()) {

                    int golesLocal = random.nextInt(6);
                    int golesVisitante = random.nextInt(6);


                    Partido partido = new Partido(equipoLocal, equipoVisitante, golesLocal, golesVisitante);
                    partidos.add(partido);
                }
            }
        }


        partidoAdapter.setPartidos(partidos);
        partidoAdapter.notifyDataSetChanged();
    }
}