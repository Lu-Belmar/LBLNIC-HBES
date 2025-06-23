package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.Proveedores;
import com.example.HardBoiledEgg.repository.proveedoresRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class proveedoresService {

    @Autowired 
    private proveedoresRepository proveedoresrepository;

    public List<Proveedores> getProveedores(){
        return proveedoresrepository.findAll();
    }

    public Proveedores getProveedoresById(Integer id){
        return proveedoresrepository.findById(id).get();
    }

    public  Proveedores createProveedores(Proveedores proveedores){
        return proveedoresrepository.save(proveedores);  

    }

    public Proveedores updateProveedores(Proveedores proveedores, Integer id){
        
        if (proveedoresrepository.existsById(id)){
            proveedoresrepository.save(proveedores);
            return proveedoresrepository.getReferenceById(id);
        } else {
            return null;
        }
    }

    public void deleteProveedores(int id){
        proveedoresrepository.deleteById(id);
    }
}
