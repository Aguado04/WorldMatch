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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldmatch.adapters.JugadorAdapter;
import com.example.worldmatch.direcciones.Direccion;
import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Equipo;
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

        Button anadirJugador = findViewById(R.id.anadirJugador);
        anadirJugador.setBackgroundColor(Color.TRANSPARENT);
        if(isAdmin == false){
            anadirJugador.setVisibility(View.GONE);
        }
        anadirJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anadirJugadores();
            }
        });
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

    private void anadirJugadores() {

        final EditText editText1 = new EditText(this);
        final EditText editText2 = new EditText(this);
        final EditText editText3 = new EditText(this);

        editText1.setHint("Nombre del jugador...");
        editText2.setHint("Edad del jugador...");
        editText3.setHint("Dorsal del jugador...");

        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.principal));
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(editText1);
        layout.addView(editText2);
        layout.addView(editText3);

        new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
                .setIcon(R.drawable.ic_logo)
                .setTitle("Añadir Jugador")
                .setView(layout)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String nombreJugador = editText1.getText().toString();
                        String edadJugadorString = editText2.getText().toString();
                        String dorsalJugadorString = editText3.getText().toString();

                        if (nombreJugador.isEmpty() || edadJugadorString.isEmpty() || dorsalJugadorString.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Campos vacíos", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Integer edadJugador = Integer.valueOf(edadJugadorString);
                        Integer dorsalJugador = Integer.valueOf(dorsalJugadorString);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CRUDinterface crudInterface = retrofit.create(CRUDinterface.class);
                        Jugador jugador = new Jugador(nombreJugador, edadJugador, dorsalJugador, equipoId);

                        Call<Jugador> call = crudInterface.insertDataJugador(jugador);

                        call.enqueue(new Callback<Jugador>() {
                            @Override
                            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                                if (response.isSuccessful()) {
                                    Jugador nuevoJugador = response.body();
                                    if (nuevoJugador != null) {
                                        jugadores.add(nuevoJugador);

                                        List<Jugador> filteredJugadores = new ArrayList<>();
                                        for (Jugador jugador : jugadores) {
                                            if (jugador.getIdEquipoJugador() == equipoId) {
                                                filteredJugadores.add(jugador);
                                            }
                                        }

                                        jugadorAdapter.setJugadores(filteredJugadores);
                                        jugadorAdapter.notifyDataSetChanged();
                                        Toast.makeText(getApplicationContext(), "Jugador insertado", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("Error: ", "Jugador insertado es nulo");
                                    }
                                } else {
                                    Log.e("Error: ", "Error al insertar jugador");
                                    Log.d("Error: ", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<Jugador> call, Throwable t) {
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