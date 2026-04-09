package com.finaltodocode.final_todocode.serviceTest;



import com.finaltodocode.final_todocode.model.*;
import com.finaltodocode.final_todocode.repository.*;
import com.finaltodocode.final_todocode.service.VentaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepo ventaRepo;
    @Mock
    private ProductoRepo productoRepo;
    @Mock
    private ClienteRepo clienteRepo;

    @InjectMocks
    private VentaService ventaService;

    @Test
    @DisplayName("Debe fallar si no hay suficiente stock para la venta")
    void guardarVentaSinStockTest() {
        // Arrange
        Producto laptop = new Producto(1L, "Laptop", "HP", 1000.0, 2.0); // Solo hay 2
        VentaProducto vp = new VentaProducto();
        vp.setProducto(laptop);
        vp.setCantidadAComprar(5); // Intenta comprar 5

        Venta venta = new Venta();
        venta.setProductos(List.of(vp));

        when(productoRepo.findById(1L)).thenReturn(Optional.of(laptop));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.guardar(venta);
        });

        assertTrue(exception.getMessage().contains("unidades disponibles"));
        verify(ventaRepo, never()).save(any());
    }

    @Test
    @DisplayName("Debe calcular total y descontar stock correctamente al guardar")
    void guardarVentaExitosaTest() {
        // Arrange
        Producto p1 = new Producto(1L, "Mouse", "Logitech", 50.0, 10.0);

        VentaProducto vp = new VentaProducto();
        vp.setProducto(p1);
        vp.setCantidadAComprar(2);

        Venta venta = new Venta();
        venta.setProductos(new ArrayList<>(List.of(vp)));

        when(productoRepo.findById(1L)).thenReturn(Optional.of(p1));

        // Act
        ventaService.guardar(venta);

        // Assert
        assertEquals(100.0, venta.getTotalVenta()); // 50.0 * 2
        assertEquals(8.0, p1.getCantidadDisponible()); // 10 - 2
        verify(productoRepo).save(p1);
        verify(ventaRepo).save(venta);
    }

    @Test
    @DisplayName("No debe permitir actualizar el total de una venta manualmente")
    void actualizarVentaTotalProhibidoTest() {
        // Arrange
        Venta ventaExistente = new Venta();
        ventaExistente.setCodigoVenta(1L);
        ventaExistente.setTotalVenta(500.0);

        Venta updates = new Venta();
        updates.setCodigoVenta(1L);
        updates.setTotalVenta(1000.0); // Intento de hackear el total

        when(ventaRepo.findById(1L)).thenReturn(Optional.of(ventaExistente));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.actualizar(updates);
        });

        assertEquals("El total de la venta no puede ser actualizado", exception.getMessage());
    }

    @Test
    @DisplayName("Debe retornar mensaje de error si el formato de fecha es inválido")
    void sumatoriaTotalFechaInvalidaTest() {
        // Act
        String resultado = ventaService.sumatoriaDelTotalyNumeroVentasEnUnDia("06-04-2026"); // Formato incorrecto

        // Assert
        assertTrue(resultado.contains("formato adecuado"));
        verify(ventaRepo, never()).findByFechaVenta(any());
    }
}