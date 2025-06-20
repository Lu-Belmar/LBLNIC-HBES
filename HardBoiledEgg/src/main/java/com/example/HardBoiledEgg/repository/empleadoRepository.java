package com.example.HardBoiledEgg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HardBoiledEgg.model.Empleado;

@Repository
public interface empleadoRepository extends JpaRepository<Empleado, Integer> {

}
