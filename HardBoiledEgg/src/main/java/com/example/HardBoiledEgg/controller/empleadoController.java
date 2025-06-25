package com.example.HardBoiledEgg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.HardBoiledEgg.model.Cliente;
import com.example.HardBoiledEgg.model.Empleado;
import com.example.HardBoiledEgg.service.empleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("api/empleado")
@Tag(name = "Gestión de Empleados", description = "API para operar con los datos de los empleados.")
public class empleadoController {
    @Autowired
    private empleadoService empleadoService;

    @Operation(summary = "Listar todos los empleados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empleados encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay empleados en el sistema")
    })
    @GetMapping
    public ResponseEntity<List<Empleado>> listar() {
        List<Empleado> empleados = empleadoService.getEmpleados();
        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empleados);
    }

    @Operation(summary = "Buscar un empleado por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
        @ApiResponse(responseCode = "404", description = "El empleado con ese ID no existe")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarEmpleadoById(@Parameter(description = "ID del empleado a buscar") @PathVariable int id) {
        if (empleadoService.getEmpleadoById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Empleado");
        } else {
            return ResponseEntity.ok(empleadoService.getEmpleadoById(id));
        }
    }

    @Operation(summary = "Crear un nuevo empleado")
    @ApiResponse(responseCode = "200", description = "Empleado creado exitosamente")
    @PostMapping("/add")
    public ResponseEntity<Empleado> creaVenta(@RequestBody Empleado empleado) {
        return ResponseEntity.ok(empleadoService.createEmpleado(empleado));
    }

    @Operation(summary = "Actualizar un empleado existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado actualizado"),
        @ApiResponse(responseCode = "404", description = "El empleado a actualizar no fue encontrado")
    })
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarEmpleado(@Parameter(description = "ID del empleado a actualizar") @PathVariable int id, @RequestBody Empleado empleado) {
        if (empleadoService.updateEmpleado(empleado, id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el empleado para actualizarlo");
        } else {
            return ResponseEntity.ok(empleadoService.getEmpleadoById(id));
        }
    }

    @Operation(summary = "Eliminar un empleado por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Empleado eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró el empleado a eliminar")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarEmpleado(@Parameter(description = "ID del empleado a eliminar") @PathVariable int id) {
        try {
            empleadoService.deleteEmpleado(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}