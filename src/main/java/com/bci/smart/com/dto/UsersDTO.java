package com.bci.smart.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UsersDTO", description = "DTO para la creación y consulta de usuarios")
public class UsersDTO {

  @Schema(description = "Identificador del usuario", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
  private UUID id;

  @NotNull(message = "El nombre no puede ser nulo")
  @Schema(description = "Nombre el usuario", example = "Perico de los palotes", required = true)
  private String name;

  @NotBlank(message = "El correo no puede estar vacío")
  @Email(message = "Formato de correo inválido")
  @Schema(description = "Correo electrónico del usuario", example = "perico.palotes@gmail.com", required = true)
  private String email;

  @NotNull(message = "La clave no puede ser nula")
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
      message = "La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra y un número.")
  @Schema(description = "Contraseña (mínimo 8 caracteres, al menos una letra y un número)",
      example = "Peru1234", required = true)
  private String password;

  @Schema(description = "Lista de teléfonos asociados al usuario")
  private List<Phones> phones;

  @Schema(description = "Fecha de creación del registro", example = "2025-04-25T10:15:30")
  private LocalDateTime createAt;

  @Schema(description = "Fecha de la última sesión", example = "2025-04-25T12:00:00")
  private LocalDateTime ultimateSession;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(name = "Phone", description = "DTO para un teléfono de usuario")
  public static class Phones {

    @Schema(description = "Número de teléfono", example = "123456789")
    private String phoneNumber;

    @Schema(description = "Código de ciudad", example = "1")
    private String cityCode;

    @Schema(description = "Código de país", example = "57")
    private String countryCode;
  }
}

