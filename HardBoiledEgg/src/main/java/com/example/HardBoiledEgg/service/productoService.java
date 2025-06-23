package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.Producto;
import com.example.HardBoiledEgg.repository.productoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class productoService {

    @Autowired 
    private productoRepository productorepository;

    public List<Producto> getProducto(){
        return productorepository.findAll();
    }

    public Producto getProductoById(Integer id){
        return productorepository.findById(id).get();
    }

    public Producto createProducto(Producto producto){
        return productorepository.save(producto);  

    }

    public Producto updateProducto(Producto producto, Integer id){
        
        if (productorepository.existsById(id)){
            productorepository.save(producto);
            return productorepository.getReferenceById(id);
        } else {
            return null;
        }
    }

    public void deleteProducto(int id){
        productorepository.deleteById(id);
    }

    public List<Producto> getProductoByNombre(String nombre){
        return productorepository.findByNombre(nombre);
    }

    public List<Producto> getProductoByCategoria(String categoria){
        return productorepository.findByCategoria(categoria);
    }


}
