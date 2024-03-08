package com.dam.pruebaspring.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Usuario {
    @Column(name = "Id_cliente")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Nombre_cliente")
    private String nombre;
    @Column(name = "Contrasena_cliente")
    private String contrasena;
    @Column(name = "Email_cliente")
    private String email;

}
