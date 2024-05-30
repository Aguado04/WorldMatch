package com.example.worldmatch.adapters;

import static com.example.worldmatch.Login.idCliente;
import static com.example.worldmatch.direcciones.Direccion.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.worldmatch.Equipos;
import com.example.worldmatch.R;
import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Favorito;
import com.example.worldmatch.model.Liga;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LigaAdapter extends RecyclerView.Adapter<LigaAdapter.LigaViewHolder> {
    private List<Liga> ligas;
    private Context context;

    public LigaAdapter(List<Liga> ligas, Context context) {
        this.ligas = ligas;
        this.context = context;
    }

    @NonNull
    @Override
    public LigaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ligas_list, parent, false);
        return new LigaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LigaViewHolder holder, int position) {
        Liga liga = ligas.get(position);
        holder.bind(liga);
    }

    @Override
    public int getItemCount() {
        return ligas.size();
    }

    public class LigaViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        ImageView ligaView;
        Button equiposButton;
        Button favoritosButton;

        public LigaViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.NombreLiga);
            ligaView = itemView.findViewById(R.id.FotoLiga);
            equiposButton = itemView.findViewById(R.id.equipos);
            favoritosButton = itemView.findViewById(R.id.ligaFavorito);

            equiposButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Liga selectedLiga = ligas.get(position);
                        Intent intent = new Intent(context, Equipos.class);
                        intent.putExtra("LigaNombre", selectedLiga.getNombre());
                        intent.putExtra("LigaId", selectedLiga.getId());
                        context.startActivity(intent);
                    }
                }
            });

            favoritosButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Liga selectedLiga = ligas.get(position);
                        anadirFavorito(selectedLiga);
                    }
                }
            });
        }

        public void bind(Liga liga) {
            Glide.with(context)
                    .load(liga.getUrl())
                    .into(ligaView);
            nameText.setText(liga.getNombre());
        }
    }
    private void anadirFavorito(Liga liga) {

        Favorito favorito = new Favorito(liga.getNombre(), idCliente, liga.getId());

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        CRUDinterface crudInterface = retrofit.create(CRUDinterface.class);


        Call<Favorito> call = crudInterface.insertDataFavorito(favorito);
        call.enqueue(new Callback<Favorito>() {
            @Override
            public void onResponse(Call<Favorito> call, Response<Favorito> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Liga añadida a favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al añadir a favoritos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Favorito> call, Throwable t) {
                Toast.makeText(context, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

