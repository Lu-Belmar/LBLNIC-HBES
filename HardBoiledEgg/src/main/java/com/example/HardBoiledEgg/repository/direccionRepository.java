package com.example.HardBoiledEgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HardBoiledEgg.model.Direccion;
import com.example.HardBoiledEgg.model.DireccionCliente;
import com.example.HardBoiledEgg.model.DireccionEmpleado;
import com.example.HardBoiledEgg.model.DireccionTienda;

@Repository
public interface direccionRepository extends JpaRepository<Direccion, Integer> {

    @Query("SELECT d FROM DireccionCliente d")
    List<DireccionCliente> findAllByTipoEntidadCliente();
    
    @Query("SELECT d FROM DireccionEmpleado d")
    List<DireccionEmpleado> findAllByTipoEntidadEmpleado();
    
    @Query("SELECT d FROM DireccionTienda d")
    List<DireccionTienda> findAllByTipoEntidadTienda();
    
    // Alternativa usando TYPE de JPA
    @Query("SELECT d FROM Direccion d WHERE TYPE(d) = DireccionCliente")
    List<DireccionCliente> findAllDireccionesCliente();    
}
