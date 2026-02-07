package com.bank.prestamos.infrastructure.adapter.in.rest.dto;

import com.bank.prestamos.domain.models.EstadoPrestamo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para la solicitud de actualización del estado de un préstamo.
 * Usa record para mayor inmutabilidad y concisión.
 */
@Schema(description = "Solicitud para cambiar el estado de un préstamo")
public record ActualizarEstadoPrestamoRequest(

    @NotNull(message = "El estado es obligatorio")
    @Schema(description = "Nuevo estado del préstamo", example = "APROBADA")
    EstadoPrestamo estado,

    @NotBlank(message = "El usuario que realiza la modificación es obligatorio")
    @Schema(description = "Usuario que realiza el cambio de estado", example = "gestor@banco.com")
    String usuarioModificacion
) {}
