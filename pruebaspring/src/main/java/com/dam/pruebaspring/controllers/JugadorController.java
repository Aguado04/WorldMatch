package com.dam.pruebaspring.controllers;

import com.dam.pruebaspring.models.Equipo;
import com.dam.pruebaspring.models.Jugador;
import com.dam.pruebaspring.models.Liga;
import com.dam.pruebaspring.models.Usuario;
import com.dam.pruebaspring.servicies.EquipoService;
import com.dam.pruebaspring.servicies.JugadorService;
import com.dam.pruebaspring.servicies.LigaService;
import com.dam.pruebaspring.servicies.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jugador")
public class JugadorController {
    @Autowired
    private JugadorService jugadorService;

    @GetMapping("/all")
    public List<Jugador> getAllJugadores() {
        return jugadorService.getAllJugadores();
    }

    @GetMapping("/by-id/{id}")
    public Jugador getJugador(@PathVariable Integer id) {
        return jugadorService.getJugadorById(id);
    }

    @PostMapping("/save")
    public Jugador saveJugador(@RequestBody Jugador jugador){
        return jugadorService.saveJugador(jugador);
    }
}
