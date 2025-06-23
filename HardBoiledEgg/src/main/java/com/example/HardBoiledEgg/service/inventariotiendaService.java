package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.InventarioTienda;
import com.example.HardBoiledEgg.repository.inventariotiendaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class inventariotiendaService {

    @Autowired 
    private inventariotiendaRepository inventariotiendarepository;

    public List<InventarioTienda> getInventarioTienda(){
        return inventariotiendarepository.findAll();
    }

    public InventarioTienda getInventarioTiendaById(Integer id){
        return inventariotiendarepository.findById(id).get();
    }

    public InventarioTienda createInventarioTienda(InventarioTienda inventariotienda){
        return inventariotiendarepository.save(inventariotienda);  

    }

    public InventarioTienda updateInventarioTienda(InventarioTienda inventariotienda, Integer id){
        
        if (inventariotiendarepository.existsById(id)){
            inventariotiendarepository.save(inventariotienda);
            return inventariotiendarepository.getReferenceById(id);
        } else {
            return null;
        }
    }

    public void deleteInventarioTienda(int id){
        inventariotiendarepository.deleteById(id);
    } 
}
