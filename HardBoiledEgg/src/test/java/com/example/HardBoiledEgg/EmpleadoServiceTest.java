package com.example.HardBoiledEgg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import lombok.extern.slf4j.Slf4j;
import com.example.HardBoiledEgg.service.productoService;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import static org.mockito.Mockito.*;

import com.example.HardBoiledEgg.repository.productoRepository;
import com.example.HardBoiledEgg.repository.proveedoresRepository;
import com.example.HardBoiledEgg.model.Categorias;
import com.example.HardBoiledEgg.model.Direccion;
import com.example.HardBoiledEgg.model.DireccionEmpleado;
import com.example.HardBoiledEgg.model.Empleado;
import com.example.HardBoiledEgg.model.Producto;
import com.example.HardBoiledEgg.model.Proveedores;
import com.example.HardBoiledEgg.repository.categoriasRepository;
import com.example.HardBoiledEgg.service.empleadoService;
import com.example.HardBoiledEgg.repository.empleadoRepository;


@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class EmpleadoServiceTest {

    @Autowired
    private empleadoService empleadoService;

    @MockitoBean
    private empleadoRepository empleadoRepository;

    // ---- Helper Methods ----
    private Empleado createSampleEmpleado(Integer id) {
        DireccionEmpleado direccion = new DireccionEmpleado();
        direccion.setId(1);
        direccion.setCalle("Calle Principal 123");
        direccion.setCiudad("Santiago");
        direccion.setRegion("RM");
        
        Empleado empleado = new Empleado();
        empleado.setId(id);
        empleado.setRun(12345678);
        empleado.setNombre("Juan Pérez");
        empleado.setCorreo("juan@empresa.com");
        empleado.setTelefono(912345678);
        empleado.setSalario(3000000);
        empleado.setDireccion(direccion);

        return empleado;
    }

    // ---- Tests para getEmpleados() ----
    @Test
    public void whenGetAllEmpleados_thenReturnList() {
        log.info("Test: Obtener todos los empleados");
        
        Empleado empleado1 = createSampleEmpleado(1);
        Empleado empleado2 = createSampleEmpleado(2);
        
        when(empleadoRepository.findAll()).thenReturn(List.of(empleado1, empleado2));

        List<Empleado> result = empleadoService.getEmpleados();
        
        assertEquals(2, result.size());
        assertEquals("Juan Pérez", result.get(0).getNombre());
        verify(empleadoRepository, times(1)).findAll();
    }

    // ---- Tests para getEmpleadoById() ----
    @Test
    public void whenValidId_thenReturnEmpleado() {
        log.info("Test: Obtener empleado por ID válido");
        
        Empleado empleado = createSampleEmpleado(1);
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));

        Empleado result = empleadoService.getEmpleadoById(1);
        
        assertNotNull(result);
        assertEquals(3000000, result.getSalario());
        assertEquals("Santiago", result.getDireccion().getCiudad());
        verify(empleadoRepository, times(1)).findById(1);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());
        assertNull(empleadoService.getEmpleadoById(99));
    }

    // ---- Tests para createEmpleado() ----
    @Test
    @Transactional
    public void whenCreateEmpleado_thenReturnSavedEmpleado() {
        log.info("Test: Crear nuevo empleado con dirección");
        
        DireccionEmpleado direccion = new DireccionEmpleado();
        direccion.setCalle("Nueva Calle 456");
        direccion.setCiudad("Valparaíso");
        direccion.setRegion("V Región");
        
        Empleado newEmpleado = new Empleado();
        newEmpleado.setRun(87654321);
        newEmpleado.setNombre("Ana López");
        newEmpleado.setCorreo("ana@empresa.com");
        newEmpleado.setSalario(2800000);
        newEmpleado.setDireccion(direccion);

        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(invocation -> {
            Empleado emp = invocation.getArgument(0);
            emp.setId(1); // Simular ID generado
            return emp;
        });

        Empleado result = empleadoService.createEmpleado(newEmpleado);
        
        assertNotNull(result.getId());
        assertEquals("Valparaíso", result.getDireccion().getCiudad());
        verify(empleadoRepository, times(1)).save(any());
    }

    // ---- Tests para updateEmpleado() ----
    @Test
    @Transactional
    public void whenUpdateEmpleado_thenReturnUpdated() {
        log.info("Test: Actualizar salario de empleado");
        
        Empleado existing = createSampleEmpleado(1);
        
        Empleado updatedData = new Empleado();
        updatedData.setSalario(4000000); // Solo actualizamos el salario

        when(empleadoRepository.existsById(1)).thenReturn(true);
        when(empleadoRepository.getReferenceById(1)).thenReturn(existing);
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });

        Empleado result = empleadoService.updateEmpleado(updatedData, 1);
        
        assertEquals(4000000, result.getSalario());
        assertEquals("Juan Pérez", result.getNombre()); // Nombre no cambió
        verify(empleadoRepository, times(1)).save(any());
    }

    // ---- Tests para deleteEmpleado() ----
    @Test
    public void whenDeleteEmpleado_thenRepositoryCalled() {
        doNothing().when(empleadoRepository).deleteById(1);
        empleadoService.deleteEmpleado(1);
        verify(empleadoRepository, times(1)).deleteById(1);
    }

    @Test
    public void whenDeleteNonExistingEmpleado_thenThrowException() {
        doThrow(EmptyResultDataAccessException.class)
            .when(empleadoRepository)
            .deleteById(99);
        
        assertThrows(EmptyResultDataAccessException.class, 
            () -> empleadoService.deleteEmpleado(99));
    }
}