package com.example.HardBoiledEgg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.HardBoiledEgg.model.InventarioTienda;
import com.example.HardBoiledEgg.service.inventariotiendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("api/inventariotienda")
@Tag(name = "Gestión de Inventario de Tiendas", description = "API para operar con el stock de productos en cada tienda.")
public class inventariotiendaController {

    @Autowired
    private inventariotiendaService inventariotiendaservice;

    @Operation(summary = "Listar todos los registros de inventario de todas las tiendas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de inventarios encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay inventarios en el sistema")
    })
    @GetMapping
    public ResponseEntity<List<InventarioTienda>> listarInventarioTienda() {
        List<InventarioTienda> inventarioTienda = inventariotiendaservice.getInventarioTienda();
        if (inventarioTienda.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inventarioTienda);
    }

    @Operation(summary = "Buscar un registro de inventario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
        @ApiResponse(responseCode = "404", description = "El registro de inventario con ese ID no existe")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarInventarioTiendaById(@Parameter(description = "ID del registro de inventario") @PathVariable int id) {
        if (inventariotiendaservice.getInventarioTiendaById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Inventario");
        } else {
            return ResponseEntity.ok(inventariotiendaservice.getInventarioTiendaById(id));
        }
    }

    @Operation(summary = "Buscar todos los registros de inventario para un producto específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventarios encontrados para el producto"),
        @ApiResponse(responseCode = "404", description = "No se encontraron registros de inventario para ese producto")
    })
    @GetMapping("/getByProducto/{id}")
    public ResponseEntity<?> buscarInventarioTiendaByProducto(@Parameter(description = "ID del producto a buscar en los inventarios") @PathVariable int id) {
        List<InventarioTienda> inventarios = inventariotiendaservice.getInventarioTiendaByProducto(id);
        if (inventarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se existe este producto en ningún Inventario");
        }
        return ResponseEntity.ok(inventarios);
    }

    @Operation(summary = "Crear un nuevo registro de inventario")
    @ApiResponse(responseCode = "200", description = "Registro de inventario creado exitosamente")
    @PostMapping("/add")
    public ResponseEntity<InventarioTienda> creaInventarioTienda(@RequestBody InventarioTienda inventariotienda) {
        return ResponseEntity.ok(inventariotiendaservice.createInventarioTienda(inventariotienda));
    }

    @Operation(summary = "Actualizar un registro de inventario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro de inventario actualizado"),
        @ApiResponse(responseCode = "404", description = "El registro de inventario a actualizar no fue encontrado")
    })
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarInventarioTienda(@Parameter(description = "ID del registro de inventario a actualizar") @PathVariable int id, @RequestBody InventarioTienda inventariotienda) {
        if (inventariotiendaservice.updateInventarioTienda(inventariotienda, id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el registro para actualizarlo");
        } else {
            return ResponseEntity.ok(inventariotiendaservice.getInventarioTiendaById(id));
        }
    }

    @Operation(summary = "Eliminar un registro de inventario por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Registro de inventario eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró el registro a eliminar")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarInventarioTienda(@Parameter(description = "ID del registro de inventario a eliminar") @PathVariable int id) {
        try {
            inventariotiendaservice.deleteInventarioTienda(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
