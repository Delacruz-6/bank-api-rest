package com.bank.prestamos.application.services;

import com.bank.prestamos.domain.excepcion.DocumentoDuplicadoException;
import com.bank.prestamos.domain.excepcion.PrestamoNoEncontradoException;
import com.bank.prestamos.domain.models.EstadoPrestamo;
import com.bank.prestamos.domain.models.Prestamo;
import com.bank.prestamos.domain.ports.in.ConsultarPrestamoUseCase;
import com.bank.prestamos.domain.ports.in.CrearPrestamoUseCase;
import com.bank.prestamos.domain.ports.in.ModificarEstadoPrestamoUseCase;
import com.bank.prestamos.domain.ports.out.PrestamoRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio que implementa los casos de uso relacionados con préstamos.
 * Actúa como orquestador entre los puertos de entrada y salida.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrestamoService implements CrearPrestamoUseCase, ConsultarPrestamoUseCase, ModificarEstadoPrestamoUseCase {

    private final PrestamoRepositoryPort repositoryPort;

    @Override
    public Prestamo crear(Prestamo prestamo) {
        log.info("Creando nuevo préstamo para: {}", prestamo.getNombreSolicitante());

        // Validar que no exista un préstamo con el mismo documento
        if (repositoryPort.existePorDocumento(prestamo.getDocumentoIdentificativo())) {
            log.warn("Intento de crear préstamo con documento duplicado: {}", 
                    prestamo.getDocumentoIdentificativo());
            throw new DocumentoDuplicadoException(prestamo.getDocumentoIdentificativo());
        }

        // Inicializar campos por defecto
        prestamo.setEstado(EstadoPrestamo.PENDIENTE);
        prestamo.setFechaCreacion(LocalDateTime.now());

        Prestamo prestamoGuardado = repositoryPort.guardar(prestamo);
        log.info("Préstamo creado exitosamente con ID: {}", prestamoGuardado.getId());

        return prestamoGuardado;
    }

    @Override
    @Transactional(readOnly = true)
    public Prestamo obtenerPorId(Long id) {
        log.debug("Consultando préstamo con ID: {}", id);
        return repositoryPort.buscarPorId(id)
                .orElseThrow(() -> {
                    log.warn("Préstamo no encontrado con ID: {}", id);
                    return new PrestamoNoEncontradoException(id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prestamo> obtenerTodos() {
        log.debug("Consultando todos los préstamos");
        List<Prestamo> prestamos = repositoryPort.buscarTodos();
        log.info("Se encontraron {} préstamos", prestamos.size());
        return prestamos;
    }

    @Override
    public Prestamo cambiarEstado(Long id, EstadoPrestamo nuevoEstado, String usuario) {
        log.info("Cambiando estado del préstamo ID {} a {}", id, nuevoEstado);

        Prestamo prestamo = obtenerPorId(id);
        EstadoPrestamo estadoAnterior = prestamo.getEstado();

        // El método cambiarEstado de la entidad valida la transición
        prestamo.cambiarEstado(nuevoEstado, usuario);

        Prestamo prestamoActualizado = repositoryPort.guardar(prestamo);
        log.info("Estado del préstamo {} cambiado de {} a {} por usuario {}", 
                id, estadoAnterior, nuevoEstado, usuario);

        return prestamoActualizado;
    }
}
