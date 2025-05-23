package com.example.HBES.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HBES.model.productoModel;
import java.util.List;

@Repository
public interface productoRepository extends JpaRepository<productoModel, Integer>{

    List<productoModel> findByNombre(String nombre);
    List<productoModel> findByCategoria(String categoria);
}
