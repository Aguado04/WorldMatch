package com.dam.pruebaspring.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "liga")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Liga {
    @Column(name = "Id_liga")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Nombre_liga")
    private String nombre;
    @Column(name = "Url_liga")
    private String url;

}
