package com.example.HardBoiledEgg.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("EMPLEADO")
public class DireccionEmpleado extends Direccion {
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "direccion")
    private Empleado empleado;
}
