package com.example.HardBoiledEgg.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "InventarioTienda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioTienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int precioLocal;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonManagedReference
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    @JsonBackReference
    private Tienda tienda;

    @OneToOne(mappedBy = "inventario")
    @JsonManagedReference
    private Venta venta;
}
