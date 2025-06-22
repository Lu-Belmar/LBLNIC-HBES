package com.example.HardBoiledEgg.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "inventario")
    private List<Producto> productos;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;
}
