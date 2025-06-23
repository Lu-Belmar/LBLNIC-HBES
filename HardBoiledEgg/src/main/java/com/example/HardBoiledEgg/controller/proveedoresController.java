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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HardBoiledEgg.model.Proveedores;
import com.example.HardBoiledEgg.service.proveedoresService;

@RestController
@RequestMapping("api/proveedores")
public class proveedoresController {
    @Autowired
    private proveedoresService proveedoresService;

    @GetMapping
    public ResponseEntity<List<Proveedores>> listarProveedores() {
        List<Proveedores> proveedores = proveedoresService.getProveedores();
        if (proveedores.isEmpty()) {
            return ResponseEntity.noContent().build();    
        }
        return ResponseEntity.ok(proveedores);
    }   

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarProveedoresById(@PathVariable int id){
        if (proveedoresService.getProveedoresById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el proveedor");
        } else {
            return ResponseEntity.ok(proveedoresService.getProveedoresById(id)) ;
        }
    }
     
    @PostMapping("/add")
    public ResponseEntity<Proveedores> creaVenta(@RequestBody Proveedores proveedores){
        return ResponseEntity.ok(proveedoresService.createProveedores(proveedores));
    }    
    
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarProveedores(@PathVariable int id,@RequestBody Proveedores proveedores){
        if (proveedoresService.updateProveedores(proveedores,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el proveedores para actualizarla");
        }else{
            return ResponseEntity.ok(proveedoresService.getProveedoresById(id));
        }
    }    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarProveedores(@PathVariable int id){
        try {
            proveedoresService.deleteProveedores(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } 
    }
}
