package com.bank.prestamos.infrastructure.excepcion;

import com.bank.prestamos.domain.excepcion.DocumentoDuplicadoException;
import com.bank.prestamos.domain.excepcion.PrestamoNoEncontradoException;
import com.bank.prestamos.domain.excepcion.TransicionEstadoInvalidaException;
import com.bank.prestamos.infrastructure.adapter.in.rest.dto.RespuestaError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la aplicación.
 * Intercepta las excepciones y las convierte en respuestas HTTP estandarizadas.
 */
@RestControllerAdvice
@Slf4j
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(PrestamoNoEncontradoException.class)
    public ResponseEntity<RespuestaError> manejarPrestamoNoEncontrado(
            PrestamoNoEncontradoException ex,
            WebRequest request) {

        log.warn("Préstamo no encontrado: {}", ex.getMessage());

        RespuestaError error = new RespuestaError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "No Encontrado",
                ex.getMessage(),
                obtenerRuta(request),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TransicionEstadoInvalidaException.class)
    public ResponseEntity<RespuestaError> manejarTransicionEstadoInvalida(
            TransicionEstadoInvalidaException ex,
            WebRequest request) {

        log.warn("Transición de estado inválida: {}", ex.getMessage());

        RespuestaError error = new RespuestaError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflicto",
                ex.getMessage(),
                obtenerRuta(request),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(DocumentoDuplicadoException.class)
    public ResponseEntity<RespuestaError> manejarDocumentoDuplicado(
            DocumentoDuplicadoException ex,
            WebRequest request) {

        log.warn("Documento duplicado: {}", ex.getMessage());

        RespuestaError error = new RespuestaError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflicto",
                ex.getMessage(),
                obtenerRuta(request),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaError> manejarErroresValidacion(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        Map<String, String> erroresValidacion = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                erroresValidacion.put(error.getField(), error.getDefaultMessage())
        );

        log.warn("Errores de validación: {}", erroresValidacion);

        RespuestaError error = new RespuestaError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Solicitud Incorrecta",
                "Falló la validación",
                obtenerRuta(request),
                erroresValidacion
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaError> manejarExcepcionGlobal(
            Exception ex,
            WebRequest request) {

        log.error("Error inesperado: ", ex);

        RespuestaError error = new RespuestaError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error Interno del Servidor",
                "Ocurrió un error inesperado",
                obtenerRuta(request),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private String obtenerRuta(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}
