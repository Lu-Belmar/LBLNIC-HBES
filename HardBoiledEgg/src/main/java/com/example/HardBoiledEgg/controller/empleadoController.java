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

import com.example.HardBoiledEgg.model.Empleado;
import com.example.HardBoiledEgg.service.empleadoService;

public class empleadoController {
    @Autowired
    private empleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<Empleado>> listar() {
        List<Empleado> empleados = empleadoService.getEmpleados();
        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();    
        }
        return ResponseEntity.ok(empleados);
    }   

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> buscarEmpleadoById(@PathVariable int id){
        if (empleadoService.getEmpleadoById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la _");
        } else {
            return ResponseEntity.ok(empleadoService.getEmpleadoById(id)) ;
        }
    }
     
    @PostMapping("/add")
    public ResponseEntity<Empleado> creaVenta(@RequestBody Empleado empleado){
        return ResponseEntity.ok(empleadoService.createEmpleado(empleado));
    }    
    
    @PostMapping("/update/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable int id,@RequestBody Empleado empleado){
        if (empleadoService.updateEmpleado(empleado,id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la _ para actualizarla");
        }else{
            return ResponseEntity.ok(empleadoService.getEmpleadoById(id));
        }
    }    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrarEmpleado(@PathVariable int id){
        try {
            empleadoService.deleteEmpleado(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        } 
    }
}
