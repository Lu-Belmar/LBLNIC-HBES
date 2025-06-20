package com.example.HardBoiledEgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HardBoiledEgg.model.Envio;

@Repository
public interface envioRepository extends JpaRepository<Envio, Integer> {

    
}
