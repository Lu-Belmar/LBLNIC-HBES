package com.example.HardBoiledEgg.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("EMPLEADO")
public class DireccionEmpleado extends Direccion {
    @OneToOne(mappedBy = "direccion")
    private Empleado empleado;
}
