package com.dam.pruebaspring.controllers;

import com.dam.pruebaspring.models.Equipo;
import com.dam.pruebaspring.models.Liga;
import com.dam.pruebaspring.models.Usuario;
import com.dam.pruebaspring.servicies.EquipoService;
import com.dam.pruebaspring.servicies.LigaService;
import com.dam.pruebaspring.servicies.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipo")
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    @GetMapping("/all")
    public List<Equipo> getAllEquipos() {
        return equipoService.getAllEquipos();
    }

    @GetMapping("/by-id/{id}")
    public Equipo getEquipo(@PathVariable Integer id) {
        return equipoService.getEquipoById(id);
    }

    @PostMapping("/save")
    public Equipo saveEquipo(@RequestBody Equipo equipo){
        return equipoService.saveEquipo(equipo);
    }


}
