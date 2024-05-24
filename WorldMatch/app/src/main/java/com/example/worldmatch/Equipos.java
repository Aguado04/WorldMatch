package com.example.worldmatch;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldmatch.adapters.EquipoAdapter;
import com.example.worldmatch.adapters.LigaAdapter;
import com.example.worldmatch.direcciones.Direccion;
import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Equipo;
import com.example.worldmatch.model.Liga;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);

        String ligaNombre = getIntent().getStringExtra("LigaNombre");
        TextView ligaNombreTextView = findViewById(R.id.ligaNombreTextView);
        ligaNombreTextView.setText(ligaNombre);

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
                        equipoAdapter = new EquipoAdapter(equipos, Equipos.this);
                        recyclerView.setAdapter(equipoAdapter);
                    } else {
                        Log.e("Response error: ", "Null products received");
                    }
                } else {
                    Log.e("Response error: ", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Equipo>> call, @NonNull Throwable t) {
                Log.e("Throw error: ", t.getMessage());
            }
        });
    }
}