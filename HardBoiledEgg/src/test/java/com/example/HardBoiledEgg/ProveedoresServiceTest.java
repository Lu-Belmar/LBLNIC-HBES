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
import com.example.HardBoiledEgg.model.Proveedores;
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

import com.example.HardBoiledEgg.repository.proveedoresRepository;
import com.example.HardBoiledEgg.service.proveedoresService; 

import com.example.HardBoiledEgg.repository.envioRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ProveedoresServiceTest {

    @Autowired
    private proveedoresService proveedoresService;

    @MockitoBean
    private proveedoresRepository proveedoresRepository;

    @Test
    public void testFindAll(){

        Proveedores proveedorMock = new Proveedores();
        proveedorMock.setTelefono(20202020);
        proveedorMock.setId(1);
        proveedorMock.setNombre("sasahjgdjh");
        
        when(proveedoresRepository.findAll()).thenReturn(List.of(proveedorMock));

        List<Proveedores> proveedores = proveedoresService.getProveedores();

        assertNotNull(proveedores);
        assertEquals(1, proveedores.size());
    }

}
