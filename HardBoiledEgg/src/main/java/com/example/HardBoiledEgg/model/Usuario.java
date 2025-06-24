package com.example.HardBoiledEgg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 10, nullable = false)
    private Integer run;

    @Column(unique = false, length = 40, nullable = false)
    private String nombre;

    @Column(unique = true, length = 50, nullable = false)
    private String correo;

    @Column(unique = true, length = 11, nullable = true)
    private Integer telefono;


}
