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
import com.example.HardBoiledEgg.model.Envio;
import com.example.HardBoiledEgg.model.Venta;
import com.example.HardBoiledEgg.repository.envioRepository;
import com.example.HardBoiledEgg.service.direccionService;

import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;




@SpringBootTest
@ActiveProfiles("test")
public class EnvioServiceTest {

    @Autowired
    private com.example.HardBoiledEgg.service.envioService envioService;

    @MockitoBean
    private envioRepository enviorepository;

    @Test
    public void testFindAll(){

        Venta venta = new Venta();
        venta.setId(1);
        venta.setMonto(20000);
        venta.setInventario(null);
        venta.setCliente(null);

        Direccion direccion = new Direccion() {
            
        };

        List<Envio> envios = envioService.getEnvios();


    
        when(enviorepository.findAll()).thenReturn(List.<Envio>of(new Envio(1,Date.valueOf("21"),)));



        assertNotNull(envios);
        assertEquals(1,envios.size());
    }
}
