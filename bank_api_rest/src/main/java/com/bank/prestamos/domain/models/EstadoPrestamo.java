package com.bank.prestamos.domain.models;

/**
 * Enumeración que representa los posibles estados de un préstamo.
 * Define las transiciones válidas entre estados.
 */
public enum EstadoPrestamo {
    PENDIENTE,
    APROBADA,
    RECHAZADA,
    CANCELADA;

    /**
     * Valida si la transición de estado es permitida.
     * 
     * Transiciones válidas:
     * - PENDIENTE → APROBADA
     * - PENDIENTE → RECHAZADA
     * - APROBADA → CANCELADA
     * 
     * @param nuevoEstado El estado al que se desea transicionar
     * @return true si la transición es válida, false en caso contrario
     */
    public boolean puedeTransicionarA(EstadoPrestamo nuevoEstado) {
        return switch (this) {
            case PENDIENTE -> nuevoEstado == APROBADA || nuevoEstado == RECHAZADA;
            case APROBADA -> nuevoEstado == CANCELADA;
            case RECHAZADA, CANCELADA -> false;
        };
    }
}
