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
import com.example.HardBoiledEgg.service.proveedoresService;

import lombok.extern.slf4j.Slf4j;

import com.example.HardBoiledEgg.repository.envioRepository;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class ProveedoresServiceTest {
    @Autowired
    private proveedoresService proveedoresService;

    @MockitoBean
    private proveedoresRepository proveedoresRepository;

    // ---- Tests para getCategorias() ----
    @Test
    public void testFindAll() {
        log.info("Test: Obtener todas los proveedores");
        
        Proveedores proveedores1 = new Proveedores(1, "Electrónicos", 191919, null);
        Proveedores proveedores2 = new Proveedores(2, "Ropa", 232323, null);
        
        when(proveedoresRepository.findAll()).thenReturn(List.of(proveedores1, proveedores2));
        log.debug("Mock configurado: findAll() retornará 2 categorías");

        List<Proveedores> result = proveedoresService.getProveedores();
        
        assertEquals(2, result.size());
        verify(proveedoresRepository, times(1)).findAll();
        log.info("Resultado: {} categorías obtenidas", result.size());
    }

    // ---- Tests para getCategoriasById() ----
    @Test
    public void ProveedoresIdValida() {
        log.info("Test: Obtener categoría por ID válido");
        
        Proveedores proveedor = new Proveedores(1, "Electrónicos", 191919,null);
        when(proveedoresRepository.findById(1)).thenReturn(Optional.of(proveedor));
        log.debug("Mock configurado: findById(1) retornará categoría");

        Proveedores result = proveedoresService.getProveedoresById(1);
        
        assertNotNull(result);
        assertEquals("Electrónicos", result.getNombre());
        verify(proveedoresRepository, times(1)).findById(1);
    }

    @Test
    public void ProveedoresIdNoValida() {
        log.info("Test: Obtener categoría con ID inválido");
        
        when(proveedoresRepository.findById(99)).thenReturn(Optional.empty());
        log.debug("Mock configurado: findById(99) retornará Optional.empty");

        Proveedores result = proveedoresService.getProveedoresById(99);
        
        assertNull(result);
        verify(proveedoresRepository, times(1)).findById(99);
    }

    // ---- Tests para createCategorias() ----
    @Test
    public void ProveedoresCreado() {
        log.info("Test: Crear nueva categoría");
        
        Proveedores newProveedores = new Proveedores(null, "Alimentos", 191919, null);
        Proveedores savedProveedores = new Proveedores(1, "Alimentos", 1234, null);
        
        when(proveedoresRepository.save(newProveedores)).thenReturn(savedProveedores);
        log.debug("Mock configurado: save() retornará categoría con ID generado");

        Proveedores result = proveedoresService.createProveedores(newProveedores);
        
        assertNotNull(result.getId());
        assertEquals("Alimentos", result.getNombre());
        verify(proveedoresRepository, times(1)).save(newProveedores);
    }

    // ---- Tests para updateCategorias() ----
    @Test
    public void SiExisteActualizarProveedores() {
        log.info("Test: Actualizar categoría existente");
        
        Proveedores existing = new Proveedores(1, "Electrónicos", 1234, null);
        Proveedores updatedData = new Proveedores(1, "Tecnología", 1234233, null);
        
        when(proveedoresRepository.existsById(1)).thenReturn(true);
        when(proveedoresRepository.save(updatedData)).thenReturn(updatedData);
        when(proveedoresRepository.getReferenceById(1)).thenReturn(updatedData);
        log.debug("Mocks configurados para update");

        Proveedores result = proveedoresService.updateProveedores(updatedData, 1);
        
        assertEquals("Tecnología", result.getNombre());
        verify(proveedoresRepository, times(1)).save(updatedData);
    }

    @Test
    public void SiNoExisteNoActualizarProveedores() {
        log.info("Test: Actualizar categoría inexistente");
        
        Proveedores data = new Proveedores(99, "Inexistente", 87654, null);
        when(proveedoresRepository.existsById(99)).thenReturn(false);
        log.debug("Mock configurado: existsById(99)=false");

        Proveedores result = proveedoresService.updateProveedores(data, 99);
        
        assertNull(result);
        verify(proveedoresRepository, never()).save(any());
    }

    // ---- Tests para deleteCategorias() ----
    @Test
    public void SiExisteEliminarProveedores() {
        log.info("Test: Eliminar categoría existente");
        int id = 1;
        
        doNothing().when(proveedoresRepository).deleteById(id);
        log.debug("Mock configurado: deleteById() no hará nada");

        assertDoesNotThrow(() -> proveedoresService.deleteProveedores(id));
        verify(proveedoresRepository, times(1)).deleteById(id);
    }

    @Test
    public void SiNoExisteLanzarErrorAlEliminarProveedores() {
        int nonExistentId = 999;
        log.info("Configurando test para eliminar envío con ID inexistente: {}", nonExistentId);

        doThrow(EmptyResultDataAccessException.class)
            .when(proveedoresRepository)
            .deleteById(nonExistentId);
        log.debug("Mock configurado: deleteById({}) lanzará EmptyResultDataAccessException", nonExistentId);

        assertThrows(EmptyResultDataAccessException.class, () -> {
            proveedoresService.deleteProveedores(nonExistentId);
        });
        log.info("Se lanzó la excepción esperada al eliminar ID {}", nonExistentId);

        verify(proveedoresRepository, times(1)).deleteById(nonExistentId);
    }

}
