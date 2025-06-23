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

import com.example.HardBoiledEgg.model.Tienda;
import com.example.HardBoiledEgg.service.tiendaService;

@RestController
@RequestMapping("/api/tienda")
public class tiendaController {


    @Autowired
    private tiendaService tiendaService;

    @GetMapping
    public ResponseEntity<List<Tienda>> listarTiendas(){
        List<Tienda> tienda = tiendaService.getTienda();
        if (tienda.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(tienda);
        }
    } 

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarTiendaById(@PathVariable int id) {
        if (tiendaService.getTiendaById(id)==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la tienda");
        } else {
            return ResponseEntity.ok(tiendaService.getTiendaById(id));
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<Tienda> createTienda(@RequestBody Tienda tienda) {
        return ResponseEntity.ok(tiendaService.createTienda(tienda));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarTeinda(@RequestBody Tienda tienda,@PathVariable int id) {
        if (tiendaService.updateTienda(tienda,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la tienda para poder actualizarla");
        } else {
            return ResponseEntity.ok(tiendaService.getTiendaById(id));
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarTienda(@PathVariable int id){
        try {
            tiendaService.deleteTienda(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
