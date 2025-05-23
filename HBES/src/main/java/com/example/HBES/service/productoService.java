package com.example.HBES.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HBES.model.Producto;
import com.example.HBES.model.Tienda;
import com.example.HBES.repository.productoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class productoService {

    @Autowired
    private productoRepository productoRepository;

        public Producto getProductoWithRelations(Long id) {
        return productoRepository.findByIdWithRelations(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
    public Producto getProductoById(int id){
        return productoRepository.findById(id).get();
    }

    public List<Producto> getProductos(){
        return productoRepository.findAll();
    }

    public List<Producto> getProductoByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

    public List<Producto> getProductoByCategoria(String categoria){
        return productoRepository.findByCategoria(categoria);
    }

    public Producto createProducto(Producto producto){
        return productoRepository.save(producto);        
    }

    public Producto updateProducto(Producto producto, int id){
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
}
