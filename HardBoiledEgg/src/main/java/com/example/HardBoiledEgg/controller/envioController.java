package com.example.HardBoiledEgg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.HardBoiledEgg.model.Envio;
import com.example.HardBoiledEgg.service.envioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("api/envio")
@Tag(name = "Gestión de Envíos", description = "API para operar con los envíos de las ventas.")
public class envioController {
    @Autowired
    private envioService envioService;

    @Operation(summary = "Listar todos los envíos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de envíos encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay envíos en el sistema")
    })
    @GetMapping
    public ResponseEntity<List<Envio>> listarEnvios() {
        List<Envio> envios = envioService.getEnvios();
        if (envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(envios);
    }

    @Operation(summary = "Buscar un envío por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío encontrado"),
        @ApiResponse(responseCode = "404", description = "El envío con ese ID no existe")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscar_ById(@Parameter(description = "ID del envío a buscar") @PathVariable int id) {
        if (envioService.getEnvioById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el envío");
        } else {
            return ResponseEntity.ok(envioService.getEnvioById(id));
        }
    }

    @Operation(summary = "Crear un nuevo envío")
    @ApiResponse(responseCode = "200", description = "Envío creado exitosamente")
    @PostMapping("/add")
    public ResponseEntity<Envio> creaVenta(@RequestBody Envio envio) {
        return ResponseEntity.ok(envioService.createEnvio(envio));
    }

    @Operation(summary = "Actualizar un envío existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío actualizado"),
        @ApiResponse(responseCode = "404", description = "El envío a actualizar no fue encontrado")
    })
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarEnvio(@Parameter(description = "ID del envío a actualizar") @PathVariable int id, @RequestBody Envio envio) {
        if (envioService.updateEnvio(envio, id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el envío para actualizarlo");
        } else {
            return ResponseEntity.ok(envioService.getEnvioById(id));
        }
    }

    @Operation(summary = "Eliminar un envío por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Envío eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró el envío a eliminar")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrar_(@Parameter(description = "ID del envío a eliminar") @PathVariable int id) {
        try {
            envioService.deleteEnvio(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}