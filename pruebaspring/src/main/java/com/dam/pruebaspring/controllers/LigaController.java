package com.dam.pruebaspring.controllers;

import com.dam.pruebaspring.models.Liga;
import com.dam.pruebaspring.models.Usuario;
import com.dam.pruebaspring.servicies.LigaService;
import com.dam.pruebaspring.servicies.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/liga")
public class LigaController {
    @Autowired
    private LigaService ligaService;

    @GetMapping("/all")
    public List<Liga> getAllLigas() {
        return ligaService.getAllLigas();
    }

    @GetMapping("/by-id/{id}")
    public Liga getLiga(@PathVariable Integer id) {
        return ligaService.getLigaById(id);
    }

    @PostMapping("/save")
    public Liga saveLiga(@RequestBody Liga liga){
        return ligaService.saveLiga(liga);
    }
}
