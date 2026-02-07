package com.bank.prestamos.application.mappers;

import com.bank.prestamos.domain.models.Prestamo;
import com.bank.prestamos.infrastructure.adapter.out.persistence.entities.PrestamoEntity;
import org.mapstruct.Mapper;

/**
 * Mapeador entre la entidad de dominio Prestamo y la entidad JPA PrestamoEntidad.
 * MapStruct genera la implementación automáticamente.
 * No requiere 'uses' ya que mapea campos simples sin composición.
 */
@Mapper(componentModel = "spring")
public interface PrestamoMapper {

    /**
     * Convierte una entidad JPA a entidad de dominio.
     *
     * @param entidad La entidad JPA
     * @return La entidad de dominio
     */
    Prestamo toDomain(PrestamoEntity entidad);

    /**
     * Convierte una entidad de dominio a entidad JPA.
     *
     * @param dominio La entidad de dominio
     * @return La entidad JPA
     */
    PrestamoEntity toEntity(Prestamo dominio);
}
