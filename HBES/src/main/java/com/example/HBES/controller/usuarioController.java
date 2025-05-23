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

import com.example.HBES.model.Producto;
import com.example.HBES.model.Usuario;  
import com.example.HBES.service.usuarioService;

@RestController
@RequestMapping("/api/usuario")
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<Usuario> getUsuarioWithProductos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.getUsuarioWithProductos(usuarioId);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.getUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();    
        }
        return ResponseEntity.ok(usuarios);
    }
/* 
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarUsuarioById(@PathVariable int id){
        if (usuarioService.getUsuarioById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario");
        } else {
            return ResponseEntity.ok(usuarioService.getUsuarioById(id)) ;
        }
        
    }
*/
    @GetMapping("/getByName/{nombre}")
    public ResponseEntity<?> buscarUsuarioByNombre(@PathVariable String nombre){
        if (usuarioService.getUsuarioByNombre(nombre).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra usuario con este nombre");           
        } else {
            return ResponseEntity.ok(usuarioService.getUsuarioByNombre(nombre)) ;
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.createUsuario(usuario));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable int id,@RequestBody Usuario usuario){
        if (usuarioService.updateUsuario(usuario,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario para actualizarlo");
        }else{
            return ResponseEntity.ok(usuarioService.getUsuarioById(id));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarUsuario(@PathVariable int id){
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } 
    }
}
