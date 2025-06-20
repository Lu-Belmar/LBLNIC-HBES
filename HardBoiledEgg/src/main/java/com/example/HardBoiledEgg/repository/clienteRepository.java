package com.example.HardBoiledEgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HardBoiledEgg.model.Cliente;

@Repository
public interface clienteRepository extends JpaRepository<Cliente, Integer> {

    
}
