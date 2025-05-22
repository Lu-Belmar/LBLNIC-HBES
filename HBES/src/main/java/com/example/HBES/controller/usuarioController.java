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

import com.example.HBES.model.usuarioModel;
import com.example.HBES.service.usuarioService;

@RestController
@RequestMapping("/api/usuario")
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<usuarioModel>> listarUsuarios(){
        List<usuarioModel> usuarios = usuarioService.getUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();    
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> buscarUsuarioById(@PathVariable int id){
        if (usuarioService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario");
        } else {
            return ResponseEntity.ok(usuarioService.getById(id)) ;
        }
        
    }

    @PostMapping("/add")
    public ResponseEntity<usuarioModel> crearUsuario(@RequestBody usuarioModel usuario){
        return ResponseEntity.ok(usuarioService.createUsuario(usuario));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable int id,@RequestBody usuarioModel usuario){
        if (usuarioService.updateUsuario(usuario,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario para actualizalo");
        }else{
            return ResponseEntity.ok(usuario);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable int id){
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } 
    }
}
