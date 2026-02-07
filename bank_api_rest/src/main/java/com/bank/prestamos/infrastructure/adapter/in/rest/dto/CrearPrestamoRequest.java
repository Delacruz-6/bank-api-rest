package com.bank.prestamos.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * DTO para la solicitud de creación de un préstamo.
 * Usa record para mayor inmutabilidad y concisión.
 */
@Schema(description = "Solicitud para crear un nuevo préstamo")
public record CrearPrestamoRequest(

    @NotBlank(message = "El nombre del solicitante es obligatorio")
    @Schema(description = "Nombre completo del solicitante", example = "Juan Pérez García")
    String nombreSolicitante,

    @Positive(message = "El importe solicitado debe ser positivo")
    @Schema(description = "Importe solicitado del préstamo", example = "15000.00")
    BigDecimal importeSolicitado,

    @NotBlank(message = "La divisa es obligatoria")
    @Pattern(regexp = "^[A-Z]{3}$", message = "La divisa debe ser un código ISO de 3 letras")
    @Schema(description = "Código de divisa ISO 4217", example = "EUR")
    String divisa,

    @NotBlank(message = "El documento identificativo es obligatorio")
    @Pattern(
        regexp = "^[0-9]{8}[A-Z]$|^[XYZ][0-9]{7}[A-Z]$",
        message = "Formato de documento inválido. Debe ser DNI (8 dígitos + letra) o NIE (letra + 7 dígitos + letra)"
    )
    @Schema(description = "Documento identificativo (DNI o NIE)", example = "12345678A")
    String documentoIdentificativo
) {}
