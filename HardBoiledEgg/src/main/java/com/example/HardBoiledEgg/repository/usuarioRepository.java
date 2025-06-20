package com.example.HardBoiledEgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HardBoiledEgg.model.Usuario;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario, Integer> {

    
}
