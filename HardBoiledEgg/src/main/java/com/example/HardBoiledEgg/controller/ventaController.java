package com.example.HardBoiledEgg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.HardBoiledEgg.model.Venta;
import com.example.HardBoiledEgg.service.ventaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/venta")
@Tag(name = "Gestión de Ventas", description = "API para operar con las ventas.")
public class ventaController {

    @Autowired
    private ventaService ventaService;

    @Operation(summary = "Listar todas las ventas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ventas encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay ventas en el sistema")
    })
    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.getVentas();
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

    @Operation(summary = "Buscar una venta por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta encontrada"),
        @ApiResponse(responseCode = "404", description = "La venta con ese ID no existe")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarVentaById(@Parameter(description = "ID de la venta a buscar") @PathVariable int id) {
        if (ventaService.getVentaById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la venta");
        } else {
            return ResponseEntity.ok(ventaService.getVentaById(id));
        }
    }

    /*  Commented out in original code, so I'm not adding Swagger docs to it.
    @PostMapping("/add")
    public ResponseEntity<Venta> creaVenta(@RequestBody Venta venta){
        return ResponseEntity.ok(ventaService.createVenta(venta));
    }
    */

    @Operation(summary = "Actualizar una venta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta actualizada"),
        @ApiResponse(responseCode = "404", description = "La venta a actualizar no fue encontrada")
    })
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarVenta(@Parameter(description = "ID de la venta a actualizar") @PathVariable int id, @RequestBody Venta venta) {
        if (ventaService.updateVenta(venta, id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la venta para actualizarla");
        } else {
            return ResponseEntity.ok(ventaService.getVentaById(id));
        }
    }

    @Operation(summary = "Eliminar una venta por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venta eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró la venta a eliminar")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarVenta(@Parameter(description = "ID de la venta a eliminar") @PathVariable int id) {
        try {
            ventaService.deleteVenta(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}