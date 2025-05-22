package com.example.HBES.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HBES.model.ventaModel;


@Repository
public interface tiendaRepository extends JpaRepository<ventaModel, Integer>{

}
