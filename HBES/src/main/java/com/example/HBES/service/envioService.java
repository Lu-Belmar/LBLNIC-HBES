package com.example.HBES.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HBES.model.envioModel;
import com.example.HBES.repository.envioRepository;

import jakarta.transaction.Transactional;

//@Service
//@Transactional


/*public class envioService {

    public List<envioModel> getProductos(){
        return productoRepository.findAll();
    }

    public productoModel getProductoById(int id){
        return productoRepository.findById(id).get();
    }

    public List<productoModel> getProductoByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

    public List<productoModel> getProductoByCategoria(String categoria){
        return productoRepository.findByCategoria(categoria);
    }

    public productoModel createProducto(productoModel producto){
        return productoRepository.save(producto);        
    }

    public productoModel updateProducto(productoModel producto, int id){
        int x = 0;
        if (productoRepository.existsById(id)){
            productoRepository.save(producto);
            return productoRepository.getById(id);
        } else {
            return null;
        }
    }

    public void deleteProducto(int id){
        productoRepository.deleteById(id);
    }
    

}*/
