package com.example.HBES.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HBES.model.Producto;
import com.example.HBES.model.Usuario;
import java.util.List;
import java.util.Optional;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.productos WHERE u.id = :usuarioId")
    Optional<Usuario> findUsuarioWithProductos(@Param("usuarioId") Long usuarioId);
   List<Usuario> findByNombre(String nombre);

}
