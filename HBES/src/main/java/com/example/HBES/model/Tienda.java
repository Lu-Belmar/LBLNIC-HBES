package com.example.HBES.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "tienda")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @Column (unique = false, length = 40, nullable = false)
    private String nombre;

    @Column (unique = false, length = 50, nullable = false)
    private String correo;

    @Column (unique = false, length = 60, nullable = false)
    private String direccion;

    @Column (unique = false, length = 11, nullable = false)
    private String telefono;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "tienda")
    @JsonIgnore
    private List<Producto>producto;
}


