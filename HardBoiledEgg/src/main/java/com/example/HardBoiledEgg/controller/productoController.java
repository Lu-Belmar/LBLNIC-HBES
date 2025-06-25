package com.example.HardBoiledEgg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.HardBoiledEgg.model.Categorias;
import com.example.HardBoiledEgg.model.Producto;
import com.example.HardBoiledEgg.service.productoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/producto")
@Tag(name = "Gestión de Productos", description = "API para operar con los productos del catálogo.")
public class productoController {

    @Autowired
    private productoService productoService;

    @Operation(summary = "Buscar un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "El producto con ese ID no existe")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarProductoById(@Parameter(description = "ID del producto a buscar") @PathVariable int id) {
        if (productoService.getProductoById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el producto");
        } else {
            return ResponseEntity.ok(productoService.getProductoById(id));
        }
    }

    @Operation(summary = "Listar todos los productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay productos en el sistema")
    })
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.getProducto();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Buscar productos por nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados"),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos con ese nombre")
    })
    @GetMapping("/getByName/{nombre}")
    public ResponseEntity<?> buscarProductoByNombre(@Parameter(description = "Nombre (o parte del nombre) a buscar") @PathVariable String nombre) {
        if (productoService.getProductoByNombre(nombre).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra producto con este nombre");
        } else {
            return ResponseEntity.ok(productoService.getProductoByNombre(nombre));
        }
    }

    @Operation(summary = "Buscar productos por categoría")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados en la categoría"),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos en esa categoría")
    })
    @GetMapping("/getByCategoria/{categoria}")
    public ResponseEntity<?> buscarUsuarioByNombre(@Parameter(description = "Objeto Categoría por el cual filtrar") @PathVariable Categorias categoria) {
        if (productoService.getProductoByCategoria(categoria).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra producto en esta categoria");
        } else {
            return ResponseEntity.ok(productoService.getProductoByCategoria(categoria));
        }
    }

    @Operation(summary = "Actualizar un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado"),
        @ApiResponse(responseCode = "404", description = "El producto a actualizar no fue encontrado")
    })
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarProducto(@Parameter(description = "ID del producto a actualizar") @PathVariable int id, @RequestBody Producto producto) {
        if (productoService.updateProducto(producto, id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el producto para actualizarlo");
        } else {
            return ResponseEntity.ok(producto);
        }
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponse(responseCode = "200", description = "Producto creado exitosamente")
    @PostMapping("/add")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        productoService.createProducto(producto);
        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Eliminar un producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró el producto a eliminar")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarProducto(@Parameter(description = "ID del producto a eliminar") @PathVariable int id) {
        try {
            productoService.deleteProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}