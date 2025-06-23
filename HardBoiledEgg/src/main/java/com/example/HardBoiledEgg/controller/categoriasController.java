package com.example.HardBoiledEgg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.HardBoiledEgg.model.Categorias;
import com.example.HardBoiledEgg.service.categoriasService;

public class categoriasController {

    @Autowired
    private categoriasService categoriasService ;

    @GetMapping
    public ResponseEntity<List<Categorias>> listarCategorias() {
        List<Categorias> categorias = categoriasService.getCategorias();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();    
        }
        return ResponseEntity.ok(categorias);
    }   

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarCategoriaById(@PathVariable int id){
        if (categoriasService.getCategoriasById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la categoria");
        } else {
            return ResponseEntity.ok(categoriasService.getCategoriasById(id)) ;
        }
    }
     
    @PostMapping("/add")
    public ResponseEntity<Categorias> creaCategorias(@RequestBody Categorias categoria){
        return ResponseEntity.ok(categoriasService.createCategorias(categoria));
    }    
    
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarCategorias(@PathVariable int id,@RequestBody Categorias categoria){
        if (categoriasService.updateCategorias(categoria,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la categoria para actualizarla");
        }else{
            return ResponseEntity.ok(categoriasService.getCategoriasById(id));
        }
    }    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarCategorias(@PathVariable int id){
        try {
            categoriasService.deleteCategorias(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } 
    }
}
