package com.dam.pruebaspring.repositories;

import com.dam.pruebaspring.models.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {

}