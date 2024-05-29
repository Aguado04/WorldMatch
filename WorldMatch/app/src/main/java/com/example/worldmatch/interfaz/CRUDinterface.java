package com.example.worldmatch.interfaz;

import com.example.worldmatch.model.Cliente;
import com.example.worldmatch.model.Equipo;
import com.example.worldmatch.model.Jugador;
import com.example.worldmatch.model.Liga;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CRUDinterface {

    @POST("usuario/save")
    Call<Cliente> insertData(@Body Cliente cliente);

    @GET("usuario/all")
    Call<List<Cliente>> getAllClientes();

    @GET("liga/all")
    Call<List<Liga>> getAllLigas();

    @POST("liga/save")
    Call<Liga> insertDataLiga(@Body Liga liga);

    @GET("equipo/all")
    Call<List<Equipo>> getAllEquipos();

    @POST("equipo/save")
    Call<Equipo> insertDataEquipo(@Body Equipo equipo);

    @GET("jugador/all")
    Call<List<Jugador>> getAllJugadores();

    @POST("jugador/save")
    Call<Jugador> insertDataJugador(@Body Jugador jugador);

    @PUT("jugador/update")
    Call<Jugador> updateDataJugador(@Body Jugador jugador);

    @DELETE("jugador/delete/{id}")
    Call<Boolean> deleteDataJugador(@Path("id") Integer id);

}
