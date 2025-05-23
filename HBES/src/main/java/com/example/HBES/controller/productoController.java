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

import com.example.HBES.model.productoModel;
import com.example.HBES.service.productoService;

@RestController
@RequestMapping("/api/producto")
public class productoController {

    @Autowired
    private productoService productoService;

    @GetMapping
    public ResponseEntity<List<productoModel>> listarProductos(){
        List<productoModel> productos = productoService.getProductos();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarProductoById(@PathVariable int id){
        if (productoService.getProductoById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el producto");
        } else {
            return ResponseEntity.ok(productoService.getProductoById(id)) ;
        }
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
    public ResponseEntity<?> actualizarProducto(@PathVariable int id,@RequestBody productoModel producto){
        if (productoService.updateProducto(producto,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el producto para actualizarlo");
        }else{
            return ResponseEntity.ok(producto);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<productoModel> crearProducto(@RequestBody productoModel producto){
        return ResponseEntity.ok(productoService.createProducto(producto));
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
