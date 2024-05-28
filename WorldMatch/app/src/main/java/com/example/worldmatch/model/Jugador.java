package com.example.worldmatch.model;

import com.google.gson.annotations.SerializedName;

public class Jugador {

    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("edad")
    private int edad;

    @SerializedName("dorsal")
    private int dorsal;
    @SerializedName("id_equipo_jugador")
    private int idEquipoJugador;

    public Jugador(String nombre, int edad, int dorsal, int idEquipoJugador) {
        this.nombre = nombre;
        this.edad = edad;
        this.dorsal = dorsal;
        this.idEquipoJugador = idEquipoJugador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public int getIdEquipoJugador() {
        return idEquipoJugador;
    }

    public void setIdEquipoJugador(int idEquipoJugador) {
        this.idEquipoJugador = idEquipoJugador;
    }
}