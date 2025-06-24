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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HardBoiledEgg.model.Direccion;
import com.example.HardBoiledEgg.model.DireccionCliente;
import com.example.HardBoiledEgg.service.direccionService;

@RestController
@RequestMapping("api/direccion")
public class direccionController {
    @Autowired
    private direccionService direccionService;

    @GetMapping
    public ResponseEntity<List<Direccion>> listarDirecciones() {
        List<Direccion> direcciones = direccionService.getDireccion();
        if (direcciones.isEmpty()) {
            return ResponseEntity.noContent().build();    
        }
        return ResponseEntity.ok(direcciones);
    }   

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarDireccionById(@PathVariable int id){
        if (direccionService.getDireccionById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la Direccion");
        } else {
            return ResponseEntity.ok(direccionService.getDireccionById(id)) ;
        }
    }
     
    @PostMapping("/add")
    public ResponseEntity<Direccion> creaVenta(@RequestBody Direccion direccion){
        return ResponseEntity.ok(direccionService.createDireccion(direccion));
    }    
    
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizar_(@PathVariable int id,@RequestBody Direccion direccion){
        if (direccionService.updateDireccion(direccion,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la Direccion para actualizarla");
        }else{
            return ResponseEntity.ok(direccionService.getDireccionById(id));
        }
    }    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrar_(@PathVariable int id){
        try {
            direccionService.deleteDireccion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } 
    }

    @PostMapping("/clientes")
    public DireccionCliente createDireccionCliente(
            @RequestBody DireccionCliente direccion,
            @RequestParam Integer clienteId) {
        return direccionService.crearDireccionCliente(direccion, clienteId);
    }
}
