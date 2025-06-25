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

import com.example.HardBoiledEgg.model.Cliente;
import com.example.HardBoiledEgg.repository.clienteRepository;
import com.example.HardBoiledEgg.service.clienteService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class ClienteSeviceTest {

    @Autowired
    private clienteService clienteService;

    @MockitoBean
    private clienteRepository clienteRepository;

@Test
public void whenGetAllClientes_thenReturnList() {
    log.info("Test: Obtener todas los Clientes");
    
    Cliente cliente1 = new Cliente(1,null, null);
    Cliente cliente2 = new Cliente(2,null,null);
    
    when(clienteRepository.findAll()).thenReturn(List.of(cliente1, cliente2));
    log.debug("Mock configurado: findAll() retornará 2 clientes");

    List<Cliente> result = clienteService.getCliente();
    
    assertEquals(2, result.size());
    verify(clienteRepository, times(1)).findAll();
    log.info("Resultado: {} clientes obtenidos", result.size());
}

// ---- Tests para getClienteById() ----
@Test
public void whenValidId_thenReturnCliente() {
    log.info("Test: Obtener cliente por ID válido");
    
    Cliente cliente = new Cliente(1,null, null);
    when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
    log.debug("Mock configurado: findById(1) retornará cliente");

    Cliente result = clienteService.getClienteById(1);
    
    assertNotNull(result);
    assertEquals("dfsdfsfs", result.getDireccion());
    verify(clienteRepository, times(1)).findById(1);
}

@Test
public void whenInvalidId_thenReturnNull() {
    log.info("Test: Obtener cliente con ID inválido");
    
    when(clienteRepository.findById(99)).thenReturn(Optional.empty());
    log.debug("Mock configurado: findById(99) retornará Optional.empty");

    Cliente result = clienteService.getClienteById(99);
    
    assertNull(result);
    verify(clienteRepository, times(1)).findById(99);
}

// ---- Tests para createCliente() ----
@Test
public void whenCreateCliente_thenReturnSavedCliente() {
    log.info("Test: Crear nuevo cliente");
    
    Cliente newCliente = new Cliente(1,null, null);
    Cliente savedCliente = new Cliente(4,null, null);
    
    when(clienteRepository.save(newCliente)).thenReturn(savedCliente);
    log.debug("Mock configurado: save() retornará cliente con ID generado");

    Cliente result = clienteService.createCliente(newCliente);
    
    assertNotNull(result.getId());
    assertEquals("correosa", result.getCorreo());
    verify(clienteRepository, times(1)).save(newCliente);
}

// ---- Tests para updateCliente() ----
@Test
public void whenUpdateExistingCliente_thenReturnUpdated() {
    log.info("Test: Actualizar Cliente existente");
    
    Cliente existing = new Cliente(1,null, null);
    Cliente updatedData = new Cliente(6,null, null);
    
    when(clienteRepository.existsById(1)).thenReturn(true);
    when(clienteRepository.save(updatedData)).thenReturn(updatedData);
    when(clienteRepository.getReferenceById(1)).thenReturn(updatedData);
    log.debug("Mocks configurados para update");

    Cliente result = clienteService.updateCliente(updatedData, 1);

    assertEquals("nanna", result.getCorreo());
    verify(clienteRepository, times(1)).save(updatedData);
}

@Test
public void whenUpdateNonExistingCliente_thenReturnNull() {
    log.info("Test: Actualizar cliente inexistente");
    
    Cliente data = new Cliente(1,null, null);
    when(clienteRepository.existsById(99)).thenReturn(false);
    log.debug("Mock configurado: existsById(99)=false");

    Cliente result = clienteService.updateCliente(data, 99);
    
    assertNull(result);
    verify(clienteRepository, never()).save(any());
}

// ---- Tests para deleteCliente() ----
@Test
public void whenDeleteExistingCliente_thenNoException() {
    log.info("Test: Eliminar cliente existente");
    int id = 1;
    
    doNothing().when(clienteRepository).deleteById(id);
    log.debug("Mock configurado: deleteById() no hará nada");

    assertDoesNotThrow(() -> clienteService.deleteCliente(id));
    verify(clienteRepository, times(1)).deleteById(id);
}

@Test
public void SiNoExisteLanzarErrorAlEliminarCliente() {
    int nonExistentId = 999;
    log.info("Configurando test para eliminar cliente con ID inexistente: {}", nonExistentId);

    doThrow(EmptyResultDataAccessException.class)
        .when(clienteRepository)
        .deleteById(nonExistentId);
    log.debug("Mock configurado: deleteById({}) lanzará EmptyResultDataAccessException", nonExistentId);

    assertThrows(EmptyResultDataAccessException.class, () -> {
        clienteService.deleteCliente(nonExistentId);
    });
    log.info("Se lanzó la excepción esperada al eliminar ID {}", nonExistentId);

    verify(clienteRepository, times(1)).deleteById(nonExistentId);
}
}
