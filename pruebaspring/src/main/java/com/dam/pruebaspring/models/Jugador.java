package com.dam.pruebaspring.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jugador")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Jugador {
    @Column(name = "Id_jugador")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Nombre_jugador")
    private String nombre;
    @Column(name = "Edad_jugador")
    private Integer edad;
    @Column(name = "Dorsal_jugador")
    private Integer dorsal;
    @Column(name = "Id_equipo_jugador")
    private Integer id_equipo_jugador;

}