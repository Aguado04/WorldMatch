package com.example.worldmatch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldmatch.R;
import com.example.worldmatch.model.Jugador;

import java.util.List;

public class JugadorAdapter extends RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder> {
    private List<Jugador> jugadores;
    private Context context;

    public JugadorAdapter(List<Jugador> jugadores, Context context) {
        this.jugadores = jugadores;
        this.context = context;
    }

    @NonNull
    @Override
    public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jugadores_list, parent, false);
        return new JugadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
        Jugador jugador = jugadores.get(position);
        holder.bind(jugador);
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    public static class JugadorViewHolder extends RecyclerView.ViewHolder {
        TextView nombreText;
        TextView edadText;
        TextView dorsalText;

        public JugadorViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreText = itemView.findViewById(R.id.Nombre);
            edadText = itemView.findViewById(R.id.Edad);
            dorsalText = itemView.findViewById(R.id.Dorsal);
        }

        public void bind(Jugador jugador) {
            nombreText.setText(jugador.getNombre());
            edadText.setText(String.valueOf(jugador.getEdad()));
            dorsalText.setText(String.valueOf(jugador.getDorsal()));
        }
    }
}