package com.dam.pruebaspring.repositories;

import com.dam.pruebaspring.models.Equipo;
import com.dam.pruebaspring.models.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

}