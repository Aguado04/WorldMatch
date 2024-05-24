package com.example.worldmatch.model;

import com.google.gson.annotations.SerializedName;

public class Equipo {
    @SerializedName("id")
    private int id;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("id_liga_equipo")
    private int id_liga_equipo;

    public Equipo(String nombre, int id_liga_equipo) {
        this.nombre = nombre;
        this.id_liga_equipo = id_liga_equipo;
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

    public int getId_liga_equipo() {
        return id_liga_equipo;
    }

    public void setId_liga_equipo(int id_liga_equipo) {
        this.id_liga_equipo = id_liga_equipo;
    }
}
