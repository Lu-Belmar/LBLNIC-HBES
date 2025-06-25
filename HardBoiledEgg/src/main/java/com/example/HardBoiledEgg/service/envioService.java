package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.Envio;
import com.example.HardBoiledEgg.model.InventarioTienda;
import com.example.HardBoiledEgg.repository.envioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class envioService {

    @Autowired 
    private envioRepository enviorepository;

    public List<Envio> getEnvios(){
        return enviorepository.findAll();
    }

    public Envio getEnvioById(Integer id){
        return enviorepository.findById(id).orElse(null);
    }


    public  Envio createEnvio(Envio envio){
        return enviorepository.save(envio);  

    }

    public Envio updateEnvio(Envio updatedData, Integer id){
        if (enviorepository.existsById(id)) {
            Envio existing = enviorepository.getReferenceById(id);
            if (updatedData.getId() != null) {
                existing.setId(updatedData.getId());
            }
            if (updatedData.getDireccion() != null) {
                existing.setDireccion(updatedData.getDireccion());
            }
            if (updatedData.getFecha_Creacion_Envio() != null) {
                existing.setFecha_Creacion_Envio(updatedData.getFecha_Creacion_Envio());
            }
            if (updatedData.getId() != null) {
                existing.setId(updatedData.getId());
            }
            if (updatedData.getVenta() != null) {
                existing.setVenta(updatedData.getVenta());
            }
            return enviorepository.save(existing);
        }
        return null;
    }

    public void deleteEnvio(int id){
        enviorepository.deleteById(id);
    }   

}
