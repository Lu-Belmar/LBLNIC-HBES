package com.example.HBES.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HBES.model.Envio;
import com.example.HBES.service.envioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/envio")
public class envioController {
    @Autowired
    private envioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> listarEnvios() {
        List<Envio> envios = envioService.getEnvios();
        if (envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(envios);
        }
    }
    
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarEnvioById(@PathVariable int id) {
        if (envioService.getEnvioById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Envío");
        } else {
            return ResponseEntity.ok(envioService.getEnvioById(id));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Envio> crearEnvío(@RequestBody Envio envio) {    
        return ResponseEntity.ok(envioService.createEnvio(envio));
    }
    
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarEnvio(@RequestBody Envio envio, @PathVariable int id) {
        if (envioService.updateEnvio(envio, id)== null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el envío para actualizarlo");
        } else {
            return ResponseEntity.ok(envioService.getEnvioById(id));
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarEnvio(@PathVariable int id){
        try {
            envioService.deleteEnvio(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
