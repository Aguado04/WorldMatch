package com.example.worldmatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.worldmatch.adapters.JugadorAdapter;
import com.example.worldmatch.direcciones.Direccion;
import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Jugador;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Jugadores extends AppCompatActivity {

    private List<Jugador> jugadores;
    private CRUDinterface crudInterface;
    private RecyclerView recyclerView;
    private JugadorAdapter jugadorAdapter;
    private int equipoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugadores);

        String equipoNombre = getIntent().getStringExtra("EquipoNombre");
        equipoId = getIntent().getIntExtra("EquipoId", -1);
        TextView equipoNombreTextView = findViewById(R.id.equipoNombreTextView);

        equipoNombreTextView.setText(equipoNombre);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.fondo));
        recyclerView = findViewById(R.id.RecyclerViewJugadores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllJugadores();
    }

    private void getAllJugadores() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Direccion.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        crudInterface = retrofit.create(CRUDinterface.class);
        Call<List<Jugador>> call = crudInterface.getAllJugadores();
        call.enqueue(new Callback<List<Jugador>>() {
            @Override
            public void onResponse(@NonNull Call<List<Jugador>> call, @NonNull Response<List<Jugador>> response) {
                if (response.isSuccessful()) {
                    jugadores = response.body();
                    if (jugadores != null) {
                        List<Jugador> filteredJugadores = new ArrayList<>();
                        for (Jugador jugador : jugadores) {
                            if (jugador.getIdEquipoJugador() == equipoId) {
                                filteredJugadores.add(jugador);
                            }
                        }
                        jugadorAdapter = new JugadorAdapter(filteredJugadores, Jugadores.this);
                        recyclerView.setAdapter(jugadorAdapter);
                    } else {
                        Log.e("Jugador", "No se recibieron jugadores");
                    }
                } else {
                    Log.e("Jugador", "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Jugador>> call, @NonNull Throwable t) {
                Log.e("Jugador", "Error en la llamada: " + t.getMessage());
            }
        });
    }
}