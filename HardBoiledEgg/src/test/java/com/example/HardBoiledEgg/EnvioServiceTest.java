package com.example.HardBoiledEgg;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.HardBoiledEgg.model.Direccion;
import com.example.HardBoiledEgg.model.DireccionCliente;
import com.example.HardBoiledEgg.model.Envio;
import com.example.HardBoiledEgg.model.Venta;
import com.example.HardBoiledEgg.repository.envioRepository;
import com.example.HardBoiledEgg.service.direccionService;

import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @MockitoBean  // Corregido: @MockBean en lugar de @MockitoBean
    private envioRepository enviorepository;

    @Test
    public void testFindAll() {
        // 1. Configuración del mock
        Venta venta = new Venta();
        venta.setId(1);
        venta.setMonto(20000);

        Envio envioMock = new Envio(); // O usa un builder si lo tienes
        envioMock.setId(1);
        envioMock.setVenta(venta);

        log.info("Configurando mock: envioRepository.findAll() retornará {}", List.of(envioMock));

        // Configura el comportamiento del mock ANTES de llamar al servicio
        when(enviorepository.findAll()).thenReturn(List.of(envioMock));

        // 2. Ejecutar el método a probar
        log.info("Ejecutando envioService.getEnvios()...");
        List<Envio> envios = envioservice.getEnvios();

        // 3. Verificaciones
        assertNotNull(envios);
        assertEquals(1, envios.size());
        log.info("Test OK. Envíos obtenidos: {}", envios);
    }

    
}