package com.example.HardBoiledEgg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.HardBoiledEgg.model.Proveedores;
import com.example.HardBoiledEgg.service.proveedoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("api/proveedores")
@Tag(name = "Gestión de Proveedores", description = "API para operar con los datos de los proveedores.")
public class proveedoresController {
    @Autowired
    private proveedoresService proveedoresService;

    @Operation(summary = "Listar todos los proveedores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de proveedores encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay proveedores en el sistema")
    })
    @GetMapping
    public ResponseEntity<List<Proveedores>> listarProveedores() {
        List<Proveedores> proveedores = proveedoresService.getProveedores();
        if (proveedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedores);
    }

    @Operation(summary = "Buscar un proveedor por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
        @ApiResponse(responseCode = "404", description = "El proveedor con ese ID no existe")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarProveedoresById(@Parameter(description = "ID del proveedor a buscar") @PathVariable int id) {
        if (proveedoresService.getProveedoresById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el proveedor");
        } else {
            return ResponseEntity.ok(proveedoresService.getProveedoresById(id));
        }
    }

    @Operation(summary = "Crear un nuevo proveedor")
    @ApiResponse(responseCode = "200", description = "Proveedor creado exitosamente")
    @PostMapping("/add")
    public ResponseEntity<Proveedores> creaVenta(@RequestBody Proveedores proveedores) {
        return ResponseEntity.ok(proveedoresService.createProveedores(proveedores));
    }

    @Operation(summary = "Actualizar un proveedor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor actualizado"),
        @ApiResponse(responseCode = "404", description = "El proveedor a actualizar no fue encontrado")
    })
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarProveedores(@Parameter(description = "ID del proveedor a actualizar") @PathVariable int id, @RequestBody Proveedores proveedores) {
        if (proveedoresService.updateProveedores(proveedores, id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el proveedor para actualizarlo");
        } else {
            return ResponseEntity.ok(proveedoresService.getProveedoresById(id));
        }
    }

    @Operation(summary = "Eliminar un proveedor por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Proveedor eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró el proveedor a eliminar")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarProveedores(@Parameter(description = "ID del proveedor a eliminar") @PathVariable int id) {
        try {
            proveedoresService.deleteProveedores(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}