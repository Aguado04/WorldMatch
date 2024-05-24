package com.dam.pruebaspring.servicies;

import com.dam.pruebaspring.models.Liga;
import com.dam.pruebaspring.models.Usuario;
import com.dam.pruebaspring.repositories.LigaRepository;
import com.dam.pruebaspring.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigaService {
    @Autowired
    private LigaRepository ligaRepository;

    public List<Liga> getAllLigas() {
        return ligaRepository.findAll();
    }

    public Liga getLigaById(Integer id) {
        return ligaRepository.findById(id).get();
    }

    public Liga saveLiga(Liga liga) {
        return ligaRepository.save(liga);
    }
}
