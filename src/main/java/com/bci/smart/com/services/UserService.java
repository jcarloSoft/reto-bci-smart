package com.bci.smart.com.services;

import com.bci.smart.com.dto.UserResponseDTO;
import com.bci.smart.com.dto.UsersDTO;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
  Mono<UserResponseDTO> createUser(UsersDTO userDTO);
  Mono<UserResponseDTO> updateUser(UsersDTO userDTO);
  Mono<UsersDTO> deleteUser(UUID id);
  Flux<UsersDTO> getAllUsers();

}
