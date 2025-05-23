package com.example.HBES.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name= "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class usuarioModel {

    ArrayList<productoModel>carroUsuario = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

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

    @OneToMany(mappedBy = "usuario")
    private List<productoModel> pedidos;

}