package com.example.HardBoiledEgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HardBoiledEgg.model.InventarioTienda;

@Repository
public interface inventariotiendaRepository extends JpaRepository<InventarioTienda, Integer> {
    
}