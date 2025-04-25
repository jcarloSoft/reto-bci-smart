package com.bci.smart.com.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

  private UUID id;

  private String name;

  private String email;

  private String password;

  private LocalDateTime createAt;
  private LocalDateTime updateAt;
  private LocalDateTime ultimateSession;

}
