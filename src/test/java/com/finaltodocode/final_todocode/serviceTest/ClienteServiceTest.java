package com.finaltodocode.final_todocode.serviceTest;

import com.finaltodocode.final_todocode.model.Cliente;
import com.finaltodocode.final_todocode.repository.ClienteRepo;
import com.finaltodocode.final_todocode.service.ClienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepo clienteRepo;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Should save a client successfully")
    void guardarTest() {
        Cliente cliente = new Cliente(1L, "Daniel", "Zapata", "12345");

        clienteService.guardar(cliente);

        // Verify the repository was called once with the correct object
        verify(clienteRepo, times(1)).save(cliente);
    }

    @Test
    @DisplayName("Should update only provided fields when client exists")
    void actualizarExitosoTest() {
        // Arrange
        Long id = 1L;
        Cliente clienteExistente = new Cliente(id, "OldName", "OldApellido", "111");
        Cliente updates = new Cliente(id, "NewName", null, "222"); // Apellido is null

        when(clienteRepo.findById(id)).thenReturn(Optional.of(clienteExistente));

        // Act
        clienteService.actualizar(updates);

        // Assert
        assertEquals("NewName", clienteExistente.getNombre());
        assertEquals("OldApellido", clienteExistente.getApellido()); // Should NOT change
        assertEquals("222", clienteExistente.getDni());

        verify(clienteRepo, times(1)).save(clienteExistente);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent client")
    void actualizarFallidoTest() {
        // Arrange
        Cliente cliente = new Cliente(99L, "Inexistente", "User", "000");
        when(clienteRepo.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.actualizar(cliente);
        });

        assertEquals("Cliente no encontrado, asegurese de ingresar un id valido", exception.getMessage());
        verify(clienteRepo, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Should delete client by ID")
    void eliminarTest() {
        Long id = 1L;

        clienteService.eliminar(id);

        verify(clienteRepo, times(1)).deleteById(id);
    }
}