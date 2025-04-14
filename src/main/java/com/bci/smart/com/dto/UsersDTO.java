package com.bci.smart.com.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsersDTO {

  private UUID id;
  @NotNull(message = "El nombre no puede ser nulo")
  private String name;
  @NotNull(message = "El correo no puede ser nulo")
  @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "El correo no tiene un formato válido")
  private String email;
  @NotNull(message = "La clave no puede ser nula")
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La clave debe tener al menos 8 caracteres, incluir al menos una letra y un número.")
  private String password;
  private List<Phones> phones;  // Lista de teléfonos
  private LocalDateTime createAt;
  private LocalDateTime ultimateSession;

  @Data
  @Builder
  @AllArgsConstructor
  public static class Phones {
    private String phoneNumber;
    private String cityCode;
    private String countryCode;
  }
}
