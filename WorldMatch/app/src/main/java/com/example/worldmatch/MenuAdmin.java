package com.example.worldmatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    }

    private void getAllLigas() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Direccion.BASE_URL)
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
}