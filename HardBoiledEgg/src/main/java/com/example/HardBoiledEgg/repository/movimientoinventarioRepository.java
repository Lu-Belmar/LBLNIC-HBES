package com.example.HardBoiledEgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HardBoiledEgg.model.MovimientoInventario;

@Repository
public interface movimientoinventarioRepository extends JpaRepository<MovimientoInventario, Integer>{

    
}