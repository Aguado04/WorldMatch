package com.example.worldmatch.model;

import com.google.gson.annotations.SerializedName;

public class Equipo {

    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("id_liga_equipo")
    private int idLigaEquipo;

    public Equipo(String nombre, int idLigaEquipo) {
        this.nombre = nombre;
        this.idLigaEquipo = idLigaEquipo;
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

    public int getIdLigaEquipo() {
        return idLigaEquipo;
    }

    public void setIdLigaEquipo(int idLigaEquipo) {
        this.idLigaEquipo = idLigaEquipo;
    }
}