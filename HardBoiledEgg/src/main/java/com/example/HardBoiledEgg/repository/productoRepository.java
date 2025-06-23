package com.example.HardBoiledEgg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HardBoiledEgg.model.Producto;

@Repository
public interface productoRepository extends JpaRepository<Producto, Integer>{

    List<Producto> findByNombre(String nombre);
    List<Producto> findByCategoria(String categoria);
}
