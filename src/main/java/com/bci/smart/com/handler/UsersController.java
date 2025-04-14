package com.bci.smart.com.handler;

import com.bci.smart.com.dto.UserResponseDTO;
import com.bci.smart.com.dto.UsersDTO;
import com.bci.smart.com.services.UserService;
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

  @PostMapping("/create")
  public Mono<ResponseEntity<UserResponseDTO>> createUser(@RequestBody @Valid UsersDTO userDTO) {
    return userService.createUser(userDTO)
        .map(userResponse -> ResponseEntity.status(HttpStatus.CREATED).body(userResponse));
  }

  @GetMapping
  public Flux<UsersDTO> getAllUsers() {
    return userService.getAllUsers();
  }
}
