package com.bci.smart.com.controller;

import com.bci.smart.com.dto.UserResponseDTO;
import com.bci.smart.com.dto.UsersDTO;
import com.bci.smart.com.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersController {

  private final UserService userService;

  @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario y devuelve el token JWT.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
          content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Solicitud inválida",
          content = @Content(schema = @Schema(example = "{\"message\": \"email: Formato de correo inválido\"}"))),
      @ApiResponse(responseCode = "409", description = "El correo ya está registrado",
          content = @Content(schema = @Schema(example = "{\"message\": \"El correo ya está registrado.\"}"))),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor",
          content = @Content(schema = @Schema(example = "{\"message\": \"Ha ocurrido un error técnico.\"}")))
  })

  @PostMapping
  public Mono<ResponseEntity<UserResponseDTO>> createUser(@RequestBody @Valid UsersDTO userDTO) {
    return userService.createUser(userDTO)
        .map(userResponse -> ResponseEntity.status(HttpStatus.CREATED).body(userResponse));
  }

  @Operation(summary = "Mostrar todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados en el sistema.")
  @ApiResponse(responseCode = "200", description = "Lista de usuarios retornada exitosamente",
      content = @Content(schema = @Schema(implementation = UsersDTO.class)))
  @ApiResponse(responseCode = "500", description = "Error interno del servidor",
      content = @Content(schema = @Schema(example = "{\"message\": \"Ha ocurrido un error inesperado.\"}")))
  @GetMapping
  public Flux<UsersDTO> getAllUsers() {
    return userService.getAllUsers();
  }
}
