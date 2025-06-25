package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.Empleado;
import com.example.HardBoiledEgg.model.Producto;
import com.example.HardBoiledEgg.repository.empleadoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class empleadoService {

    @Autowired 
    private empleadoRepository empleadorepository;

    public List<Empleado> getEmpleados(){
        return empleadorepository.findAll();
    }

    public Empleado getEmpleadoById(Integer id){
        return empleadorepository.findById(id).orElse(null);
    }

    public  Empleado createEmpleado(Empleado empleado){
        return empleadorepository.save(empleado);  

    }

    @Transactional
    public Empleado updateEmpleado(Empleado updatedData, Integer id) {
        if (empleadorepository.existsById(id)) {
            Empleado existing = empleadorepository.getReferenceById(id);
            if (updatedData.getNombre() != null) {
                existing.setNombre(updatedData.getNombre());
            }
            if (updatedData.getCorreo() != null) {
                existing.setCorreo(updatedData.getCorreo());
            }
            if (updatedData.getDireccion() != null) {
                existing.setDireccion(updatedData.getDireccion());
            }
            if (updatedData.getId() != null) {
                existing.setId(updatedData.getId());
            }
            if (updatedData.getRun() != null) {
                existing.setRun(updatedData.getRun());
            }
            if (updatedData.getSalario() != null) {
                existing.setSalario(updatedData.getSalario());
            }
            if (updatedData.getTelefono() != null) {
                existing.setTelefono(updatedData.getTelefono());
            }
            return empleadorepository.save(existing);
        }
        return null;
    }

    public void deleteEmpleado(int id){
        empleadorepository.deleteById(id);
    }   
}

/*
 *  @Autowired 
    private _Repository _repository;

    public List<_> get_(){
        return _repository.findAll();
    }

    public _ get_ById(Integer id){
        return _repository.findById(id).get();
    }

    public _ create_(_ _){
        return _repository.save(_);  

    }

    public _ update_(_ _, Integer id){
        
        if (_repository.existsById(id)){
            _repository.save(_);
            return _repository.getReferenceById(id);
        } else {
            return null;
        }
    }

    public void delete_(int id){
        _repository.deleteById(id);
    }   
 * 
 * 
 */