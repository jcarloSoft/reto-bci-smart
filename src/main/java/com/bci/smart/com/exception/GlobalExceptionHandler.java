package com.bci.smart.com.exception;

import com.bci.smart.com.dto.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(WebExchangeBindException ex) {
    String errorMessage = ex.getFieldErrors().isEmpty()
        ? "Solicitud inválida"
        : ex.getFieldErrors().get(0).getDefaultMessage();

    Map<String, String> response = new HashMap<>();
    response.put("message", errorMessage);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public Mono<ResponseEntity<Response>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
    return Mono.just(ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(new Response("El correo ya está registrado.")));
  }

/*  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<Response>> handleGenericException(Exception ex) {
    return Mono.just(ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new Response("Ha ocurrido un error inesperado. Intente nuevamente más tarde.")));
  }*/
}
