package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.Cliente;
import com.example.HardBoiledEgg.repository.clienteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class clienteService {

    @Autowired 
    private clienteRepository clienterepository;

    public List<Cliente> getCliente(){
        return clienterepository.findAll();
    }

    public Cliente getClienteById(Integer id){
        if (clienterepository.findById(id).isPresent()) {
            return clienterepository.findById(id).get();
        } else {
            return null;
        }
    }

    public  Cliente createCliente(Cliente cliente){
        return clienterepository.save(cliente);  

    }

    public Cliente updateCliente(Cliente cliente, Integer id){
        
        if (clienterepository.existsById(id)){
            clienterepository.save(cliente);
            return clienterepository.getReferenceById(id);
        } else {
            return null;
        }
    }

    public void deleteCliente(int id){
        clienterepository.deleteById(id);
    }   


}
