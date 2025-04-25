package com.bci.smart.com.controller;

import com.bci.smart.com.dto.UserResponseDTO;
import com.bci.smart.com.dto.UsersDTO;
import com.bci.smart.com.exception.UserAlreadyExistsException;
import com.bci.smart.com.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UsersControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UsersController usersController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Test Create User - Success")
  public void testCreateUser() {
    UsersDTO userDTO = new UsersDTO();
    userDTO.setName("Juan Pérez");
    userDTO.setEmail("juan@example.com");
    userDTO.setPassword("password123");

    UserResponseDTO responseDTO = UserResponseDTO.builder()
        .id(UUID.randomUUID())
        .created(LocalDateTime.now())
        .modified(LocalDateTime.now())
        .lastLogin(LocalDateTime.now())
        .token("dummyToken")
        .isActive(true)
        .name("Juan Pérez")
        .email("juan@example.com")
        .phones(List.of())
        .build();

    when(userService.createUser(any(UsersDTO.class))).thenReturn(Mono.just(responseDTO));

    Mono<ResponseEntity<UserResponseDTO>> response = usersController.createUser(userDTO);

    StepVerifier.create(response)
        .consumeNextWith(entity -> {
          assert entity.getStatusCode().equals(HttpStatus.CREATED);
          assert entity.getBody() != null;
          assert entity.getBody().getEmail().equals("juan@example.com");
        })
        .verifyComplete();
  }

  @Test
  @DisplayName("Test Create User - Invalid Email")
  public void testCreateUser_InvalidEmail() {
    UsersDTO userDTO = new UsersDTO();
    userDTO.setName("Juan Pérez");
    userDTO.setEmail("invalid-email");
    userDTO.setPassword("password123");

    when(userService.createUser(any(UsersDTO.class)))
        .thenReturn(
            Mono.error(new IllegalArgumentException("El correo proporcionado no es válido.")));

    Mono<ResponseEntity<UserResponseDTO>> response = usersController.createUser(userDTO);

    StepVerifier.create(response)
        .expectErrorMatches(ex ->
            ex instanceof IllegalArgumentException &&
                ex.getMessage().equals("El correo proporcionado no es válido.")
        )
        .verify();
  }

  @Test
  @DisplayName("Test Create User - User Already Exists")
  public void testCreateUser_UserAlreadyExists() {
    UsersDTO userDTO = new UsersDTO();
    userDTO.setName("Juan Pérez");
    userDTO.setEmail("juan@example.com");
    userDTO.setPassword("password123");

    when(userService.createUser(any(UsersDTO.class)))
        .thenReturn(Mono.error(new UserAlreadyExistsException("El correo ya está registrado")));

    Mono<ResponseEntity<UserResponseDTO>> response = usersController.createUser(userDTO);

    StepVerifier.create(response)
        .expectErrorMatches(ex ->
            ex instanceof UserAlreadyExistsException &&
                ex.getMessage().equals("El correo ya está registrado")
        )
        .verify();
  }

  @Test
  @DisplayName("Test Get All Users - Success")
  public void testGetAllUsers() {
    UsersDTO user1 = new UsersDTO();
    user1.setId(UUID.randomUUID());
    user1.setName("Juan Carlos Hilario");
    user1.setEmail("hilario@bci.com");

    UsersDTO user2 = new UsersDTO();
    user2.setId(UUID.randomUUID());
    user2.setName("Mauricio Hilario ");
    user2.setEmail("mauricio@gmail.com");

    when(userService.getAllUsers()).thenReturn(Flux.just(user1, user2));

    Flux<UsersDTO> response = usersController.getAllUsers();

    StepVerifier.create(response)
        .expectNext(user1)
        .expectNext(user2)
        .verifyComplete();
  }

  @Test
  @DisplayName("Test Get All Users - Empty")
  public void testGetAllUsers_Empty() {
    when(userService.getAllUsers()).thenReturn(Flux.empty());

    Flux<UsersDTO> response = usersController.getAllUsers();

    StepVerifier.create(response)
        .expectNextCount(0)
        .verifyComplete();
  }
}
