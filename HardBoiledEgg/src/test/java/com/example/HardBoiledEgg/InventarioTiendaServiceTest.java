package com.example.HardBoiledEgg;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.HardBoiledEgg.model.InventarioTienda;
import com.example.HardBoiledEgg.repository.inventariotiendaRepository;
import com.example.HardBoiledEgg.service.inventariotiendaService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j

public class InventarioTiendaServiceTest {
    
    @Autowired
    private inventariotiendaService inventariotiendaService;

    @MockitoBean 
    private inventariotiendaRepository inventariotiendaRepository;

    @Test
public void whenGetAllInventariosTienda_thenReturnList() {
    log.info("Test: Obtener todos los InventariosTienda");
    
    InventarioTienda inventarioTienda1 = new InventarioTienda(1,12,6543,null,null,null);
    InventarioTienda inventarioTienda2 = new InventarioTienda(2,234,7654,null,null,null);
    
    when(inventariotiendaRepository.findAll()).thenReturn(List.of(inventarioTienda1, inventarioTienda2));
    log.debug("Mock configurado: findAll() retornará 2 inventarios de tienda");

    List<InventarioTienda> result = inventariotiendaService.getInventarioTienda();
    
    assertEquals(2, result.size());
    verify(inventariotiendaRepository, times(1)).findAll();
    log.info("Resultado: {} inventarios de tienda obtenidos", result.size());
}

// ---- Tests para getInventarioTiendaById() ----
@Test
public void whenValidId_thenReturnInventarioTienda() {
    log.info("Test: Obtener inventario de tienda por ID válido");
    
    InventarioTienda inventarioTienda = new InventarioTienda(1,12,6543,null,null,null);
    when(inventariotiendaRepository.findById(1)).thenReturn(Optional.of(inventarioTienda));
    log.debug("Mock configurado: findById(1) retornará inventario de tienda");

    InventarioTienda result = inventariotiendaService.getInventarioTiendaById(1);
    
    assertNotNull(result);
    assertEquals(6543, result.getPrecioLocal());
    verify(inventariotiendaRepository, times(1)).findById(1);
}

@Test
public void whenInvalidId_thenReturnNull() {
    log.info("Test: Obtener inventario de tienda con ID inválido");
    
    when(inventariotiendaRepository.findById(99)).thenReturn(Optional.empty());
    log.debug("Mock configurado: findById(99) retornará Optional.empty");

    InventarioTienda result = inventariotiendaService.getInventarioTiendaById(99);
    
    assertNull(result);
    verify(inventariotiendaRepository, times(1)).findById(99);
}

// ---- Tests para createInventarioTienda() ----
@Test
public void whenCreateInventarioTienda_thenReturnSavedInventarioTienda() {
    log.info("Test: Crear nuevo inventario de tienda");
    
    InventarioTienda newInventarioTienda = new InventarioTienda(1,12,6543,null,null,null);
    InventarioTienda savedInventarioTienda = new InventarioTienda(2,23,8766,null,null,null);
    
    when(inventariotiendaRepository.save(newInventarioTienda)).thenReturn(savedInventarioTienda);
    log.debug("Mock configurado: save() retornará inventario con ID generado");

    InventarioTienda result = inventariotiendaService.createInventarioTienda(newInventarioTienda);
    
    assertNotNull(result.getId());
    assertEquals(6543, result.getPrecioLocal());
    verify(inventariotiendaRepository, times(1)).save(newInventarioTienda);
}

// ---- Tests para updateInventarioTienda() ----
@Test
public void whenUpdateExistingInventarioTienda_thenReturnUpdated() {
    log.info("Test: Actualizar InventarioTienda existente");
    
    InventarioTienda existing = new InventarioTienda(1,12,6543,null,null,null);
    InventarioTienda updatedData = new InventarioTienda(4,876,34567,null,null,null);
    
    when(inventariotiendaRepository.existsById(1)).thenReturn(true);
    when(inventariotiendaRepository.save(updatedData)).thenReturn(updatedData);
    when(inventariotiendaRepository.getReferenceById(1)).thenReturn(updatedData);
    log.debug("Mocks configurados para update");

    InventarioTienda result = inventariotiendaService.updateInventarioTienda(updatedData, 1);

    assertEquals(876, result.getStock());
    verify(inventariotiendaRepository, times(1)).save(updatedData);
}

@Test
public void whenUpdateNonExistingInventarioTienda_thenReturnNull() {
    log.info("Test: Actualizar inventario de tienda inexistente");
    
    InventarioTienda data = new InventarioTienda(1,12,6543,null,null,null);
    when(inventariotiendaRepository.existsById(99)).thenReturn(false);
    log.debug("Mock configurado: existsById(99)=false");

    InventarioTienda result = inventariotiendaService.updateInventarioTienda(data, 99);
    
    assertNull(result);
    verify(inventariotiendaRepository, never()).save(any());
}

// ---- Tests para deleteInventarioTienda() ----
@Test
public void whenDeleteExistingInventarioTienda_thenNoException() {
    log.info("Test: Eliminar inventario de tienda existente");
    int id = 1;
    
    doNothing().when(inventariotiendaRepository).deleteById(id);
    log.debug("Mock configurado: deleteById() no hará nada");

    assertDoesNotThrow(() -> inventariotiendaService.deleteInventarioTienda(id));
    verify(inventariotiendaRepository, times(1)).deleteById(id);
}

@Test
public void SiNoExisteLanzarErrorAlEliminarInventarioTienda() {
    int nonExistentId = 999;
    log.info("Configurando test para eliminar inventario de tienda con ID inexistente: {}", nonExistentId);

    doThrow(EmptyResultDataAccessException.class)
        .when(inventariotiendaRepository)
        .deleteById(nonExistentId);
    log.debug("Mock configurado: deleteById({}) lanzará EmptyResultDataAccessException", nonExistentId);

    assertThrows(EmptyResultDataAccessException.class, () -> {
        inventariotiendaService.deleteInventarioTienda(nonExistentId);
    });
    log.info("Se lanzó la excepción esperada al eliminar ID {}", nonExistentId);

    verify(inventariotiendaRepository, times(1)).deleteById(nonExistentId);
}    


}
