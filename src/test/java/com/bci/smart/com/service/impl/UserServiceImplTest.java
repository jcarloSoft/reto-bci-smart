package com.bci.smart.com.service.impl;

import com.bci.smart.com.dto.UserResponseDTO;
import com.bci.smart.com.dto.UsersDTO;
import com.bci.smart.com.exception.UserAlreadyExistsException;
import com.bci.smart.com.controller.UserMapper;
import com.bci.smart.com.model.Users;
import com.bci.smart.com.repository.PhoneRepository;
import com.bci.smart.com.repository.UserRepository;
import com.bci.smart.com.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PhoneRepository phoneRepository;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserServiceImpl userService;

  private UsersDTO userDTO;

  @BeforeEach
  public void setUp() {
    userDTO = new UsersDTO();
    userDTO.setName("Juan Pérez");
    userDTO.setEmail("juan@example.com");
    userDTO.setPassword("password123");
  }

  @Test
  public void testCreateUserWhenUserDoesNotExist() {
    UUID userId = UUID.randomUUID();
    Users user = new Users(userId, "Juan Pérez", "juan@example.com", "password123",
        LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());

    UsersDTO userDTO = new UsersDTO();
    userDTO.setName("Juan Hilario");
    userDTO.setEmail("juanhilario@bci.com");
    userDTO.setPassword("password123");
    userDTO.setPhones(List.of());

    UserResponseDTO responseDTO = UserResponseDTO.builder()
        .id(userId)
        .created(LocalDateTime.now())
        .modified(LocalDateTime.now())
        .lastLogin(LocalDateTime.now())
        .token("dummyToken")
        .isActive(true)
        .name("Juan Hilario")
        .email("juanhilario@bci.com")
        .phones(List.of())
        .build();

    when(userRepository.findByEmail(any(String.class))).thenReturn(Mono.empty());
    when(userMapper.toEntity(any(UsersDTO.class))).thenReturn(user);
    when(userRepository.save(any(Users.class))).thenReturn(Mono.just(user));
    when(phoneRepository.saveAll(anyList())).thenReturn(Flux.empty());
    when(phoneRepository.findByUserId(any(UUID.class))).thenReturn(Flux.empty());

    Mono<UserResponseDTO> result = userService.createUser(userDTO);

    StepVerifier.create(result)
        .expectNextMatches(userResponse -> userResponse.getEmail().equals("juan@example.com"))
        .verifyComplete();

    verify(userRepository, times(1)).findByEmail(any(String.class));
    verify(userRepository, times(1)).save(any(Users.class)); // Verificamos que se haya guardado
  }

  @Test
  public void testCreateUserWhenUserAlreadyExists() {
    when(userRepository.findByEmail(any(String.class)))
        .thenReturn(Mono.just(new Users())); // Usuario ya existe

    Mono<UserResponseDTO> result = userService.createUser(userDTO);

    StepVerifier.create(result)
        .expectErrorMatches(ex ->
            ex instanceof UserAlreadyExistsException &&
                ex.getMessage().equals("El correo ya está registrado.")
        )
        .verify();

    verify(userRepository, times(1)).findByEmail(any(String.class));
    verify(userRepository, times(0)).save(any(Users.class));
  }


  @Test
  public void testCreateUserWhenEmailIsInvalid() {
    userDTO.setEmail("invalid-email");

    Mono<UserResponseDTO> result = userService.createUser(userDTO);

    StepVerifier.create(result)
        .expectErrorMatches(ex -> ex instanceof IllegalArgumentException &&
            ex.getMessage().equals("El correo proporcionado no es válido."))
        .verify();

    verify(userRepository, times(0)).findByEmail(any(String.class));
    verify(userRepository, times(0)).save(any(Users.class));
  }
}
