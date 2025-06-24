package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.repository.tiendaRepository;
import com.example.HardBoiledEgg.model.Cliente;
import com.example.HardBoiledEgg.model.Direccion;
import com.example.HardBoiledEgg.model.DireccionCliente;
import com.example.HardBoiledEgg.model.DireccionEmpleado;
import com.example.HardBoiledEgg.model.DireccionTienda;
import com.example.HardBoiledEgg.model.Empleado;
import com.example.HardBoiledEgg.model.Tienda;
import com.example.HardBoiledEgg.repository.clienteRepository;
import com.example.HardBoiledEgg.repository.direccionRepository;
import com.example.HardBoiledEgg.repository.empleadoRepository;
import jakarta.transaction.Transactional;


@Service
public class direccionService {

    @Autowired
    private direccionRepository direccionrepository;
    
    @Autowired
    private clienteRepository clienterepository;
    
    @Autowired
    private empleadoRepository empleadorepository;
    
    @Autowired
    private tiendaRepository tiendarepository;

    // Métodos comunes para todas las direcciones
    public List<Direccion> findAll() {
        return direccionrepository.findAll();
    }
    
    public Direccion findById(Integer id) {
        return direccionrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
    }
    
    public void delete(Integer id) {
        direccionrepository.deleteById(id);
    }

    // Métodos específicos para cada tipo de dirección
    
    public DireccionCliente crearDireccionCliente(DireccionCliente direccion, Integer clienteId) {
        Cliente cliente = clienterepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        DireccionCliente saved = direccionrepository.save(direccion);
        cliente.setDireccion(saved);
        clienterepository.save(cliente);
        
        return saved;
    }
    
    public DireccionEmpleado crearDireccionEmpleado(DireccionEmpleado direccion, Integer empleadoId) {
        Empleado empleado = empleadorepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        
        DireccionEmpleado saved = direccionrepository.save(direccion);
        empleado.setDireccion(saved);
        empleadorepository.save(empleado);
        
        return saved;
    }
    
    public DireccionTienda crearDireccionTienda(DireccionTienda direccion, Integer tiendaId) {
        Tienda tienda = tiendarepository.findById(tiendaId)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
        
        DireccionTienda saved = direccionrepository.save(direccion);
        tienda.setDireccion(saved);
        tiendarepository.save(tienda);
        
        return saved;
    }
    public List<DireccionCliente> findAllDireccionesCliente() {
        return direccionrepository.findAllByTipoEntidadCliente();
    }
    
    public List<DireccionEmpleado> findAllDireccionesEmpleado() {
        return direccionrepository.findAllByTipoEntidadEmpleado();
    }
    
    public List<DireccionTienda> findAllDireccionesTienda() {
        return direccionrepository.findAllByTipoEntidadTienda();
    }

    public List<Direccion> getDireccion(){
        return direccionrepository.findAll();
    }

    public Direccion getDireccionById(Integer id){
        if (direccionrepository.findById(id).isPresent()) {
            return direccionrepository.findById(id).get();
        } else {
            return null;
        }
    }

    public  Direccion createDireccion(Direccion direccion){
        return direccionrepository.save(direccion);  

    }

    public Direccion updateDireccion(Direccion direccion, Integer id){
        
        if (direccionrepository.existsById(id)){
            direccionrepository.save(direccion);
            return direccionrepository.getReferenceById(id);
        } else {
            return null;
        }
    }

    public void deleteDireccion(int id){
        direccionrepository.deleteById(id);
    }   
}
