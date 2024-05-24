package com.dam.pruebaspring.servicies;

import com.dam.pruebaspring.models.Equipo;
import com.dam.pruebaspring.models.Usuario;
import com.dam.pruebaspring.repositories.EquipoRepository;
import com.dam.pruebaspring.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {
    @Autowired
    private EquipoRepository equipoRepository;

    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    public Equipo getEquipoById(Integer id) {
        return equipoRepository.findById(id).get();
    }

    public Equipo saveEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }
}
