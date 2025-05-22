package com.example.HBES.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HBES.model.productoModel;


@Repository
public interface productoRepository extends JpaRepository<productoModel, Integer>{

}
