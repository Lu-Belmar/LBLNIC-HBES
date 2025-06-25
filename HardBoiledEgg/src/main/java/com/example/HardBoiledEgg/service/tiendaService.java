package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.Tienda;
import com.example.HardBoiledEgg.repository.tiendaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class tiendaService {


    @Autowired 
    private tiendaRepository tiendarepository;

    public List<Tienda> getTienda(){
        return tiendarepository.findAll();
    }

    public Tienda getTiendaById(Integer id){
        return tiendarepository.findById(id).orElse(null);
    }

    public Tienda createTienda(Tienda tienda){
        return tiendarepository.save(tienda);  

    }

    public Tienda updateTienda(Tienda tienda, Integer id){
        
        if (tiendarepository.existsById(id)){
            tiendarepository.save(tienda);
            return tiendarepository.getReferenceById(id);
        } else {
            return null;
        }
    }

    public void deleteTienda(int id){
        tiendarepository.deleteById(id);
    }
}
