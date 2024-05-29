package com.dam.pruebaspring.servicies;

import com.dam.pruebaspring.models.Usuario;
import com.dam.pruebaspring.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


}
