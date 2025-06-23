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

import com.example.HardBoiledEgg.model.Venta;
import com.example.HardBoiledEgg.service.ventaService;

@RestController
@RequestMapping("/api/venta")
public class ventaController {

    @Autowired
    private ventaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.getVentas();
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();    
        }
        return ResponseEntity.ok(ventas);
    }   

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarVentaById(@PathVariable int id){
        if (ventaService.getVentaById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la venta");
        } else {
            return ResponseEntity.ok(ventaService.getVentaById(id)) ;
        }
    }
     
  /*   @PostMapping("/add")
    public ResponseEntity<Venta> creaVenta(@RequestBody Venta venta){
        return ResponseEntity.ok(ventaService.createVenta(venta));
    }    */
    
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarVenta(@PathVariable int id,@RequestBody Venta venta){
        if (ventaService.updateVenta(venta,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la venta para actualizarla");
        }else{
            return ResponseEntity.ok(ventaService.getVentaById(id));
        }
    }    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarVenta(@PathVariable int id){
        try {
            ventaService.deleteVenta(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } 
    }
}
