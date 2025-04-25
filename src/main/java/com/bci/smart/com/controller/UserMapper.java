package com.bci.smart.com.controller;

import com.bci.smart.com.dto.UsersDTO;
import com.bci.smart.com.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

  // Convertir UsersDTO a la entidad Users
  Users toEntity(UsersDTO usersDTO);

  // Convertir entidad Users a DTO
  @Mapping(target = "phones", expression = "java(mapPhones(entity))")
  UsersDTO toDto(Users entity);

  // Mapeo de teléfonos
  default List<UsersDTO.Phones> mapPhones(Users entity) {

    return List.of();
  }
}
