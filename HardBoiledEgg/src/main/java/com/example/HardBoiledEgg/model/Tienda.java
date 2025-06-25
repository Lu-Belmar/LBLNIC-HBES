package com.example.HardBoiledEgg.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Tienda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer telefono;

    @Column(nullable = false)
    private String correo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    private DireccionTienda direccion;

    @OneToMany(mappedBy = "tienda")
    @JsonManagedReference
    private List<InventarioTienda> inventarios;

}
