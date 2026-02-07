package com.bank.prestamos.domain.ports.in;

import com.bank.prestamos.domain.models.Prestamo;

import java.util.List;

/**
 * Puerto de entrada para consultar préstamos.
 * Define los casos de uso de consulta.
 */
public interface ConsultarPrestamoUseCase {

    /**
     * Obtiene un préstamo por su ID.
     *
     * @param id El ID del préstamo
     * @return El préstamo encontrado
     * @throws com.bank.prestamos.domain.excepcion.PrestamoNoEncontradoException si no existe
     */
    Prestamo obtenerPorId(Long id);

    /**
     * Obtiene todos los préstamos del sistema.
     *
     * @return Lista de todos los préstamos
     */
    List<Prestamo> obtenerTodos();
}
