package com.bank.prestamos.domain.excepcion;

/**
 * Excepción lanzada cuando se intenta crear un préstamo con un documento identificativo 
 * que ya existe en el sistema.
 */
public class DocumentoDuplicadoException extends RuntimeException {

    public DocumentoDuplicadoException(String documento) {
        super("Ya existe un préstamo con el documento " + documento);
    }
}
