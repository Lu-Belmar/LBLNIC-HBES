package com.example.HBES.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HBES.model.envioModel;
import com.example.HBES.repository.envioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class envioService {

    @Autowired envioRepository envioRepository;
    public List<envioModel> getEnvios(){
        return envioRepository.findAll();
    }

    public productoModel getEnvios(int id_envio){
        return envioRepository.findById(id_envio).get();
    }

    public List<envioModel> getProductoByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

    public List<envioModel> getProductoByCategoria(String categoria){
        return productoRepository.findByCategoria(categoria);
    }

    public  List<envioModel> createProducto(productoModel producto){
        return productoRepository.save(producto);        
    }

    public envioModel updateEnvio(productoModel producto, int id){
        int x = 0;
        if envioRepository.existsById(id)){
            enviooRepository.save(producto);
            return productoRepository.getById(id);
        } else {
            return null;
        }
    }

    public void deleteProducto(int id){
        productoRepository.deleteById(id);
    }
    

}
