package com.example.HardBoiledEgg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import lombok.extern.slf4j.Slf4j;
import com.example.HardBoiledEgg.service.productoService;

import jakarta.transaction.Transactional;
import static org.mockito.Mockito.*;

import com.example.HardBoiledEgg.repository.productoRepository;
import com.example.HardBoiledEgg.repository.proveedoresRepository;
import com.example.HardBoiledEgg.model.Categorias;
import com.example.HardBoiledEgg.model.Producto;
import com.example.HardBoiledEgg.model.Proveedores;
import com.example.HardBoiledEgg.repository.categoriasRepository;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class ProductoServiceTest {

    @Autowired
    private productoService productoservice;

    @MockitoBean
    private productoRepository productorepository;

    @MockitoBean
    private proveedoresRepository proveedoresrepository;

    @MockitoBean
    private categoriasRepository categoriasrepository;

    // ---- Tests para createProducto ----
    @Test
    @Transactional
    public void whenCreateProducto_thenReturnSavedProducto() {
        log.info("Test: Crear producto con categoría y proveedor");
        
        Categorias categoria = new Categorias(1, "Electrónicos", "Dispositivos electrónicos", null);
        Proveedores proveedor = new Proveedores(1, "Luis nunez",12342535,null);
        Producto newProducto = new Producto(null, "Laptop", "Dell", proveedor, categoria, null);

        when(proveedoresrepository.findById(1)).thenReturn(Optional.of(proveedor));
        when(categoriasrepository.findById(1)).thenReturn(Optional.of(categoria));
        when(productorepository.save(any(Producto.class))).thenReturn(newProducto);

        Producto saved = productoservice.createProducto(newProducto);


        assertNotNull(saved);
        assertEquals("Laptop", saved.getNombre());
        verify(productorepository, times(1)).save(any());
        log.info("Producto creado: ID={}, Nombre={}", saved.getId(), saved.getNombre());
    }

    @Test
    public void whenValidId_thenReturnProducto() {
        log.info("Test: Obtener producto por ID válido");
        
        Producto producto = new Producto(1, "Teclado", "Logitech", null, null, null);
        when(productorepository.findById(1)).thenReturn(Optional.of(producto));

        Producto found = productoservice.getProductoById(1);

        assertNotNull(found);
        assertEquals("Teclado", found.getNombre());
        verify(productorepository, times(1)).findById(1);
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        when(productorepository.findById(99)).thenReturn(Optional.empty());
        assertNull(productoservice.getProductoById(99));
    }

    @Test
    @Transactional
    public void whenUpdateProducto_thenReturnUpdated() {
        log.info("Test: Actualizar marca de producto");
        
        Producto existing = new Producto(1, "Mouse", "Genérico", null, null, null);
        

        Producto updatedData = new Producto(1, "Mouse", "Razer", null, null, null);


        when(productorepository.existsById(1)).thenReturn(true);
        when(productorepository.getReferenceById(1)).thenReturn(existing);
        when(productorepository.save(any(Producto.class))).thenAnswer(invocation -> {
            Producto productoToSave = invocation.getArgument(0);
            return productoToSave; 
        });

        Producto result = productoservice.updateProducto(updatedData, 1);

        assertNotNull(result);
        assertEquals("Razer", result.getMarca());
        verify(productorepository, times(1)).save(any());
    }

    @Test
    public void whenDeleteProducto_thenRepositoryCalled() {
        doNothing().when(productorepository).deleteById(1);
        productoservice.deleteProducto(1);
        verify(productorepository, times(1)).deleteById(1);
    }

    @Test
    @Transactional
    public void whenProductoWithCategoria_thenRelationPersisted() {
        log.info("Test: Relación Producto-Categoría");
        
        Categorias categoria = new Categorias(1, "Electrónicos", "Desc", null);
        Producto producto = new Producto(null, "Tablet", "Samsung", null, categoria, null);

        when(categoriasrepository.findById(1)).thenReturn(Optional.of(categoria));
        when(productorepository.save(any())).thenReturn(producto);

        Producto saved = productoservice.createProducto(producto);

        assertNotNull(saved.getCategoria());
        assertEquals("Electrónicos", saved.getCategoria().getNombre());
        log.info("Relación verificada: Producto {} - Categoría {}", 
            saved.getNombre(), saved.getCategoria().getNombre());
    }
}