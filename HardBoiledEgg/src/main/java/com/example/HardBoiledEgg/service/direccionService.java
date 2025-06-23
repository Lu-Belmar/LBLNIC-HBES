package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.Direccion;
import com.example.HardBoiledEgg.repository.direccionRepository;

import jakarta.transaction.Transactional;

@Service    
@Transactional
public class direccionService {


    @Autowired 
    private direccionRepository direccionrepository;

    public List<Direccion> getDireccion(){
        return direccionrepository.findAll();
    }

    public Direccion getDireccionById(Integer id){
        return direccionrepository.findById(id).get();
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
