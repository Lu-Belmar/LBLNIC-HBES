package com.example.HBES.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.HBES.repository.ventaRepository;
import com.example.HBES.model.Tienda;
import com.example.HBES.model.Venta;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
@Repository
@Service
@Transactional
public class ventaService {

@Autowired 
private  ventaRepository ventaRepository;
 
    public List<Venta> getVentas(){
        return ventaRepository.findAll();
    }

    public Venta getVentaById(int id){
        return ventaRepository.findById(id).get();
    }

    public Venta createVenta(Venta venta){
        return ventaRepository.save(venta);
    }

    public Venta updateVenta(Venta venta, int id){
        if (ventaRepository.existsById(id)) {
            ventaRepository.save(venta);
            return ventaRepository.getById(id);
        } else {
            return null;
        }
    }

    public void deleteVenta(int id){
        ventaRepository.deleteById(id);
    }



}
