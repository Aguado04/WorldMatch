package com.dam.pruebaspring.repositories;

import com.dam.pruebaspring.models.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Integer> {

}