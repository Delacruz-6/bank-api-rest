package com.bank.prestamos.infrastructure.adapter.in.rest.controller;

import com.bank.prestamos.domain.models.Prestamo;
import com.bank.prestamos.domain.ports.in.ConsultarPrestamoUseCase;
import com.bank.prestamos.domain.ports.in.CrearPrestamoUseCase;
import com.bank.prestamos.domain.ports.in.ModificarEstadoPrestamoUseCase;
import com.bank.prestamos.infrastructure.adapter.in.rest.dto.ActualizarEstadoPrestamoRequest;
import com.bank.prestamos.infrastructure.adapter.in.rest.dto.CrearPrestamoRequest;
import com.bank.prestamos.infrastructure.adapter.in.rest.dto.PrestamoResponse;
import com.bank.prestamos.infrastructure.adapter.in.rest.dto.RespuestaError;
import com.bank.prestamos.infrastructure.adapter.in.rest.mappers.PrestamoDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de préstamos.
 * Expone los endpoints de la API v1.
 */
@RestController
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Préstamos", description = "API para gestión de solicitudes de préstamos personales")
public class PrestamoController {

    private final CrearPrestamoUseCase crearPrestamoUseCase;
    private final ConsultarPrestamoUseCase consultarPrestamoUseCase;
    private final ModificarEstadoPrestamoUseCase modificarEstadoPrestamoUseCase;
    private final PrestamoDtoMapper mapeador;

    @PostMapping
    @Operation(summary = "Crear nueva solicitud de préstamo", 
               description = "Crea una nueva solicitud de préstamo con estado inicial PENDIENTE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Préstamo creado exitosamente",
                     content = @Content(schema = @Schema(implementation = PrestamoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                     content = @Content(schema = @Schema(implementation = RespuestaError.class))),
        @ApiResponse(responseCode = "409", description = "Ya existe un préstamo con el documento proporcionado",
                     content = @Content(schema = @Schema(implementation = RespuestaError.class)))
    })
    public ResponseEntity<PrestamoResponse> crearPrestamo(@Valid @RequestBody CrearPrestamoRequest request) {
        log.info("Recibida solicitud para crear préstamo: {}", request);
        
        Prestamo prestamo = mapeador.toDomain(request);
        Prestamo prestamoCreado = crearPrestamoUseCase.crear(prestamo);
        PrestamoResponse response = mapeador.toResponse(prestamoCreado);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos los préstamos", 
               description = "Obtiene una lista de todas las solicitudes de préstamo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de préstamos obtenida exitosamente",
                     content = @Content(schema = @Schema(implementation = PrestamoResponse.class)))
    })
    public ResponseEntity<List<PrestamoResponse>> listarPrestamos() {
        log.info("Recibida solicitud para listar todos los préstamos");
        
        List<Prestamo> prestamos = consultarPrestamoUseCase.obtenerTodos();
        List<PrestamoResponse> response = mapeador.toResponseList(prestamos);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener préstamo por ID", 
               description = "Obtiene los detalles de un préstamo específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Préstamo encontrado",
                     content = @Content(schema = @Schema(implementation = PrestamoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Préstamo no encontrado",
                     content = @Content(schema = @Schema(implementation = RespuestaError.class)))
    })
    public ResponseEntity<PrestamoResponse> obtenerPrestamoPorId(@PathVariable Long id) {
        log.info("Recibida solicitud para obtener préstamo con ID: {}", id);
        
        Prestamo prestamo = consultarPrestamoUseCase.obtenerPorId(id);
        PrestamoResponse response = mapeador.toResponse(prestamo);
        
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de préstamo", 
               description = "Cambia el estado de un préstamo validando las transiciones permitidas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente",
                     content = @Content(schema = @Schema(implementation = PrestamoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                     content = @Content(schema = @Schema(implementation = RespuestaError.class))),
        @ApiResponse(responseCode = "404", description = "Préstamo no encontrado",
                     content = @Content(schema = @Schema(implementation = RespuestaError.class))),
        @ApiResponse(responseCode = "409", description = "Transición de estado no permitida",
                     content = @Content(schema = @Schema(implementation = RespuestaError.class)))
    })
    public ResponseEntity<PrestamoResponse> cambiarEstadoPrestamo(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEstadoPrestamoRequest request) {
        
        log.info("Recibida solicitud para cambiar estado del préstamo {} a {}", id, request.estado());
        
        Prestamo prestamo = modificarEstadoPrestamoUseCase.cambiarEstado(
                id, 
                request.estado(), 
                request.usuarioModificacion()
        );
        PrestamoResponse response = mapeador.toResponse(prestamo);
        
        return ResponseEntity.ok(response);
    }
}
