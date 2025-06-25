package com.example.HardBoiledEgg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.HardBoiledEgg.model.Categorias;
import com.example.HardBoiledEgg.service.categoriasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("api/categoria")
@Tag(name = "Gestión de Categorías", description = "API para operar con las categorías de productos.")
public class categoriasController {

    @Autowired
    private categoriasService categoriasService;

    @Operation(summary = "Listar todas las categorías")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de categorías encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay categorías en el sistema")
    })
    @GetMapping
    public ResponseEntity<List<Categorias>> listarCategorias() {
        List<Categorias> categorias = categoriasService.getCategorias();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "Buscar una categoría por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "La categoría con ese ID no existe")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarCategoriaById(@Parameter(description = "ID de la categoría a buscar") @PathVariable int id) {
        Categorias categoria = categoriasService.getCategoriasById(id);
        if (categoria == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la categoria");
        }
        return ResponseEntity.ok(categoria);
    }

    @Operation(summary = "Crear una nueva categoría")
    @ApiResponse(responseCode = "200", description = "Categoría creada exitosamente")
    @PostMapping("/add")
    public ResponseEntity<Categorias> creaCategorias(@RequestBody Categorias categoria) {
        return ResponseEntity.ok(categoriasService.createCategorias(categoria));
    }

    @Operation(summary = "Actualizar una categoría existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada"),
        @ApiResponse(responseCode = "404", description = "La categoría a actualizar no fue encontrada")
    })
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarCategorias(@Parameter(description = "ID de la categoría a actualizar") @PathVariable int id, @RequestBody Categorias categoria) {
        Categorias actualizado = categoriasService.updateCategorias(categoria, id);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la categoria para actualizarla");
        }
        return ResponseEntity.ok(categoriasService.getCategoriasById(id));
    }

    @Operation(summary = "Eliminar una categoría por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró la categoría a eliminar")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarCategorias(@Parameter(description = "ID de la categoría a eliminar") @PathVariable int id) {
        try {
            categoriasService.deleteCategorias(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
