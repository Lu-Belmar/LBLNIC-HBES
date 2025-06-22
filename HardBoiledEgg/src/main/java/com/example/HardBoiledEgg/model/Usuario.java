package com.example.HardBoiledEgg.model;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @Column(unique = false, length = 50, nullable = false)
    private String correo;

    @Column(unique = false, length = 60, nullable = false)
    private String direccion;

    @Column(unique = false, length = 11, nullable = true)
    private String telefono;


}
