package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.InventarioTienda;
import com.example.HardBoiledEgg.model.Producto;
import com.example.HardBoiledEgg.repository.inventariotiendaRepository;
import com.example.HardBoiledEgg.repository.tiendaRepository;

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

    @Transactional
    public InventarioTienda updateInventarioTienda(InventarioTienda updatedData, Integer id){
        if (inventariotiendarepository.existsById(id)) {
            InventarioTienda existing = inventariotiendarepository.getReferenceById(id);
            if (updatedData.getId() != null) {
                existing.setId(updatedData.getId());
            }
            if (updatedData.getPrecioLocal() != null) {
                existing.setPrecioLocal(updatedData.getPrecioLocal());
            }
            if (updatedData.getProducto() != null) {
                existing.setProducto(updatedData.getProducto());
            }
            if (updatedData.getStock() != null) {
                existing.setStock(updatedData.getStock());
            }
            if (updatedData.getTienda() != null) {
                existing.setTienda(updatedData.getTienda());
            }
            if (updatedData.getVenta() != null) {
                existing.setVenta(updatedData.getVenta());
            }
            return inventariotiendarepository.save(existing);
        }
        return null;
    }

    public void deleteInventarioTienda(int id){
        inventariotiendarepository.deleteById(id);
    } 

    public List<InventarioTienda> getInventarioTiendaByProducto(int id){
        
        Producto producto = productoService.getProductoById(id);
        return inventariotiendarepository.findByProducto(producto);
    }

}
