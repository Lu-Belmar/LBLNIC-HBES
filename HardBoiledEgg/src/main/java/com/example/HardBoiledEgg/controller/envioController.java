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

import com.example.HardBoiledEgg.model.Envio;
import com.example.HardBoiledEgg.service.envioService;

public class envioController {
    @Autowired
    private envioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> listarEnvios() {
        List<Envio> envios = envioService.getEnvios();
        if (envios.isEmpty()) {
            return ResponseEntity.noContent().build();    
        }
        return ResponseEntity.ok(envios);
    }   

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscar_ById(@PathVariable int id){
        if (envioService.getEnvioById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el envío");
        } else {
            return ResponseEntity.ok(envioService.getEnvioById(id)) ;
        }
    }
     
    @PostMapping("/add")
    public ResponseEntity<Envio> creaVenta(@RequestBody Envio envio){
        return ResponseEntity.ok(envioService.createEnvio(envio));
    }    
    
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarEnvio(@PathVariable int id,@RequestBody Envio envio){
        if (envioService.updateEnvio(envio ,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la _ para actualizarla");
        }else{
            return ResponseEntity.ok(envioService.getEnvioById(id));
        }
    }    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrar_(@PathVariable int id){
        try {
            envioService.deleteEnvio(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } 
    }
}
