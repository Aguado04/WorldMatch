package com.example.worldmatch.interfaz;

import com.example.worldmatch.model.Cliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CRUDinterface {

    @POST("usuario/save")
    Call<Cliente> insertData(@Body Cliente cliente);

    @GET("usuario/all")
    Call<List<Cliente>> getAllClientes();
}
