package com.example.worldmatch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.worldmatch.Equipos;
import com.example.worldmatch.R;
import com.example.worldmatch.model.Liga;

import java.util.List;

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

        public LigaViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.NombreLiga);
            ligaView = itemView.findViewById(R.id.FotoLiga);
            equiposButton = itemView.findViewById(R.id.equipos);

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
        }

        public void bind(Liga liga) {
            Glide.with(context)
                    .load(liga.getUrl())
                    .into(ligaView);
            nameText.setText(liga.getNombre());
        }
    }
}