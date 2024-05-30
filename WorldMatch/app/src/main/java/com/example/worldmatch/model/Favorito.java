package com.example.worldmatch.model;

import com.google.gson.annotations.SerializedName;

public class Favorito {

    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("id_usuario_favorito")
    private int idUsuarioFavorito;

    @SerializedName("id_liga_favorito")
    private int idLigaFavorito;

    public Favorito(String nombre, int idUsuarioFavorito, int idLigaFavorito) {
        this.nombre = nombre;
        this.idUsuarioFavorito = idUsuarioFavorito;
        this.idLigaFavorito = idLigaFavorito;
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

    public int getIdUsuarioFavorito() {
        return idUsuarioFavorito;
    }

    public void setIdUsuarioFavorito(int idUsuarioFavorito) {
        this.idUsuarioFavorito = idUsuarioFavorito;
    }

    public int getIdLigaFavorito() {
        return idLigaFavorito;
    }

    public void setIdLigaFavorito(int idLigaFavorito) {
        this.idLigaFavorito = idLigaFavorito;
    }
}
