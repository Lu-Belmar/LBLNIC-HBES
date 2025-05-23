package com.example.HBES.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HBES.model.Envio;
import com.example.HBES.repository.envioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class envioService {

    @Autowired 
    private envioRepository envioRepository;

    public List<Envio> getEnvios(){
        return envioRepository.findAll();
    }

    public Envio getEnvioById(Integer id){
        return envioRepository.findById(id).get();
    }

    public  Envio createEnvio(Envio envio){
        return envioRepository.save(envio);  

    }

    public Envio updateEnvio(Envio Envio, Integer id){
        
        if (envioRepository.existsById(id)){
            envioRepository.save(Envio);
            return envioRepository.getById(id);
        } else {
            return null;
        }
    }

    public void deleteEnvio(int id){
        envioRepository.deleteById(id);
    }
   
}
