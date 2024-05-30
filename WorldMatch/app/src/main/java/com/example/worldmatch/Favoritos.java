package com.example.worldmatch;

import static com.example.worldmatch.Login.idCliente;
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

import com.example.worldmatch.adapters.FavoritoAdapter;
import com.example.worldmatch.adapters.JugadorAdapter;
import com.example.worldmatch.direcciones.Direccion;
import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Equipo;
import com.example.worldmatch.model.Favorito;
import com.example.worldmatch.model.Jugador;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Favoritos extends AppCompatActivity {

    private List<Favorito> favoritos;
    private CRUDinterface crudInterface;
    private RecyclerView recyclerView;
    private FavoritoAdapter favoritoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);


        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.fondo));
        recyclerView = findViewById(R.id.RecyclerViewFavoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllFavoritos();
    }

    private void getAllFavoritos() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Direccion.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        crudInterface = retrofit.create(CRUDinterface.class);
        Call<List<Favorito>> call = crudInterface.getAllFavoritos();
        call.enqueue(new Callback<List<Favorito>>() {
            @Override
            public void onResponse(@NonNull Call<List<Favorito>> call, @NonNull Response<List<Favorito>> response) {
                if (response.isSuccessful()) {
                    favoritos = response.body();
                    if (favoritos != null) {
                        List<Favorito> filteredFavoritos = new ArrayList<>();
                        for (Favorito favorito : favoritos) {
                            if (favorito.getIdUsuarioFavorito() == idCliente) {
                                filteredFavoritos.add(favorito);
                            }
                        }
                        favoritoAdapter = new FavoritoAdapter(filteredFavoritos, Favoritos.this);
                        recyclerView.setAdapter(favoritoAdapter);
                    } else {
                        Log.e("Jugador", "No se recibieron jugadores");
                    }
                } else {
                    Log.e("Jugador", "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Favorito>> call, @NonNull Throwable t) {
                Log.e("Jugador", "Error en la llamada: " + t.getMessage());
            }
        });
    }
}
