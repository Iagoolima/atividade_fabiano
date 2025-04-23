package com.autenticacao.app.config.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ErrorException {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public GeneralErrorException handleRuntimeExceptions(RuntimeException ex) {
        return new GeneralErrorException(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(new ErrorFieldResponse(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleRuntimeExceptions(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage())
        );
    }

}
