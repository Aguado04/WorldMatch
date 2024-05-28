package com.example.worldmatch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldmatch.Equipos;
import com.example.worldmatch.Jugadores;
import com.example.worldmatch.R;
import com.example.worldmatch.model.Equipo;
import com.example.worldmatch.model.Liga;

import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {
    private List<Equipo> equipos;
    private Context context;

    public EquipoAdapter(List<Equipo> equipos, Context context) {
        this.equipos = equipos;
        this.context = context;
    }

    @NonNull
    @Override
    public EquipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.equipos_list, parent, false);
        return new EquipoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipoViewHolder holder, int position) {
        Equipo equipo = equipos.get(position);
        holder.bind(equipo);
    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public class EquipoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreText;
        Button jugadorButton;

        public EquipoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreText = itemView.findViewById(R.id.nombreEquipo);
            jugadorButton = itemView.findViewById(R.id.info);
            jugadorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Equipo selectedEquipo = equipos.get(position);
                        Intent intent = new Intent(context, Jugadores.class);
                        intent.putExtra("EquipoNombre", selectedEquipo.getNombre());
                        intent.putExtra("EquipoId", selectedEquipo.getId());
                        context.startActivity(intent);
                    }
                }
            });
        }

            public void bind (Equipo equipo){
                nombreText.setText(equipo.getNombre());
            }
        }
    }
