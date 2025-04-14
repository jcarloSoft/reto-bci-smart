package com.bci.smart.com.exception;

import com.bci.smart.com.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import org.springframework.validation.BindingResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Mono<ResponseEntity<Response>> handleValidationException(MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    StringBuilder errorMessage = new StringBuilder("Errores de validación:");
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      errorMessage.append("\nCampo: ").append(fieldError.getField())
          .append(" - ").append(fieldError.getDefaultMessage());
    }
    Response response = new Response();
    response.setMessage(errorMessage.toString());
    return Mono.just(new ResponseEntity<>(response, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(RuntimeException.class)
  public Mono<ResponseEntity<Response>> handleRuntimeException(RuntimeException ex) {
    Response response = new Response();
    response.setMessage("Error Runtime Handler Activado: " + ex.getMessage());
    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(response));
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public Mono<ResponseEntity<Response>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
    Response response = new Response();
    response.setMessage(ex.getMessage());
    return Mono.just(new ResponseEntity<>(response, HttpStatus.CONFLICT));
  }
}