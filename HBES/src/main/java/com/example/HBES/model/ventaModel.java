package com.example.HBES.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "Venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ventaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pedido;


    @Column(nullable = false)
    private Integer monto;
}
