package com.bank.prestamos.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests unitarios para el enum EstadoPrestamo.
 */
@DisplayName("Tests del enum EstadoPrestamo")
class EstadoPrestamoTest {

    @Test
    @DisplayName("Debe permitir transición de PENDIENTE a APROBADA")
    void debePermitirTransicionPendienteAAprobada() {
        boolean resultado = EstadoPrestamo.PENDIENTE.puedeTransicionarA(EstadoPrestamo.APROBADA);
        assertThat(resultado).isTrue();
    }

    @Test
    @DisplayName("Debe permitir transición de PENDIENTE a RECHAZADA")
    void debePermitirTransicionPendienteARechazada() {
        boolean resultado = EstadoPrestamo.PENDIENTE.puedeTransicionarA(EstadoPrestamo.RECHAZADA);
        assertThat(resultado).isTrue();
    }

    @Test
    @DisplayName("Debe permitir transición de APROBADA a CANCELADA")
    void debePermitirTransicionAprobadaACancelada() {
        boolean resultado = EstadoPrestamo.APROBADA.puedeTransicionarA(EstadoPrestamo.CANCELADA);
        assertThat(resultado).isTrue();
    }

    @Test
    @DisplayName("No debe permitir transición de PENDIENTE a CANCELADA")
    void noDebePermitirTransicionPendienteACancelada() {
        boolean resultado = EstadoPrestamo.PENDIENTE.puedeTransicionarA(EstadoPrestamo.CANCELADA);
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("No debe permitir transición de RECHAZADA a cualquier estado")
    void noDebePermitirTransicionDesdeRechazada() {
        assertThat(EstadoPrestamo.RECHAZADA.puedeTransicionarA(EstadoPrestamo.APROBADA)).isFalse();
        assertThat(EstadoPrestamo.RECHAZADA.puedeTransicionarA(EstadoPrestamo.CANCELADA)).isFalse();
        assertThat(EstadoPrestamo.RECHAZADA.puedeTransicionarA(EstadoPrestamo.PENDIENTE)).isFalse();
    }

    @Test
    @DisplayName("No debe permitir transición de CANCELADA a cualquier estado")
    void noDebePermitirTransicionDesdeCancelada() {
        assertThat(EstadoPrestamo.CANCELADA.puedeTransicionarA(EstadoPrestamo.APROBADA)).isFalse();
        assertThat(EstadoPrestamo.CANCELADA.puedeTransicionarA(EstadoPrestamo.RECHAZADA)).isFalse();
        assertThat(EstadoPrestamo.CANCELADA.puedeTransicionarA(EstadoPrestamo.PENDIENTE)).isFalse();
    }
}
