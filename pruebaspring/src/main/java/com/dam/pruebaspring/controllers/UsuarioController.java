package com.dam.pruebaspring.controllers;

import com.dam.pruebaspring.models.Usuario;
import com.dam.pruebaspring.servicies.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/all")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/by-id/{id}")
    public Usuario getUsuario(@PathVariable Integer id) {
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping("/save")
    public Usuario saveUsuario(@RequestBody Usuario usuario){
        return usuarioService.saveUsuario(usuario);
    }
}
