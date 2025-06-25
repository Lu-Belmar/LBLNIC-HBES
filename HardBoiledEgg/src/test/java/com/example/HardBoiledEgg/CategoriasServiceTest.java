package com.example.HardBoiledEgg;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.HardBoiledEgg.model.Direccion;
import com.example.HardBoiledEgg.model.DireccionCliente;
import com.example.HardBoiledEgg.model.Envio;
import com.example.HardBoiledEgg.model.Venta;
import com.example.HardBoiledEgg.repository.envioRepository;
import com.example.HardBoiledEgg.service.direccionService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import com.example.HardBoiledEgg.service.envioService;

import lombok.extern.slf4j.Slf4j;

import com.example.HardBoiledEgg.repository.envioRepository;
import com.example.HardBoiledEgg.service.categoriasService;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class CategoriasServiceTest {

    @Autowired
    private categoriasService categoriasService;

    @MockitoBean
    private categoriasRepository categoriasRepository;

    // ---- Tests para getCategorias() ----
    @Test
    public void whenGetAllCategorias_thenReturnList() {
        log.info("Test: Obtener todas las categorías");
        
        Categorias categoria1 = new Categorias(1, "Electrónicos", "Dispositivos electrónicos", null);
        Categorias categoria2 = new Categorias(2, "Ropa", "Prendas de vestir", null);
        
        when(categoriasRepository.findAll()).thenReturn(List.of(categoria1, categoria2));
        log.debug("Mock configurado: findAll() retornará 2 categorías");

        List<Categorias> result = categoriasService.getCategorias();
        
        assertEquals(2, result.size());
        verify(categoriasRepository, times(1)).findAll();
        log.info("Resultado: {} categorías obtenidas", result.size());
    }

    // ---- Tests para getCategoriasById() ----
    @Test
    public void whenValidId_thenReturnCategoria() {
        log.info("Test: Obtener categoría por ID válido");
        
        Categorias categoria = new Categorias(1, "Electrónicos", "Dispositivos electrónicos", null);
        when(categoriasRepository.findById(1)).thenReturn(Optional.of(categoria));
        log.debug("Mock configurado: findById(1) retornará categoría");

        Categorias result = categoriasService.getCategoriasById(1);
        
        assertNotNull(result);
        assertEquals("Electrónicos", result.getNombre());
        verify(categoriasRepository, times(1)).findById(1);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        log.info("Test: Obtener categoría con ID inválido");
        
        when(categoriasRepository.findById(99)).thenReturn(Optional.empty());
        log.debug("Mock configurado: findById(99) retornará Optional.empty");

        Categorias result = categoriasService.getCategoriasById(99);
        
        assertNull(result);
        verify(categoriasRepository, times(1)).findById(99);
    }

    // ---- Tests para createCategorias() ----
    @Test
    public void whenCreateCategoria_thenReturnSavedCategoria() {
        log.info("Test: Crear nueva categoría");
        
        Categorias newCategoria = new Categorias(null, "Alimentos", "Comestibles", null);
        Categorias savedCategoria = new Categorias(1, "Alimentos", "Comestibles", null);
        
        when(categoriasRepository.save(newCategoria)).thenReturn(savedCategoria);
        log.debug("Mock configurado: save() retornará categoría con ID generado");

        Categorias result = categoriasService.createCategorias(newCategoria);
        
        assertNotNull(result.getId());
        assertEquals("Alimentos", result.getNombre());
        verify(categoriasRepository, times(1)).save(newCategoria);
    }

    // ---- Tests para updateCategorias() ----
    @Test
    public void whenUpdateExistingCategoria_thenReturnUpdated() {
        log.info("Test: Actualizar categoría existente");
        
        Categorias existing = new Categorias(1, "Electrónicos", "Dispositivos", null);
        Categorias updatedData = new Categorias(1, "Tecnología", "Dispositivos tecnológicos", null);
        
        when(categoriasRepository.existsById(1)).thenReturn(true);
        when(categoriasRepository.save(updatedData)).thenReturn(updatedData);
        when(categoriasRepository.getReferenceById(1)).thenReturn(updatedData);
        log.debug("Mocks configurados para update");

        Categorias result = categoriasService.updateCategorias(updatedData, 1);
        
        assertEquals("Tecnología", result.getNombre());
        verify(categoriasRepository, times(1)).save(updatedData);
    }

    @Test
    public void whenUpdateNonExistingCategoria_thenReturnNull() {
        log.info("Test: Actualizar categoría inexistente");
        
        Categorias data = new Categorias(99, "Inexistente", "No existe", null);
        when(categoriasRepository.existsById(99)).thenReturn(false);
        log.debug("Mock configurado: existsById(99)=false");

        Categorias result = categoriasService.updateCategorias(data, 99);
        
        assertNull(result);
        verify(categoriasRepository, never()).save(any());
    }

    // ---- Tests para deleteCategorias() ----
    @Test
    public void whenDeleteExistingCategoria_thenNoException() {
        log.info("Test: Eliminar categoría existente");
        int id = 1;
        
        doNothing().when(categoriasRepository).deleteById(id);
        log.debug("Mock configurado: deleteById() no hará nada");

        assertDoesNotThrow(() -> categoriasService.deleteCategorias(id));
        verify(categoriasRepository, times(1)).deleteById(id);
    }

    @Test
    public void whenDeleteNonExistingCategoria_thenThrowException() {
        log.info("Test: Eliminar categoría inexistente");
        int id = 99;
        
        doThrow(EmptyResultDataAccessException.class)
            .when(categoriasRepository)
            .deleteById(id);
        log.debug("Mock configurado: deleteById() lanzará EmptyResultDataAccessException");

        assertThrows(EmptyResultDataAccessException.class, 
            () -> categoriasService.deleteCategorias(id));
    }
}
