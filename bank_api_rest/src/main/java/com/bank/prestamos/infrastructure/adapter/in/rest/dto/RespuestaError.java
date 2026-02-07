package com.bank.prestamos.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO para respuestas de error estandarizadas.
 * Usa record para mayor inmutabilidad y concisión.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Respuesta de error estandarizada")
public record RespuestaError(

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Marca de tiempo del error", example = "2026-02-07T10:30:00")
    LocalDateTime marcaTiempo,

    @Schema(description = "Código de estado HTTP", example = "404")
    int estado,

    @Schema(description = "Nombre del error HTTP", example = "No Encontrado")
    String error,

    @Schema(description = "Mensaje descriptivo del error", example = "Préstamo no encontrado con id: 999")
    String mensaje,

    @Schema(description = "Ruta de la petición que generó el error", example = "/api/prestamos/999")
    String ruta,

    @Schema(description = "Errores de validación de campos (opcional)")
    Map<String, String> erroresValidacion
) {}
