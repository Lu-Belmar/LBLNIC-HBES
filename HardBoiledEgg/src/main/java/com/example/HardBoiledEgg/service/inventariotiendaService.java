package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.InventarioTienda;
import com.example.HardBoiledEgg.model.Producto;
import com.example.HardBoiledEgg.repository.inventariotiendaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class inventariotiendaService {

    @Autowired 
    private inventariotiendaRepository inventariotiendarepository;
    private final productoService productoService;

    public List<InventarioTienda> getInventarioTienda(){
        return inventariotiendarepository.findAll();
    }

    public InventarioTienda getInventarioTiendaById(Integer id){
        return inventariotiendarepository.findById(id).orElse(null);  
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

    public List<InventarioTienda> getInventarioTiendaByProducto(int id){
        
        Producto producto = productoService.getProductoById(id);
        return inventariotiendarepository.findByProducto(producto);
    }

}
