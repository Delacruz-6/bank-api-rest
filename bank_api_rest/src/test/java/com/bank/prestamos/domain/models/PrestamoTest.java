package com.bank.prestamos.domain.models;

import com.bank.prestamos.domain.excepcion.TransicionEstadoInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitarios para la entidad Prestamo.
 */
@DisplayName("Tests de la entidad Prestamo")
class PrestamoTest {

    private Prestamo prestamo;

    @BeforeEach
    void setUp() {
        prestamo = Prestamo.builder()
                .id(1L)
                .nombreSolicitante("Juan Pérez")
                .importeSolicitado(new BigDecimal("15000.00"))
                .divisa("EUR")
                .documentoIdentificativo("12345678A")
                .estado(EstadoPrestamo.PENDIENTE)
                .fechaCreacion(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Debe cambiar estado de PENDIENTE a APROBADA exitosamente")
    void debeCambiarEstadoPendienteAAprobada() {
        prestamo.cambiarEstado(EstadoPrestamo.APROBADA, "gestor@banco.com");

        assertThat(prestamo.getEstado()).isEqualTo(EstadoPrestamo.APROBADA);
        assertThat(prestamo.getUsuarioUltimaModificacion()).isEqualTo("gestor@banco.com");
        assertThat(prestamo.getFechaUltimaModificacion()).isNotNull();
    }

    @Test
    @DisplayName("Debe cambiar estado de PENDIENTE a RECHAZADA exitosamente")
    void debeCambiarEstadoPendienteARechazada() {
        prestamo.cambiarEstado(EstadoPrestamo.RECHAZADA, "gestor@banco.com");

        assertThat(prestamo.getEstado()).isEqualTo(EstadoPrestamo.RECHAZADA);
        assertThat(prestamo.getUsuarioUltimaModificacion()).isEqualTo("gestor@banco.com");
        assertThat(prestamo.getFechaUltimaModificacion()).isNotNull();
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar transición inválida")
    void debeLanzarExcepcionTransicionInvalida() {
        assertThatThrownBy(() -> prestamo.cambiarEstado(EstadoPrestamo.CANCELADA, "gestor@banco.com"))
                .isInstanceOf(TransicionEstadoInvalidaException.class)
                .hasMessageContaining("Transición de estado inválida de PENDIENTE a CANCELADA");
    }

    @Test
    @DisplayName("Debe cambiar estado de APROBADA a CANCELADA")
    void debeCambiarEstadoAprobadaACancelada() {
        prestamo.setEstado(EstadoPrestamo.APROBADA);
        
        prestamo.cambiarEstado(EstadoPrestamo.CANCELADA, "gestor@banco.com");

        assertThat(prestamo.getEstado()).isEqualTo(EstadoPrestamo.CANCELADA);
        assertThat(prestamo.getUsuarioUltimaModificacion()).isEqualTo("gestor@banco.com");
    }

    @Test
    @DisplayName("Debe retornar true cuando el préstamo está pendiente")
    void debeRetornarTrueCuandoEstaPendiente() {
        assertThat(prestamo.estaPendiente()).isTrue();
        assertThat(prestamo.estaAprobada()).isFalse();
        assertThat(prestamo.estaRechazada()).isFalse();
        assertThat(prestamo.estaCancelada()).isFalse();
    }

    @Test
    @DisplayName("Debe retornar true cuando el préstamo está aprobado")
    void debeRetornarTrueCuandoEstaAprobada() {
        prestamo.setEstado(EstadoPrestamo.APROBADA);

        assertThat(prestamo.estaAprobada()).isTrue();
        assertThat(prestamo.estaPendiente()).isFalse();
    }
}
