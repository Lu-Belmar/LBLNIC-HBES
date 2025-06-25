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



@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class EnvioServiceTest {

    @Autowired
    private envioService envioservice;

    @MockitoBean
    private envioRepository enviorepository;

    @Test
    public void testFindAll() {

        Venta venta = new Venta();
        venta.setId(1);
        venta.setMonto(20000);

        Envio envioMock = new Envio();
        envioMock.setId(1);
        envioMock.setVenta(venta);

        log.info("Configurando mock: envioRepository.findAll() retornará {}", List.of(envioMock));


        when(enviorepository.findAll()).thenReturn(List.of(envioMock));


        log.info("Ejecutando envioService.getEnvios()...");
        List<Envio> envios = envioservice.getEnvios();

        assertNotNull(envios);
        assertEquals(1, envios.size());
        log.info("Test OK. Envíos obtenidos: {}", envios);
    }
    @Test
    public void EnvioIdValida() {
        Envio envioMock = new Envio();
        envioMock.setId(1);
        
        when(enviorepository.findById(1)).thenReturn(Optional.of(envioMock));

        Envio found = envioservice.getEnvioById(1);

        assertNotNull(found);
        assertEquals(1, found.getId());
        verify(enviorepository, times(1)).findById(1);
    }

    @Test
    public void EnvioIdNoValida() {
        when(enviorepository.findById(99)).thenReturn(Optional.empty());

        Envio result = envioservice.getEnvioById(99);

        assertNull(result);
        verify(enviorepository, times(1)).findById(99);
    }

    @Test
    public void EnvioCreado() {
        log.info("Iniciando test: createEnvio...");
        Envio newEnvio = new Envio();
        newEnvio.setDireccion("Calle Falsa 123");
        log.debug("Envio a crear: {}", newEnvio);

        when(enviorepository.save(newEnvio)).thenReturn(newEnvio);
        log.debug("Mock configurado: envioRepository.save() retornará {}", newEnvio);

        Envio savedEnvio = envioservice.createEnvio(newEnvio);
        log.info("Envio guardado: {}", savedEnvio);

        assertNotNull(savedEnvio);
        assertEquals("Calle Falsa 123", savedEnvio.getDireccion());
        verify(enviorepository, times(1)).save(newEnvio);
        log.info("Test createEnvio exitoso ");
    }
    @Test
    public void SiExisteActualizarEnvio() {
        log.info("Iniciando test: updateEnvio...");
        Envio existingEnvio = new Envio();
        existingEnvio.setId(1);
        existingEnvio.setDireccion("Dirección Antigua");

        Envio updatedData = new Envio();
        updatedData.setId(1);
        updatedData.setDireccion("Dirección Nueva");
        log.debug("Datos actualizados: {}", updatedData);

        when(enviorepository.existsById(1)).thenReturn(true);
        when(enviorepository.save(updatedData)).thenReturn(updatedData);
        when(enviorepository.getReferenceById(1)).thenReturn(updatedData);
        log.debug("Mocks configurados para updateEnvio");

        Envio result = envioservice.updateEnvio(updatedData, 1);
        log.info("Resultado de actualizacion: {}", result);

        assertNotNull(result);
        assertEquals("Dirección Nueva", result.getDireccion());
        verify(enviorepository, times(1)).save(updatedData);
        log.info("Test updateEnvio exitoso");
    }

    @Test
    public void SiNoExisteNoActualizarEnvio() {
        Envio envio = new Envio();
        envio.setId(99);

        when(enviorepository.existsById(99)).thenReturn(false);

        Envio result = envioservice.updateEnvio(envio, 99);

        assertNull(result);
        verify(enviorepository, never()).save(any());
    }

    @Test
    public void SiExisteEliminarEnvio() {
        doNothing().when(enviorepository).deleteById(1);

        envioservice.deleteEnvio(1);

        verify(enviorepository, times(1)).deleteById(1);
    }

    @Test
    public void SiNoExisteLanzarErrorAlEliminarEnvio() {
        int nonExistentId = 999;
        log.info("Configurando test para eliminar envío con ID inexistente: {}", nonExistentId);

        doThrow(EmptyResultDataAccessException.class)
            .when(enviorepository)
            .deleteById(nonExistentId);
        log.debug("Mock configurado: deleteById({}) lanzará EmptyResultDataAccessException", nonExistentId);

        assertThrows(EmptyResultDataAccessException.class, () -> {
            envioservice.deleteEnvio(nonExistentId);
        });
        log.info("Se lanzó la excepción esperada al eliminar ID {}", nonExistentId);

        verify(enviorepository, times(1)).deleteById(nonExistentId);
    }

    
}