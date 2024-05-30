package com.example.worldmatch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldmatch.R;
import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Favorito;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.worldmatch.direcciones.Direccion.BASE_URL;

public class FavoritoAdapter extends RecyclerView.Adapter<FavoritoAdapter.FavoritoViewHolder> {
    private List<Favorito> favoritos;
    private Context context;

    public FavoritoAdapter(List<Favorito> favoritos, Context context) {
        this.favoritos = favoritos;
        this.context = context;
    }

    public void setFavoritos(List<Favorito> favoritos) {
        this.favoritos = favoritos;
    }

    @NonNull
    @Override
    public FavoritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favoritos_list, parent, false);
        return new FavoritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritoViewHolder holder, int position) {
        Favorito favorito = favoritos.get(position);
        holder.bind(favorito);
    }

    @Override
    public int getItemCount() {
        return favoritos.size();
    }

    public class FavoritoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreFavorito;
        Button borrarFavorito;

        public FavoritoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreFavorito = itemView.findViewById(R.id.nombreFavorito);
            borrarFavorito = itemView.findViewById(R.id.borrarFavorito);

            borrarFavorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Favorito selectedFavorito = favoritos.get(position);
                        deleteFavorito(selectedFavorito, position);
                    }
                }
            });
        }

        public void bind(Favorito favorito) {
            nombreFavorito.setText(favorito.getNombre());
        }

        private void deleteFavorito(Favorito favorito, int position) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            CRUDinterface crudInterface = retrofit.create(CRUDinterface.class);

            Call<Boolean> call = crudInterface.deleteDataFavorito(favorito.getId());
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()) {
                        // Eliminar el favorito de la lista y notificar al adaptador
                        favoritos.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, favoritos.size());
                        Toast.makeText(context, "Favorito eliminado correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error al eliminar favorito", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(context, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}