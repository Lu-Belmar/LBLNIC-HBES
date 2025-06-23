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

import com.example.HardBoiledEgg.model.Cliente;
import com.example.HardBoiledEgg.service.clienteService;

@RestController
@RequestMapping("api/cliente")
public class clienteController {
    @Autowired
    private clienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.getCliente();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();    
        }
        return ResponseEntity.ok(clientes);
    }   

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarClienteById(@PathVariable int id){
        if (clienteService.getClienteById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Cliente");
        } else {
            return ResponseEntity.ok(clienteService.getClienteById(id)) ;
        }
    }
     
    @PostMapping("/add")
    public ResponseEntity<Cliente> creaCliente(@RequestBody Cliente cliente){
        return ResponseEntity.ok(clienteService.createCliente(cliente));
    }    
    
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable int id,@RequestBody Cliente cliente){
        if (clienteService.updateCliente(cliente,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el Cliente para actualizarla");
        }else{
            return ResponseEntity.ok(clienteService.getClienteById(id));
        }
    }    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarCliente(@PathVariable int id){
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } 
    }
}
