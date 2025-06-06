package com.example.HBES.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HBES.model.Producto;
import java.util.List;
import java.util.Optional;

@Repository
public interface productoRepository extends JpaRepository<Producto, Integer>{
        @Query("SELECT p FROM Producto p JOIN FETCH p.usuario JOIN FETCH p.tienda WHERE p.id = :id")
    Optional<Producto> findByIdWithRelations(@Param("id") Long id);
    List<Producto> findByNombre(String nombre);
    List<Producto> findByCategoria(String categoria);
}
