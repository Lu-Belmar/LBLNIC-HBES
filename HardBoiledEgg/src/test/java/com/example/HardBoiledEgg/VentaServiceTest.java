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

import com.example.HardBoiledEgg.model.Venta;
import com.example.HardBoiledEgg.repository.ventaRepository;
import com.example.HardBoiledEgg.service.ventaService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class VentaServiceTest {
    @Autowired
    private ventaService ventaService;

    @MockitoBean
    private ventaRepository ventaRepository;

@Test
public void whenGetAllVentas_thenReturnList() {
    log.info("Test: Obtener todas las Ventas");
    
    Venta venta1 = new Venta(1,123,null,null);
    Venta venta2 = new Venta(2,6543,null,null);
    
    when(ventaRepository.findAll()).thenReturn(List.of(venta1, venta2));
    log.debug("Mock configurado: findAll() retornará 2 ventas");

    List<Venta> result = ventaService.getVentas();
    
    assertEquals(2, result.size());
    verify(ventaRepository, times(1)).findAll();
    log.info("Resultado: {} ventas obtenidas", result.size());
}

// ---- Tests para getVentaById() ----
@Test
public void whenValidId_thenReturnVenta() {
    log.info("Test: Obtener venta por ID válido");
    
    Venta venta = new Venta(1,123,null,null);
    when(ventaRepository.findById(1)).thenReturn(Optional.of(venta));
    log.debug("Mock configurado: findById(1) retornará venta");

    Venta result = ventaService.getVentaById(1);
    
    assertNotNull(result);
    assertEquals(123, result.getMonto());
    verify(ventaRepository, times(1)).findById(1);
}

@Test
public void whenInvalidId_thenReturnNull() {
    log.info("Test: Obtener venta con ID inválido");
    
    when(ventaRepository.findById(99)).thenReturn(Optional.empty());
    log.debug("Mock configurado: findById(99) retornará Optional.empty");

    Venta result = ventaService.getVentaById(99);
    
    assertNull(result);
    verify(ventaRepository, times(1)).findById(99);
}

// ---- Tests para createVenta() ----
@Test
public void whenCreateVenta_thenReturnSavedVenta() {
    log.info("Test: Crear nueva venta");
    
    Venta newVenta = new Venta(1,123,null,null);
    Venta savedVenta = new Venta(3,654,null,null);
    
    when(ventaRepository.save(newVenta)).thenReturn(savedVenta);
    log.debug("Mock configurado: save() retornará venta con ID generado");

    Venta result = ventaService.createVenta(newVenta);
    
    assertNotNull(result.getId());
    assertEquals(654, result.getMonto());
    verify(ventaRepository, times(1)).save(newVenta);
}

// ---- Tests para updateVenta() ----
@Test
public void whenUpdateExistingVenta_thenReturnUpdated() {
    log.info("Test: Actualizar Venta existente");
    
    Venta existing = new Venta(1,123,null,null);
    Venta updatedData = new Venta(2,765,null,null);
    
    when(ventaRepository.existsById(1)).thenReturn(true);
    when(ventaRepository.save(updatedData)).thenReturn(updatedData);
    when(ventaRepository.getReferenceById(1)).thenReturn(updatedData);
    log.debug("Mocks configurados para update");

    Venta result = ventaService.updateVenta(updatedData, 1);

    assertEquals(765, result.getMonto());
    verify(ventaRepository, times(1)).save(updatedData);
}

@Test
public void whenUpdateNonExistingVenta_thenReturnNull() {
    log.info("Test: Actualizar venta inexistente");
    
    Venta data = new Venta(99,123,null,null);
    when(ventaRepository.existsById(99)).thenReturn(false);
    log.debug("Mock configurado: existsById(99)=false");

    Venta result = ventaService.updateVenta(data, 99);
    
    assertNull(result);
    verify(ventaRepository, never()).save(any());
}

// ---- Tests para deleteVenta() ----
@Test
public void whenDeleteExistingVenta_thenNoException() {
    log.info("Test: Eliminar venta existente");
    int id = 1;
    
    doNothing().when(ventaRepository).deleteById(id);
    log.debug("Mock configurado: deleteById() no hará nada");

    assertDoesNotThrow(() -> ventaService.deleteVenta(id));
    verify(ventaRepository, times(1)).deleteById(id);
}

@Test
public void whenNonExistentVenta_thenThrowExceptionOnDelete() {
    int nonExistentId = 999;
    log.info("Configurando test para eliminar venta con ID inexistente: {}", nonExistentId);

    doThrow(EmptyResultDataAccessException.class)
        .when(ventaRepository)
        .deleteById(nonExistentId);
    log.debug("Mock configurado: deleteById({}) lanzará EmptyResultDataAccessException", nonExistentId);

    assertThrows(EmptyResultDataAccessException.class, () -> {
        ventaService.deleteVenta(nonExistentId);
    });
    log.info("Se lanzó la excepción esperada al eliminar ID {}", nonExistentId);

    verify(ventaRepository, times(1)).deleteById(nonExistentId);
}
}
