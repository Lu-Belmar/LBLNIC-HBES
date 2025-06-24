package com.example.HardBoiledEgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HardBoiledEgg.model.InventarioTienda;
import com.example.HardBoiledEgg.model.Venta;

@Repository
public interface ventaRepository extends JpaRepository<Venta, Integer>{

    boolean existsByInventario(InventarioTienda inventariotienda);
}