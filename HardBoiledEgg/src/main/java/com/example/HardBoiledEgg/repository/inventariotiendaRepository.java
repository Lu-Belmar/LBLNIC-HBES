package com.example.HardBoiledEgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HardBoiledEgg.model.InventarioTienda;
import com.example.HardBoiledEgg.model.Producto;

@Repository
public interface inventariotiendaRepository extends JpaRepository<InventarioTienda, Integer> {
    
        
    @Query("SELECT i FROM InventarioTienda i WHERE i.venta IS NULL")
    List<InventarioTienda> findByVentaIsNull();

    List<InventarioTienda> findByProducto(Producto producto);
}