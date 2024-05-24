package com.example.worldmatch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.worldmatch.R;
import com.example.worldmatch.model.Equipo;

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


        public EquipoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreText = itemView.findViewById(R.id.nombreEquipo);

        }

        public void bind(Equipo equipo) {
            nombreText.setText(equipo.getNombre());

        }
    }
}