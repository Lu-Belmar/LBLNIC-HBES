package com.example.HardBoiledEgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardBoiledEgg.model.Categorias;
import com.example.HardBoiledEgg.repository.categoriasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class categoriasService {

    @Autowired 
    private categoriasRepository categoriasrepository;

    public List<Categorias> getCategorias(){
        return categoriasrepository.findAll();
    }

    public Categorias getCategoriasById(Integer id){
        if (categoriasrepository.findById(id).isPresent()) {
            return categoriasrepository.findById(id).get();
        } else {
            return null;
        }
    }

    public  Categorias createCategorias(Categorias categorias){
        return categoriasrepository.save(categorias);  

    }

    public Categorias updateCategorias(Categorias categorias, Integer id){
        
        if (categoriasrepository.existsById(id)){
            categoriasrepository.save(categorias);
            return categoriasrepository.getReferenceById(id);
        } else {
            return null;
        }
    }

    public void deleteCategorias(int id){
        categoriasrepository.deleteById(id);
    }

}
