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

    public usuarioModel getById(int id){
        return usuarioRepository.findById(id).get();
    }

    public usuarioModel createUsuario(usuarioModel usuario){
        return usuarioRepository.save(usuario);
    }

    public usuarioModel updateUsuario(usuarioModel usuario, int id){
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.save(usuario);
            return usuario;
        }else{
            return null;
        }
    } 

    public void deleteUsuario(int id){
        usuarioRepository.deleteById(id);
    } 
}
