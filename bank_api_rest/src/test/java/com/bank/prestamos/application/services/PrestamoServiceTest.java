package com.bank.prestamos.application.services;

import com.bank.prestamos.domain.excepcion.DocumentoDuplicadoException;
import com.bank.prestamos.domain.excepcion.PrestamoNoEncontradoException;
import com.bank.prestamos.domain.excepcion.TransicionEstadoInvalidaException;
import com.bank.prestamos.domain.models.EstadoPrestamo;
import com.bank.prestamos.domain.models.Prestamo;
import com.bank.prestamos.domain.ports.out.PrestamoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para PrestamoServicio.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del servicio PrestamoServicio")
class PrestamoServiceTest {

    @Mock
    private PrestamoRepositoryPort repositoryPort;

    @InjectMocks
    private PrestamoService prestamoService;

    private Prestamo prestamoMock;

    @BeforeEach
    void setUp() {
        prestamoMock = Prestamo.builder()
                .id(1L)
                .nombreSolicitante("Juan Pérez")
                .importeSolicitado(new BigDecimal("15000.00"))
                .divisa("EUR")
                .documentoIdentificativo("12345678A")
                .estado(EstadoPrestamo.PENDIENTE)
                .build();
    }

    @Test
    @DisplayName("Debe crear préstamo exitosamente")
    void debeCrearPrestamoExitosamente() {
        when(repositoryPort.existePorDocumento(anyString())).thenReturn(false);
        when(repositoryPort.guardar(any(Prestamo.class))).thenReturn(prestamoMock);

        Prestamo resultado = prestamoService.crear(prestamoMock);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getEstado()).isEqualTo(EstadoPrestamo.PENDIENTE);
        verify(repositoryPort).existePorDocumento("12345678A");
        verify(repositoryPort).guardar(any(Prestamo.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el documento está duplicado")
    void debeLanzarExcepcionDocumentoDuplicado() {
        when(repositoryPort.existePorDocumento(anyString())).thenReturn(true);

        assertThatThrownBy(() -> prestamoService.crear(prestamoMock))
                .isInstanceOf(DocumentoDuplicadoException.class)
                .hasMessageContaining("Ya existe un préstamo con el documento 12345678A");

        verify(repositoryPort).existePorDocumento("12345678A");
        verify(repositoryPort, never()).guardar(any());
    }

    @Test
    @DisplayName("Debe obtener préstamo por ID exitosamente")
    void debeObtenerPrestamoPorId() {
        when(repositoryPort.buscarPorId(1L)).thenReturn(Optional.of(prestamoMock));

        Prestamo resultado = prestamoService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(repositoryPort).buscarPorId(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando préstamo no existe")
    void debeLanzarExcepcionPrestamoNoEncontrado() {
        when(repositoryPort.buscarPorId(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> prestamoService.obtenerPorId(999L))
                .isInstanceOf(PrestamoNoEncontradoException.class)
                .hasMessageContaining("Préstamo no encontrado con id: 999");

        verify(repositoryPort).buscarPorId(999L);
    }

    @Test
    @DisplayName("Debe obtener todos los préstamos")
    void debeObtenerTodosPrestamos() {
        Prestamo prestamo2 = Prestamo.builder()
                .id(2L)
                .nombreSolicitante("María López")
                .build();

        when(repositoryPort.buscarTodos()).thenReturn(Arrays.asList(prestamoMock, prestamo2));

        List<Prestamo> resultado = prestamoService.obtenerTodos();

        assertThat(resultado).hasSize(2);
        verify(repositoryPort).buscarTodos();
    }

    @Test
    @DisplayName("Debe cambiar estado de préstamo exitosamente")
    void debeCambiarEstadoExitosamente() {
        when(repositoryPort.buscarPorId(1L)).thenReturn(Optional.of(prestamoMock));
        when(repositoryPort.guardar(any(Prestamo.class))).thenReturn(prestamoMock);

        Prestamo resultado = prestamoService.cambiarEstado(1L, EstadoPrestamo.APROBADA, "gestor@banco.com");

        assertThat(resultado).isNotNull();
        assertThat(resultado.getEstado()).isEqualTo(EstadoPrestamo.APROBADA);
        verify(repositoryPort).buscarPorId(1L);
        verify(repositoryPort).guardar(any(Prestamo.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando transición de estado es inválida")
    void debeLanzarExcepcionTransicionInvalida() {
        when(repositoryPort.buscarPorId(1L)).thenReturn(Optional.of(prestamoMock));

        assertThatThrownBy(() -> prestamoService.cambiarEstado(1L, EstadoPrestamo.CANCELADA, "gestor@banco.com"))
                .isInstanceOf(TransicionEstadoInvalidaException.class)
                .hasMessageContaining("Transición de estado inválida");

        verify(repositoryPort).buscarPorId(1L);
        verify(repositoryPort, never()).guardar(any());
    }
}
