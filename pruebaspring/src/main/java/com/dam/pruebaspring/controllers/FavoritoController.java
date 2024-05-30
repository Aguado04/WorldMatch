package com.dam.pruebaspring.controllers;

import com.dam.pruebaspring.models.Equipo;
import com.dam.pruebaspring.models.Favorito;
import com.dam.pruebaspring.models.Liga;
import com.dam.pruebaspring.models.Usuario;
import com.dam.pruebaspring.servicies.EquipoService;
import com.dam.pruebaspring.servicies.FavoritoService;
import com.dam.pruebaspring.servicies.LigaService;
import com.dam.pruebaspring.servicies.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorito")
public class FavoritoController {
    @Autowired
    private FavoritoService favoritoService;

    @GetMapping("/all")
    public List<Favorito> getAllFavoritos() {
        return favoritoService.getAllFavoritos();
    }

    @GetMapping("/by-id/{id}")
    public Favorito getFavorito(@PathVariable Integer id) {
        return favoritoService.getFavoritoById(id);
    }

    @PostMapping("/save")
    public Favorito saveFavorito(@RequestBody Favorito favorito){
        return favoritoService.saveFavorito(favorito);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteFavorito(@PathVariable Integer id) {return favoritoService.deleteFavorito(id);}



}
