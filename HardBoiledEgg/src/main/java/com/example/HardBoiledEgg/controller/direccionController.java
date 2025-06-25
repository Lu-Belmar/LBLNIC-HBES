package com.example.HardBoiledEgg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.HardBoiledEgg.model.Direccion;
import com.example.HardBoiledEgg.model.DireccionCliente;
import com.example.HardBoiledEgg.service.direccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("api/direccion")
@Tag(name = "Gestión de Direcciones", description = "API para operar con las direcciones de clientes, empleados y tiendas.")
public class direccionController {
    @Autowired
    private direccionService direccionService;

    @Operation(summary = "Listar todas las direcciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de direcciones encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay direcciones en el sistema")
    })
    @GetMapping
    public ResponseEntity<List<Direccion>> listarDirecciones() {
        List<Direccion> direcciones = direccionService.getDireccion();
        if (direcciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(direcciones);
    }

    @Operation(summary = "Buscar una dirección por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección encontrada"),
        @ApiResponse(responseCode = "404", description = "La dirección con ese ID no existe")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarDireccionById(@Parameter(description = "ID de la dirección a buscar") @PathVariable int id) {
        if (direccionService.getDireccionById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la Direccion");
        } else {
            return ResponseEntity.ok(direccionService.getDireccionById(id));
        }
    }

    @Operation(summary = "Crear una nueva dirección (Genérica - Usar endpoints específicos)")
    @ApiResponse(responseCode = "200", description = "Dirección creada exitosamente")
    @PostMapping("/add")
    public ResponseEntity<Direccion> creaDireccion(@RequestBody Direccion direccion) {
        return ResponseEntity.ok(direccionService.createDireccion(direccion));
    }

    @Operation(summary = "Actualizar una dirección existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección actualizada"),
        @ApiResponse(responseCode = "404", description = "La dirección a actualizar no fue encontrada")
    })
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizar_(@Parameter(description = "ID de la dirección a actualizar") @PathVariable int id, @RequestBody Direccion direccion) {
        if (direccionService.updateDireccion(direccion, id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la Direccion para actualizarla");
        } else {
            return ResponseEntity.ok(direccionService.getDireccionById(id));
        }
    }

    @Operation(summary = "Eliminar una dirección por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Dirección eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró la dirección a eliminar")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrar_(@Parameter(description = "ID de la dirección a eliminar") @PathVariable int id) {
        try {
            direccionService.deleteDireccion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear una dirección y asociarla a un cliente")
    @ApiResponse(responseCode = "200", description = "Dirección de cliente creada y asociada")
    @PostMapping("/clientes")
    public DireccionCliente createDireccionCliente(
        @RequestBody DireccionCliente direccion,
        @Parameter(description = "ID del cliente al que se asociará la dirección") @RequestParam Integer clienteId) {
        return direccionService.crearDireccionCliente(direccion, clienteId);
    }

    @Operation(summary = "Obtener todas las direcciones de tipo cliente")
    @ApiResponse(responseCode = "200", description = "Lista de direcciones de clientes")
    @GetMapping("/clientes")
    public List<DireccionCliente> getAllDireccionesCliente() {
        return direccionService.findAllDireccionesCliente();
    }
}