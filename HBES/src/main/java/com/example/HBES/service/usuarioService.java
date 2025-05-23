package com.example.HBES.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HBES.model.usuarioModel;
import com.example.HBES.repository.usuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class usuarioService {

    @Autowired
    private usuarioRepository usuarioRepository;

    public List<usuarioModel> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public usuarioModel getUsuarioById(int id){
        return usuarioRepository.findById(id).get();
    }

    public List<usuarioModel> getUsuarioByNombre(String nombre){
        return usuarioRepository.findByNombre(nombre);      
    }

    public usuarioModel createUsuario(usuarioModel usuario){
        return usuarioRepository.save(usuario);
    }

    public usuarioModel updateUsuario(usuarioModel usuario, int id){
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.save(usuario);
            return usuarioRepository.getById(id);
        }else{
            return null;
        }
    } 

    public void deleteUsuario(int id){
        usuarioRepository.deleteById(id);
    } 

    public void createCarritoUsuario(){

    }
}
