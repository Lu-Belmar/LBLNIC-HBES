package com.example.HBES.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HBES.model.Tienda;


@Repository
public interface tiendaRepository extends JpaRepository<Tienda, Integer>{

}
