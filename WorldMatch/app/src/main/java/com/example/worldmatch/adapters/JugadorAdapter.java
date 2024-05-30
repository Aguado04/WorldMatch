package com.example.worldmatch.adapters;

import static com.example.worldmatch.Login.isAdmin;
import static com.example.worldmatch.direcciones.Direccion.BASE_URL;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldmatch.R;
import com.example.worldmatch.interfaz.CRUDinterface;
import com.example.worldmatch.model.Jugador;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JugadorAdapter extends RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder> {
    private List<Jugador> jugadores;
    private Context context;

    public JugadorAdapter(List<Jugador> jugadores, Context context) {
        this.jugadores = jugadores;
        this.context = context;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    @NonNull
    @Override
    public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jugadores_list, parent, false);
        return new JugadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadorViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Jugador jugador = jugadores.get(position);
        holder.bind(jugador);

        if(isAdmin == false){
            holder.actualizarJugador.setVisibility(View.GONE);
            holder.borrarJugador.setVisibility(View.GONE);
        }

        holder.actualizarJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateJugador(jugador, position);
            }
        });

        holder.borrarJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteJugador(jugador, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    public class JugadorViewHolder extends RecyclerView.ViewHolder {
        TextView nombreText;
        TextView edadText;
        TextView dorsalText;
        Button actualizarJugador;

        Button borrarJugador;

        public JugadorViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreText = itemView.findViewById(R.id.Nombre);
            edadText = itemView.findViewById(R.id.Edad);
            dorsalText = itemView.findViewById(R.id.Dorsal);
            actualizarJugador = itemView.findViewById(R.id.editarJugador);
            borrarJugador = itemView.findViewById(R.id.borrarJugador);
        }

        public void bind(Jugador jugador) {
            nombreText.setText(jugador.getNombre());
            edadText.setText(String.valueOf(jugador.getEdad()));
            dorsalText.setText(String.valueOf(jugador.getDorsal()));
        }
    }

    private void updateJugador(Jugador jugador, int position) {
        final EditText editText1 = new EditText(context);
        final EditText editText2 = new EditText(context);
        final EditText editText3 = new EditText(context);

        editText1.setHint("Nombre del jugador...");
        editText2.setHint("Edad del jugador...");
        editText3.setHint("Dorsal del jugador...");

        editText1.setText(jugador.getNombre());
        editText2.setText(String.valueOf(jugador.getEdad()));
        editText3.setText(String.valueOf(jugador.getDorsal()));

        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(ContextCompat.getColor(context, R.color.principal));
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(editText1);
        layout.addView(editText2);
        layout.addView(editText3);

        new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle)
                .setIcon(R.drawable.ic_logo)
                .setTitle("Actualizar Jugador")
                .setView(layout)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nombreJugador = editText1.getText().toString();
                        String edadJugadorString = editText2.getText().toString();
                        String dorsalJugadorString = editText3.getText().toString();

                        if (nombreJugador.isEmpty() || edadJugadorString.isEmpty() || dorsalJugadorString.isEmpty()) {
                            Toast.makeText(context, "Campos vacíos", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Integer edadJugador = Integer.valueOf(edadJugadorString);
                        Integer dorsalJugador = Integer.valueOf(dorsalJugadorString);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CRUDinterface crudInterface = retrofit.create(CRUDinterface.class);
                        jugador.setNombre(nombreJugador);
                        jugador.setEdad(edadJugador);
                        jugador.setDorsal(dorsalJugador);

                        Call<Jugador> call = crudInterface.updateDataJugador(jugador);
                        call.enqueue(new Callback<Jugador>() {
                            @Override
                            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Jugador actualizado correctamente", Toast.LENGTH_SHORT).show();
                                    jugadores.set(position, jugador);
                                    notifyItemChanged(position);
                                } else {
                                    Toast.makeText(context, "Error al actualizar jugador: " + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Jugador> call, Throwable t) {
                                Toast.makeText(context, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void deleteJugador(Jugador jugador, int position) {
        new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle)
                .setIcon(R.drawable.ic_logo)
                .setTitle("Eliminar Jugador")
                .setMessage("¿Estás seguro de que quieres eliminar este jugador?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CRUDinterface crudInterface = retrofit.create(CRUDinterface.class);

                        Call<Boolean> call = crudInterface.deleteDataJugador(jugador.getId());
                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if (response.isSuccessful() && response.body() != null && response.body()) {
                                    jugadores.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, jugadores.size());
                                    Toast.makeText(context, "Jugador eliminado correctamente", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error al eliminar jugador", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(context, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}