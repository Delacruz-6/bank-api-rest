package com.bank.prestamos.domain.excepcion;

/**
 * Excepción lanzada cuando se intenta realizar una transición de estado no permitida.
 */
public class TransicionEstadoInvalidaException extends RuntimeException {

    public TransicionEstadoInvalidaException(String estadoActual, String estadoDeseado) {
        super(String.format("Transición de estado inválida de %s a %s", estadoActual, estadoDeseado));
    }
}
