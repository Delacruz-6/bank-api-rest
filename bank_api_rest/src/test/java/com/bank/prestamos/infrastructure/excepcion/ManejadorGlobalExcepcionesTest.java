package com.bank.prestamos.infrastructure.excepcion;

import com.bank.prestamos.domain.excepcion.DocumentoDuplicadoException;
import com.bank.prestamos.domain.excepcion.PrestamoNoEncontradoException;
import com.bank.prestamos.domain.excepcion.TransicionEstadoInvalidaException;
import com.bank.prestamos.infrastructure.adapter.in.rest.dto.RespuestaError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests unitarios para ManejadorGlobalExcepciones.
 */
@DisplayName("Tests del manejador global de excepciones")
class ManejadorGlobalExcepcionesTest {

    private ManejadorGlobalExcepciones manejador;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        manejador = new ManejadorGlobalExcepciones();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/prestamos/1");
        webRequest = new ServletWebRequest(request);
    }

    @Test
    @DisplayName("Debe manejar PrestamoNoEncontradoException con status 404")
    void debeManejarPrestamoNoEncontrado() {
        PrestamoNoEncontradoException excepcion = new PrestamoNoEncontradoException(999L);

        ResponseEntity<RespuestaError> response = manejador.manejarPrestamoNoEncontrado(excepcion, webRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().estado()).isEqualTo(404);
        assertThat(response.getBody().error()).isEqualTo("No Encontrado");
        assertThat(response.getBody().mensaje()).contains("Préstamo no encontrado con id: 999");
    }

    @Test
    @DisplayName("Debe manejar TransicionEstadoInvalidaException con status 409")
    void debeManejarTransicionEstadoInvalida() {
        TransicionEstadoInvalidaException excepcion = 
            new TransicionEstadoInvalidaException("RECHAZADA", "APROBADA");

        ResponseEntity<RespuestaError> response = manejador.manejarTransicionEstadoInvalida(excepcion, webRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().estado()).isEqualTo(409);
        assertThat(response.getBody().error()).isEqualTo("Conflicto");
        assertThat(response.getBody().mensaje()).contains("Transición de estado inválida");
    }

    @Test
    @DisplayName("Debe manejar DocumentoDuplicadoException con status 409")
    void debeManejarDocumentoDuplicado() {
        DocumentoDuplicadoException excepcion = new DocumentoDuplicadoException("12345678A");

        ResponseEntity<RespuestaError> response = manejador.manejarDocumentoDuplicado(excepcion, webRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().estado()).isEqualTo(409);
        assertThat(response.getBody().error()).isEqualTo("Conflicto");
        assertThat(response.getBody().mensaje()).contains("Ya existe un préstamo con el documento");
    }

    @Test
    @DisplayName("Debe manejar MethodArgumentNotValidException con status 400")
    void debeManejarErroresValidacion() {
        MethodArgumentNotValidException excepcion = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        
        FieldError fieldError1 = new FieldError("prestamo", "nombreSolicitante", "El nombre es obligatorio");
        FieldError fieldError2 = new FieldError("prestamo", "importeSolicitado", "El importe debe ser positivo");
        
        when(excepcion.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        ResponseEntity<RespuestaError> response = manejador.manejarErroresValidacion(excepcion, webRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().estado()).isEqualTo(400);
        assertThat(response.getBody().error()).isEqualTo("Solicitud Incorrecta");
        assertThat(response.getBody().erroresValidacion()).hasSize(2);
        assertThat(response.getBody().erroresValidacion()).containsKeys("nombreSolicitante", "importeSolicitado");
    }

    @Test
    @DisplayName("Debe manejar excepciones generales con status 500")
    void debeManejarExcepcionGeneral() {
        Exception excepcion = new RuntimeException("Error inesperado");

        ResponseEntity<RespuestaError> response = manejador.manejarExcepcionGlobal(excepcion, webRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().estado()).isEqualTo(500);
        assertThat(response.getBody().error()).isEqualTo("Error Interno del Servidor");
        assertThat(response.getBody().mensaje()).isEqualTo("Ocurrió un error inesperado");
    }
}
