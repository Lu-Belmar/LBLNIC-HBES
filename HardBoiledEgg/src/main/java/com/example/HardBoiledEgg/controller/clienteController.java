package com.example.HardBoiledEgg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.HardBoiledEgg.model.Cliente;
import com.example.HardBoiledEgg.service.clienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("api/cliente")
@Tag(name = "Gestión de Clientes", description = "API para operar con los datos de los clientes.")
public class clienteController {
    @Autowired
    private clienteService clienteService;

    @Operation(summary = "Listar todos los clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay clientes en el sistema")
    })
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.getCliente();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Buscar un cliente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "El cliente con ese ID no existe")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarClienteById(@Parameter(description = "ID del cliente a buscar") @PathVariable int id) {
        if (clienteService.getClienteById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Cliente");
        } else {
            return ResponseEntity.ok(clienteService.getClienteById(id));
        }
    }

    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponse(responseCode = "200", description = "Cliente creado exitosamente")
    @PostMapping("/add")
    public ResponseEntity<Cliente> creaCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.createCliente(cliente));
    }

    @Operation(summary = "Actualizar un cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
        @ApiResponse(responseCode = "404", description = "El cliente a actualizar no fue encontrado")
    })
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarCliente(@Parameter(description = "ID del cliente a actualizar") @PathVariable int id, @RequestBody Cliente cliente) {
        if (clienteService.updateCliente(cliente, id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Cliente para actualizarla");
        } else {
            return ResponseEntity.ok(clienteService.getClienteById(id));
        }
    }

    @Operation(summary = "Eliminar un cliente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró el cliente a eliminar")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarCliente(@Parameter(description = "ID del cliente a eliminar") @PathVariable int id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}