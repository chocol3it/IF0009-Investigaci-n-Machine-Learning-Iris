package cr.ac.ucr.paraiso.ie.c4h877.iris.exception;

import cr.ac.ucr.paraiso.ie.c4h877.iris.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errores.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ErrorResponseDTO error = new ErrorResponseDTO("Error de Validación",
                HttpStatus.BAD_REQUEST.value(),
                "Los campos de la solicitud no cumplen las restricciones.",
                request.getRequestURI(), errores);
        return ResponseEntity.badRequest().body(error);
    }

    // Por ejemplo: entrenar con k = 0 o con k mayor que la cantidad de flores.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(
            IllegalArgumentException exception, HttpServletRequest request) {
        return response("Solicitud Inválida", HttpStatus.BAD_REQUEST,
                exception.getMessage(), request);
    }

    // Por ejemplo: pedir una predicción antes de que el modelo esté entrenado.
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalState(
            IllegalStateException exception, HttpServletRequest request) {
        return response("Modelo No Disponible", HttpStatus.SERVICE_UNAVAILABLE,
                exception.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception exception, HttpServletRequest request) {
        return response("Error Interno del Servidor", HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un fallo inesperado. Contacte al administrador.", request);
    }

    private ResponseEntity<ErrorResponseDTO> response(String title, HttpStatus status,
            String detail, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(title, status.value(), detail,
                request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
