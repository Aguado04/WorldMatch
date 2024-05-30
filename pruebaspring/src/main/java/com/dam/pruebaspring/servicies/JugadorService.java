package com.dam.pruebaspring.servicies;

import com.dam.pruebaspring.models.Equipo;
import com.dam.pruebaspring.models.Jugador;
import com.dam.pruebaspring.models.Usuario;
import com.dam.pruebaspring.repositories.EquipoRepository;
import com.dam.pruebaspring.repositories.JugadorRepository;
import com.dam.pruebaspring.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JugadorService {
    @Autowired
    private JugadorRepository jugadorRepository;

    public List<Jugador> getAllJugadores() {
        return jugadorRepository.findAll();
    }

    public Jugador getJugadorById(Integer id) {
        return jugadorRepository.findById(id).get();
    }

    public Jugador saveJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public Jugador updateJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }
    public  Boolean deleteJugador(Integer id) {
        jugadorRepository.deleteById(id);
        return jugadorRepository.findById(id).isEmpty();
    }
}
