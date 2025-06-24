package com.example.HardBoiledEgg.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("TIENDA")
public class DireccionTienda extends Direccion {
    @OneToOne(mappedBy = "direccion")
    private Tienda tienda;
}