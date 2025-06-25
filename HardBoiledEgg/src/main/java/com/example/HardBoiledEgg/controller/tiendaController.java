package com.example.HardBoiledEgg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.HardBoiledEgg.model.Tienda;
import com.example.HardBoiledEgg.service.tiendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/tienda")
@Tag(name = "Gestión de Tiendas", description = "API para operar con las sucursales físicas.")
public class tiendaController {

    @Autowired
    private tiendaService tiendaService;

    @Operation(summary = "Listar todas las tiendas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tiendas encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay tiendas en el sistema")
    })
    @GetMapping
    public ResponseEntity<List<Tienda>> listarTiendas() {
        List<Tienda> tienda = tiendaService.getTienda();
        if (tienda.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(tienda);
        }
    }

    @Operation(summary = "Buscar una tienda por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tienda encontrada"),
        @ApiResponse(responseCode = "404", description = "La tienda con ese ID no existe")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarTiendaById(@Parameter(description = "ID de la tienda a buscar") @PathVariable int id) {
        if (tiendaService.getTiendaById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la tienda");
        } else {
            return ResponseEntity.ok(tiendaService.getTiendaById(id));
        }
    }

    @Operation(summary = "Crear una nueva tienda")
    @ApiResponse(responseCode = "200", description = "Tienda creada exitosamente")
    @PostMapping("/add")
    public ResponseEntity<Tienda> createTienda(@RequestBody Tienda tienda) {
        return ResponseEntity.ok(tiendaService.createTienda(tienda));
    }

    @Operation(summary = "Actualizar una tienda existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tienda actualizada"),
        @ApiResponse(responseCode = "404", description = "La tienda a actualizar no fue encontrada")
    })
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarTeinda(@Parameter(description = "ID de la tienda a actualizar") @PathVariable int id, @RequestBody Tienda tienda) {
        if (tiendaService.updateTienda(tienda, id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la tienda para poder actualizarla");
        } else {
            return ResponseEntity.ok(tiendaService.getTiendaById(id));
        }
    }

    @Operation(summary = "Eliminar una tienda por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tienda eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró la tienda a eliminar")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarTienda(@Parameter(description = "ID de la tienda a eliminar") @PathVariable int id) {
        try {
            tiendaService.deleteTienda(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}