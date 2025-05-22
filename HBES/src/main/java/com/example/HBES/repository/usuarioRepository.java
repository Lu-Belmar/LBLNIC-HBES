package com.example.HBES.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HBES.model.usuarioModel;


@Repository
public interface usuarioRepository extends JpaRepository<usuarioModel, Integer> {

   

}
