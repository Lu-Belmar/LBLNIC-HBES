package com.example.HardBoiledEgg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HardBoiledEgg.model.InventarioTienda;
import com.example.HardBoiledEgg.model.Producto;
import com.example.HardBoiledEgg.repository.productoRepository;
import com.example.HardBoiledEgg.service.inventariotiendaService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/inventariotienda")
public class inventariotiendaController {
    @Autowired
    private inventariotiendaService inventariotiendaservice;
    private productoRepository productoRepository;

    @GetMapping
    public ResponseEntity<List<InventarioTienda>> listarInventarioTienda() {
        List<InventarioTienda> InventarioTienda = inventariotiendaservice.getInventarioTienda();
        if (InventarioTienda.isEmpty()) {
            return ResponseEntity.noContent().build();    
        }
        return ResponseEntity.ok(InventarioTienda);
    }   

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarInventarioTiendaById(@PathVariable int id){
        if (inventariotiendaservice.getInventarioTiendaById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Inventario");
        } else {
            return ResponseEntity.ok(inventariotiendaservice.getInventarioTiendaById(id)) ;
        }
    }
/* 
    @GetMapping("/getByProducto/{id}")
    public ResponseEntity<?> buscarInventarioTiendaByProducto(@PathVariable int id) {
        if (productoRepository.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró tienda con este producto")
        } else {
            return inventariotiendaservice.getInventarioTiendaByProducto(productoRepository.findById(id));
        }
        
        
    }
    */
   @GetMapping("/getByProductox/{id}")
    public List<InventarioTienda> buscarInventarioTiendaByProducto(@PathVariable int id) {
        Producto rrr = productoRepository.findById(id).get() ;
            return inventariotiendaservice.getInventarioTiendaByProducto(rrr);
        
    }
     
    @PostMapping("/add")
    public ResponseEntity<InventarioTienda> creaInventarioTienda(@RequestBody InventarioTienda inventariotienda){
        return ResponseEntity.ok(inventariotiendaservice.createInventarioTienda(inventariotienda));
    }    
    
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarInventarioTienda(@PathVariable int id,@RequestBody InventarioTienda inventariotienda){
        if (inventariotiendaservice.updateInventarioTienda(inventariotienda ,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la _ para actualizarla");
        }else{
            return ResponseEntity.ok(inventariotiendaservice.getInventarioTiendaById(id));
        }
    }    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarInventarioTienda(@PathVariable int id){
        try {
            inventariotiendaservice.deleteInventarioTienda(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } 
    }



}
