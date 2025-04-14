package com.bci.smart.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
  private UUID id;
  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime lastLogin;
  private String token;
  private boolean isActive;
  private String name;
  private String email;
  private List<PhoneResponse> phones;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PhoneResponse {
    private String phoneNumber;
    private String cityCode;
    private String countryCode;
  }
}
