package com.example.HBES.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HBES.model.Producto;
import com.example.HBES.service.productoService;

@RestController
@RequestMapping("/api/producto")
public class productoController {

    @Autowired
    private productoService productoService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<Producto> getProductoWithRelations(@PathVariable Long id) {
        Producto producto = productoService.getProductoWithRelations(id);
        return ResponseEntity.ok(producto);
    }
/* 
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarProductoById(@PathVariable int id){
        if (productoService.getProductoById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario");
        } else {
            return ResponseEntity.ok(productoService.getProductoById(id)) ;
        }
        
    }
*/
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(){
        List<Producto> productos = productoService.getProductos();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/getByName/{nombre}")
    public ResponseEntity<?> buscarProductoByNombre(@PathVariable String nombre){
        if (productoService.getProductoByNombre(nombre).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra producto con este nombre");           
        } else {
            return ResponseEntity.ok(productoService.getProductoByNombre(nombre)) ;
        }
    }

    @GetMapping("/getByCategoria/{categoria}")
    public ResponseEntity<?> buscarUsuarioByNombre(@PathVariable String categoria){
        if (productoService.getProductoByCategoria(categoria).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra producto en esta categoria");           
        } else {
            return ResponseEntity.ok(productoService.getProductoByCategoria(categoria)) ;
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable int id,@RequestBody Producto producto){
        if (productoService.updateProducto(producto,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el producto para actualizarlo");
        }else{
            return ResponseEntity.ok(producto);
        }
        
    }

    @PostMapping("/add")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto){
        productoService.createProducto(producto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarProducto(@PathVariable int id){
        try {
            productoService.deleteProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } 
    }

}
