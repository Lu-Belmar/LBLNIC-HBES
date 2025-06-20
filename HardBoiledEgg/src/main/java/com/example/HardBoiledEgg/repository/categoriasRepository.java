package com.example.HardBoiledEgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HardBoiledEgg.model.Categorias;

@Repository
public interface categoriasRepository extends JpaRepository<Categorias, Integer>{

}
