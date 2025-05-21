package com.example.HBES.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "tienda")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class tiendaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (unique = true , length = 60, nullable = false)
    private String ubicacion;

    @Column (unique = false, length = 40, nullable = false)
    private String nombre;

    @Column (unique = false, length = 50, nullable = false)
    private String correo;

    @Column (unique = false, length = 60, nullable = false)
    private String direccion;

    @Column (unique = false, length = 11, nullable = false)
    private String telefono;
}


