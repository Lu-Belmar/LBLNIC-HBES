package com.example.HBES.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "producto" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class productoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_producto;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private int ubicacion;

    @Column(nullable = false)
    private int precio;

    @Column(nullable = false)
    private String categoria;


}
