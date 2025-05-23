package com.example.HBES.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HBES.model.Envio;


@Repository
public interface envioRepository extends JpaRepository<Envio, Integer>{

}
