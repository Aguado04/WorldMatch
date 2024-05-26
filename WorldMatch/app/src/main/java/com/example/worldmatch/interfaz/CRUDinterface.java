package com.example.worldmatch.interfaz;

import com.example.worldmatch.model.Cliente;
import com.example.worldmatch.model.Equipo;
import com.example.worldmatch.model.Liga;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CRUDinterface {

    @POST("usuario/save")
    Call<Cliente> insertData(@Body Cliente cliente);

    @GET("usuario/all")
    Call<List<Cliente>> getAllClientes();

    @GET("liga/all")
    Call<List<Liga>> getAllLigas();

    @GET("equipo/all")
    Call<List<Equipo>> getAllEquipos();


}
