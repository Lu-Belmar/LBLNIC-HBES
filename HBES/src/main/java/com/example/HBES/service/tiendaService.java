package com.example.HBES.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HBES.model.Tienda;
import com.example.HBES.repository.tiendaRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class tiendaService {


    @Autowired
    private tiendaRepository tiendaRepository;

    public List<Tienda> getTiendas(){
        return tiendaRepository.findAll();
    }

    public Tienda getTiendaById(int id){
        return tiendaRepository.findById(id).get();
    }

    public Tienda createTienda(Tienda tienda){
        return tiendaRepository.save(tienda);
    }

    public Tienda updateTienda(Tienda tienda, int id){
        if (tiendaRepository.existsById(id)) {
            tiendaRepository.save(tienda);
            return tiendaRepository.getById(id);
        } else {
            return null;
        }
    }

    public void deleteTienda(int id){
        tiendaRepository.deleteById(id);
    }




}
