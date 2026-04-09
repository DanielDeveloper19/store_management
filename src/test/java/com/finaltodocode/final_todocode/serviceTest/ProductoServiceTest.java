package com.finaltodocode.final_todocode.serviceTest;

import com.finaltodocode.final_todocode.model.Producto;
import com.finaltodocode.final_todocode.repository.ProductoRepo;
import com.finaltodocode.final_todocode.service.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepo productoRepo;

    @InjectMocks
    private ProductoService productoService;

    @Test
    @DisplayName("Should increase stock correctly when sumarUnidades is called")
    void sumarUnidadesTest() {
        // Arrange
        Long id = 101L;
        Producto productoExistente = new Producto(id, "Laptop", "BrandX", 1000.0, 5.0);
        when(productoRepo.findById(id)).thenReturn(Optional.of(productoExistente));

        // Act
        productoService.sumarUnidades(id, 10);

        // Assert
        assertEquals(15.0, productoExistente.getCantidadDisponible());
        verify(productoRepo, times(1)).save(productoExistente);
    }

    @Test
    @DisplayName("Should filter products using the '>' operator correctly")
    void buscarProductosConCantidadDisponibleTest() {
        // Arrange
        Producto p1 = new Producto(1L, "Teclado", "A", 20.0, 5.0);
        Producto p2 = new Producto(2L, "Mouse", "B", 10.0, 15.0);
        Producto p3 = new Producto(3L, "Monitor", "C", 200.0, 25.0);

        when(productoRepo.findAll()).thenReturn(Arrays.asList(p1, p2, p3));

        // Act
        List<Producto> resultado = productoService.buscarProductosConCantidadDisponible(10, ">");

        // Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(p2));
        assertTrue(resultado.contains(p3));
        assertFalse(resultado.contains(p1));
    }

    @Test
    @DisplayName("Should throw exception when logic operator is invalid")
    void buscarProductosOperadorInvalidoTest() {
        // Arrange
        when(productoRepo.findAll()).thenReturn(List.of(new Producto()));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.buscarProductosConCantidadDisponible(10, "INVALID_OP");
        });

        assertTrue(exception.getMessage().contains("Operador lógico no reconocido"));
    }

    @Test
    @DisplayName("Should update only provided product fields")
    void actualizarProductoTest() {
        // Arrange
        Long id = 1L;
        Producto original = new Producto(id, "Original", "Marca", 50.0, 10.0);
        Producto updates = new Producto(id, "New Name", null, 75.0, null);

        when(productoRepo.findById(id)).thenReturn(Optional.of(original));

        // Act
        productoService.actualizar(updates);

        // Assert
        assertEquals("New Name", original.getNombre());
        assertEquals("Marca", original.getMarca()); // Remained same
        assertEquals(75.0, original.getPrecio());
        assertEquals(10.0, original.getCantidadDisponible()); // Remained same
        verify(productoRepo).save(original);
    }
}