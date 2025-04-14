package com.bci.smart.com.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("phones")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Phone {

  @Column("id")
  private UUID id;

  @Column("phone_number")
  private String phoneNumber;

  @Column("city_code")
  private String cityCode;

  @Column("country_code")
  private String countryCode;

  @Column("user_id")
  private UUID userId;
}
