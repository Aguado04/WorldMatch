package com.dam.pruebaspring.servicies;

import com.dam.pruebaspring.models.Equipo;
import com.dam.pruebaspring.models.Favorito;
import com.dam.pruebaspring.models.Usuario;
import com.dam.pruebaspring.repositories.EquipoRepository;
import com.dam.pruebaspring.repositories.FavoritoRepository;
import com.dam.pruebaspring.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritoService {
    @Autowired
    private FavoritoRepository favoritoRepository;

    public List<Favorito> getAllFavoritos() {
        return favoritoRepository.findAll();
    }

    public Favorito getFavoritoById(Integer id) {
        return favoritoRepository.findById(id).get();
    }

    public Favorito saveFavorito(Favorito favorito) {
        return favoritoRepository.save(favorito);
    }

    public  Boolean deleteFavorito(Integer id) {
        favoritoRepository.deleteById(id);
        return favoritoRepository.findById(id).isEmpty();
    }



}
