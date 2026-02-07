package com.bank.prestamos.domain.ports.in;

import com.bank.prestamos.domain.models.Prestamo;

/**
 * Puerto de entrada para crear un nuevo préstamo.
 * Define el caso de uso de creación de préstamos.
 */
public interface CrearPrestamoUseCase {

    /**
     * Crea un nuevo préstamo en el sistema.
     *
     * @param prestamo Los datos del préstamo a crear
     * @return El préstamo creado con su ID asignado
     */
    Prestamo crear(Prestamo prestamo);
}
