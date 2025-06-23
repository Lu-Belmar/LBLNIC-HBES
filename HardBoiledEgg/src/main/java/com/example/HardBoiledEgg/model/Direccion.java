package com.example.HardBoiledEgg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import jakarta.persistence.OneToOne;

import lombok.Data;

@Entity
@Table(name = "Direccion")
@Data
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String calle;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String region;


    @Column(nullable = false)
    private Integer entidadId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entidad",nullable = false)
    private TipoEntidad tipoentidad;

    public enum TipoEntidad{
        CLIENTE, EMPLEADO, TIENDA
    }

    public void setCliente(Cliente cliente) {
        this.tipoentidad = TipoEntidad.CLIENTE;
        this.entidadId = cliente.getId();
    }

    public void setEmpleado(Empleado empleado) {
        this.tipoentidad = TipoEntidad.CLIENTE;
        this.entidadId = empleado.getId();
    }

    public void setTienda(Tienda tienda) {
        this.tipoentidad = TipoEntidad.CLIENTE;
        this.entidadId = tienda.getId();
    }

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @OneToOne
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;


}