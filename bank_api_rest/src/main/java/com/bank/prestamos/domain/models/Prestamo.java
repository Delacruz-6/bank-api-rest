package com.bank.prestamos.domain.models;

import com.bank.prestamos.domain.excepcion.TransicionEstadoInvalidaException;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa un préstamo personal.
 * Contiene la lógica de negocio relacionada con las transiciones de estado.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Prestamo {

    private Long id;
    private String nombreSolicitante;
    private BigDecimal importeSolicitado;
    private String divisa;
    private String documentoIdentificativo;
    private EstadoPrestamo estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimaModificacion;
    private String usuarioUltimaModificacion;

    /**
     * Cambia el estado del préstamo validando que la transición sea permitida.
     *
     * @param nuevoEstado El nuevo estado deseado
     * @param usuario El usuario que realiza el cambio
     * @throws TransicionEstadoInvalidaException si la transición no es válida
     */
    public void cambiarEstado(EstadoPrestamo nuevoEstado, String usuario) {
        if (!this.estado.puedeTransicionarA(nuevoEstado)) {
            throw new TransicionEstadoInvalidaException(this.estado.name(), nuevoEstado.name());
        }
        this.estado = nuevoEstado;
        this.fechaUltimaModificacion = LocalDateTime.now();
        this.usuarioUltimaModificacion = usuario;
    }

    /**
     * Verifica si el préstamo está en estado pendiente.
     *
     * @return true si está pendiente, false en caso contrario
     */
    public boolean estaPendiente() {
        return this.estado == EstadoPrestamo.PENDIENTE;
    }

    /**
     * Verifica si el préstamo está aprobado.
     *
     * @return true si está aprobado, false en caso contrario
     */
    public boolean estaAprobada() {
        return this.estado == EstadoPrestamo.APROBADA;
    }

    /**
     * Verifica si el préstamo está rechazado.
     *
     * @return true si está rechazado, false en caso contrario
     */
    public boolean estaRechazada() {
        return this.estado == EstadoPrestamo.RECHAZADA;
    }

    /**
     * Verifica si el préstamo está cancelado.
     *
     * @return true si está cancelado, false en caso contrario
     */
    public boolean estaCancelada() {
        return this.estado == EstadoPrestamo.CANCELADA;
    }
}
