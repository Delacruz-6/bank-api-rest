package com.bank.prestamos.domain.ports.in;

import com.bank.prestamos.domain.models.EstadoPrestamo;
import com.bank.prestamos.domain.models.Prestamo;

/**
 * Puerto de entrada para modificar el estado de un préstamo.
 * Define el caso de uso de cambio de estado.
 */
public interface ModificarEstadoPrestamoUseCase {

    /**
     * Cambia el estado de un préstamo.
     *
     * @param id El ID del préstamo
     * @param nuevoEstado El nuevo estado deseado
     * @param usuario El usuario que realiza el cambio
     * @return El préstamo con el estado actualizado
     * @throws com.bank.prestamos.domain.excepcion.PrestamoNoEncontradoException si no existe
     * @throws com.bank.prestamos.domain.excepcion.TransicionEstadoInvalidaException si la transición no es válida
     */
    Prestamo cambiarEstado(Long id, EstadoPrestamo nuevoEstado, String usuario);
}
