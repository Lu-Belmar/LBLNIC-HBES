package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.Venta;
import com.example.HardBoiledEgg.repository.ventaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ventaService {

    @Autowired
    private ventaRepository ventaRepository;

    public List<Venta> getVentas(){
        return ventaRepository.findAll();
    }

    public Venta getVentaById(Integer id){
        return ventaRepository.findById(id).get();
    } 

    public Venta createVenta(Venta venta){
        return ventaRepository.save(venta);
    }

    public Venta updateVenta(Venta venta, int id){
        if (ventaRepository.existsById(id)) {
            return ventaRepository.save(venta);
        }else{
            return null;
        }
        
    }

    public void deleteVenta(int id){
        ventaRepository.deleteById(id);
    }
}
