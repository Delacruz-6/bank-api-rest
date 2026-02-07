package com.bank.prestamos.infrastructure.adapter.in.rest.mappers;

import com.bank.prestamos.domain.models.Prestamo;
import com.bank.prestamos.infrastructure.adapter.in.rest.dto.CrearPrestamoRequest;
import com.bank.prestamos.infrastructure.adapter.in.rest.dto.PrestamoResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapeador entre DTOs REST y entidades de dominio.
 * MapStruct genera la implementación automáticamente.
 * No requiere 'uses' ya que no delega en otros mappers.
 */
@Mapper(componentModel = "spring")
public interface PrestamoDtoMapper {

    /**
     * Convierte un request de creación a entidad de dominio.
     *
     * @param request El DTO de request
     * @return La entidad de dominio
     */
    Prestamo toDomain(CrearPrestamoRequest request);

    /**
     * Convierte una entidad de dominio a DTO de respuesta.
     *
     * @param prestamo La entidad de dominio
     * @return El DTO de respuesta
     */
    PrestamoResponse toResponse(Prestamo prestamo);

    /**
     * Convierte una lista de entidades de dominio a lista de DTOs de respuesta.
     *
     * @param prestamos Lista de entidades de dominio
     * @return Lista de DTOs de respuesta
     */
    List<PrestamoResponse> toResponseList(List<Prestamo> prestamos);
}
