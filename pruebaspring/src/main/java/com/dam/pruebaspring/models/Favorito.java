package com.dam.pruebaspring.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorito")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Favorito {
    @Column(name = "Id_favorito")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Nombre_favorito")
    private String nombre;
    @Column(name = "Id_usuario_favorito")
    private Integer id_usuario_favorito;
    @Column(name = "Id_liga_favorito")
    private Integer id_liga_favorito;

}
