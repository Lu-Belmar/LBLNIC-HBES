package com.example.HBES.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HBES.model.Producto;
import com.example.HBES.model.Usuario;
import com.example.HBES.repository.usuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class usuarioService {

    @Autowired
    private usuarioRepository usuarioRepository;

    public Usuario getUsuarioWithProductos(Long usuarioId) {

        return usuarioRepository.findUsuarioWithProductos(usuarioId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado")); 
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int id){
        return usuarioRepository.findById(id).get();
    }

    public List<Usuario> getUsuarioByNombre(String nombre){
        return usuarioRepository.findByNombre(nombre);      
    }

    public Usuario createUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Usuario usuario, int id){
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
