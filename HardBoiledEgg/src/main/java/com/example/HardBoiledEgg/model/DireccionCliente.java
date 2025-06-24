package com.example.HardBoiledEgg.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("CLIENTE")
public class DireccionCliente extends Direccion {
    @OneToOne(mappedBy = "direccion")
    private Cliente cliente;
}
