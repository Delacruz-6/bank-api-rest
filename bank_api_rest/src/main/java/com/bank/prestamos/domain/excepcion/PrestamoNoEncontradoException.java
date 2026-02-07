package com.bank.prestamos.domain.excepcion;

/**
 * Excepción lanzada cuando se intenta acceder a un préstamo que no existe.
 */
public class PrestamoNoEncontradoException extends RuntimeException {

    public PrestamoNoEncontradoException(Long id) {
        super("Préstamo no encontrado con id: " + id);
    }
}
