package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.Categorias;
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

    public Producto getProductoById(int id){
        return productorepository.findById(id).orElse(null);
    }

    public Producto createProducto(Producto producto){
        return productorepository.save(producto);  

    }

    @Transactional
    public Producto updateProducto(Producto updatedData, Integer id) {
        if (productorepository.existsById(id)) {
            Producto existing = productorepository.getReferenceById(id);
            if (updatedData.getNombre() != null) {
                existing.setNombre(updatedData.getNombre());
            }
            if (updatedData.getMarca() != null) {
                existing.setMarca(updatedData.getMarca());
            }
            if (updatedData.getCategoria() != null) {
                existing.setCategoria(updatedData.getCategoria());
            }
            if (updatedData.getInventario() != null) {
                existing.setInventario(updatedData.getInventario());
            }
            if (updatedData.getProveedor() != null) {
                existing.setProveedor(updatedData.getProveedor());
            }
            return productorepository.save(existing);
        }
        return null;
    }

    public void deleteProducto(int id){
        productorepository.deleteById(id);
    }

    public List<Producto> getProductoByNombre(String nombre){
        return productorepository.findByNombre(nombre);
    }

    public List<Producto> getProductoByCategoria(Categorias categoria){
        return productorepository.findByCategoria(categoria);
    }


}
