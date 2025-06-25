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
import com.example.HardBoiledEgg.model.InventarioTienda;
import com.example.HardBoiledEgg.model.Producto;
import com.example.HardBoiledEgg.model.Proveedores;
import com.example.HardBoiledEgg.model.Tienda;
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

import com.example.HardBoiledEgg.repository.proveedoresRepository;
import com.example.HardBoiledEgg.repository.tiendaRepository;
import com.example.HardBoiledEgg.service.proveedoresService;
import com.example.HardBoiledEgg.service.tiendaService;

import lombok.extern.slf4j.Slf4j;

import com.example.HardBoiledEgg.repository.envioRepository;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class TiendaServiceTest {
    @Autowired
    private tiendaRepository tiendaRepository;

    @MockitoBean
    private tiendaService tiendaService;

    // ---- Tests para getCategorias() ----
    @Test
    public void whenGetAllTienda_thenReturnList() {
        log.info("Test: Obtener todas las Tienda");
        
        Tienda tienda1 = new Tienda(1,19191, "correosa",null,null);
        Tienda tienda2 = new Tienda(2,23445, "dfsdfsfs",null,null);
        
        when(tiendaRepository.findAll()).thenReturn(List.of(tienda1, tienda2));
        log.debug("Mock configurado: findAll() retornará 2 categorías");

        List<Tienda> result = tiendaService.getTienda();
        
        assertEquals(2, result.size());
        verify(tiendaRepository, times(1)).findAll();
        log.info("Resultado: {} categorías obtenidas", result.size());
    }

    // ---- Tests para getCategoriasById() ----
    @Test
    public void whenValidId_thenReturnTienda() {
        log.info("Test: Obtener categoría por ID válido");
        
        Tienda tienda = new Tienda(2,23445, "dfsdfsfs",null,null);
        when(tiendaRepository.findById(1)).thenReturn(Optional.of(tienda));
        log.debug("Mock configurado: findById(1) retornará categoría");

        Tienda result = tiendaService.getTiendaById(1);
        
        assertNotNull(result);
        assertEquals("dfsdfsfs", result.getDireccion());
        verify(tiendaRepository, times(1)).findById(1);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        log.info("Test: Obtener categoría con ID inválido");
        
        when(tiendaRepository.findById(99)).thenReturn(Optional.empty());
        log.debug("Mock configurado: findById(99) retornará Optional.empty");

        Tienda result = tiendaService.getTiendaById(99);
        
        assertNull(result);
        verify(tiendaRepository, times(1)).findById(99);
    }

    // ---- Tests para createCategorias() ----
    @Test
    public void whenCreateCategoria_thenReturnSavedTienda() {
        log.info("Test: Crear nueva categoría");
        
        Tienda newTienda = new Tienda(1,19191, "correosa",null,null);
        Tienda savedTienda = new Tienda(3,19191, "correosa",null,null);
        
        when(tiendaRepository.save(newTienda)).thenReturn(savedTienda);
        log.debug("Mock configurado: save() retornará categoría con ID generado");

        Tienda result = tiendaService.createTienda(newTienda);
        
        assertNotNull(result.getId());
        assertEquals("correosa", result.getCorreo());
        verify(tiendaRepository, times(1)).save(newTienda);
    }

    // ---- Tests para updateCategorias() ----
    @Test
    public void whenUpdateExistingTienda_thenReturnUpdated() {
        log.info("Test: Actualizar Tienda existente");
        
        Tienda existing = new Tienda(1,19191, "correosa",null,null);
        Tienda updatedData = new Tienda(3,2345, "nanna",null,null);
        
        when(tiendaRepository.existsById(1)).thenReturn(true);
        when(tiendaRepository.save(updatedData)).thenReturn(updatedData);
        when(tiendaRepository.getReferenceById(1)).thenReturn(updatedData);
        log.debug("Mocks configurados para update");

        Tienda result = tiendaService.updateTienda(updatedData, 1);

        assertEquals("nanna", result.getCorreo());
        verify(tiendaRepository, times(1)).save(updatedData);
    }

    @Test
    public void whenUpdateNonExistingTienda_thenReturnNull() {
        log.info("Test: Actualizar categoría inexistente");
        
        Tienda data = new Tienda(99,19191, "correosa",null,null);
        when(tiendaRepository.existsById(99)).thenReturn(false);
        log.debug("Mock configurado: existsById(99)=false");

        Tienda result = tiendaService.updateTienda(data, 99);
        
        assertNull(result);
        verify(tiendaRepository, never()).save(any());
    }

    // ---- Tests para deleteCategorias() ----
    @Test
    public void whenDeleteExistingTienda_thenNoException() {
        log.info("Test: Eliminar categoría existente");
        int id = 1;
        
        doNothing().when(tiendaRepository).deleteById(id);
        log.debug("Mock configurado: deleteById() no hará nada");

        assertDoesNotThrow(() -> tiendaService.deleteTienda(id));
        verify(tiendaRepository, times(1)).deleteById(id);
    }

    @Test
    public void SiNoExisteLanzarErrorAlEliminarEnvio() {
        int nonExistentId = 999;
        log.info("Configurando test para eliminar envío con ID inexistente: {}", nonExistentId);

        doThrow(EmptyResultDataAccessException.class)
            .when(tiendaRepository)
            .deleteById(nonExistentId);
        log.debug("Mock configurado: deleteById({}) lanzará EmptyResultDataAccessException", nonExistentId);

        assertThrows(EmptyResultDataAccessException.class, () -> {
            tiendaService.deleteTienda(nonExistentId);
        });
        log.info("Se lanzó la excepción esperada al eliminar ID {}", nonExistentId);

        verify(tiendaRepository, times(1)).deleteById(nonExistentId);
    }
}
