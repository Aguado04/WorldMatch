package com.dam.pruebaspring.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipo")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Equipo {
    @Column(name = "Id_equipo")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Nombre_equipo")
    private String nombre;
    @Column(name = "Id_liga_equipo")
    private Integer id_liga_equipo;

}
