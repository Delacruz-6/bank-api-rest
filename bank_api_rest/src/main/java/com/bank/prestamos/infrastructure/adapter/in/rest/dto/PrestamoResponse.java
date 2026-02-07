package com.bank.prestamos.infrastructure.adapter.in.rest.dto;

import com.bank.prestamos.domain.models.EstadoPrestamo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para la respuesta de un préstamo.
 * Usa record para mayor inmutabilidad y concisión.
 */
@Schema(description = "Respuesta con los datos de un préstamo")
public record PrestamoResponse(

    @Schema(description = "Identificador único del préstamo", example = "1")
    Long id,

    @Schema(description = "Nombre completo del solicitante", example = "Juan Pérez García")
    String nombreSolicitante,

    @Schema(description = "Importe solicitado del préstamo", example = "15000.00")
    BigDecimal importeSolicitado,

    @Schema(description = "Código de divisa ISO 4217", example = "EUR")
    String divisa,

    @Schema(description = "Documento identificativo (DNI o NIE)", example = "12345678A")
    String documentoIdentificativo,

    @Schema(description = "Estado actual del préstamo", example = "PENDIENTE")
    EstadoPrestamo estado,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Fecha y hora de creación", example = "2026-02-07T10:30:00")
    LocalDateTime fechaCreacion,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Fecha y hora de última modificación", example = "2026-02-07T11:15:00")
    LocalDateTime fechaUltimaModificacion,

    @Schema(description = "Usuario que realizó la última modificación", example = "gestor@banco.com")
    String usuarioUltimaModificacion
) {}
