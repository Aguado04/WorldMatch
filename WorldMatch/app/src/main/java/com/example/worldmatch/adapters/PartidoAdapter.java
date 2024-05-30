package com.example.worldmatch.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldmatch.R;
import com.example.worldmatch.model.Partido;

import java.util.List;

public class PartidoAdapter extends RecyclerView.Adapter<PartidoAdapter.PartidoViewHolder> {

    private List<Partido> partidos;

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    @NonNull
    @Override
    public PartidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partidos_list, parent, false);
        return new PartidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartidoViewHolder holder, int position) {
        Partido partido = partidos.get(position);
        holder.bind(partido);
    }

    @Override
    public int getItemCount() {
        return partidos != null ? partidos.size() : 0;
    }

    static class PartidoViewHolder extends RecyclerView.ViewHolder {

        TextView equipoLocalTextView;
        TextView equipoVisitanteTextView;
        TextView golesLocalTextView;
        TextView golesVisitanteTextView;

        PartidoViewHolder(@NonNull View itemView) {
            super(itemView);
            equipoLocalTextView = itemView.findViewById(R.id.equipoLocal);
            equipoVisitanteTextView = itemView.findViewById(R.id.equipoVisitante);
            golesLocalTextView = itemView.findViewById(R.id.golesLocal);
            golesVisitanteTextView = itemView.findViewById(R.id.golesVisitante);
        }

        void bind(Partido partido) {
            equipoLocalTextView.setText(partido.getEquipoLocal().getNombre());
            equipoVisitanteTextView.setText(partido.getEquipoVisitante().getNombre());
            golesLocalTextView.setText(String.valueOf(partido.getGolesLocal()));
            golesVisitanteTextView.setText(String.valueOf(partido.getGolesVisitante()));
        }
    }
}