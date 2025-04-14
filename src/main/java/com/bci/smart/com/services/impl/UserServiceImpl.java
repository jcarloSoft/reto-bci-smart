package com.bci.smart.com.services.impl;

import com.bci.smart.com.dto.UserResponseDTO;
import com.bci.smart.com.dto.UsersDTO;
import com.bci.smart.com.exception.UserAlreadyExistsException;
import com.bci.smart.com.handler.UserMapper;
import com.bci.smart.com.model.Phone;
import com.bci.smart.com.model.Users;
import com.bci.smart.com.repository.PhoneRepository;
import com.bci.smart.com.repository.UserRepository;
import com.bci.smart.com.services.UserService;
import com.bci.smart.com.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PhoneRepository phoneRepository;
  private final UserMapper userMapper;

  @Override
  public Mono<UserResponseDTO> createUser(UsersDTO userDTO) {
    return userRepository.findByEmail(userDTO.getEmail()) // Verificar si el email ya está registrado
        .flatMap(existingUser -> {
          // Si existe, lanzar la excepción personalizada y asegurar el tipo con Mono.<UserResponseDTO>error
          return Mono.<UserResponseDTO>error(new UserAlreadyExistsException("El correo ya está registrado"));
        })
        .switchIfEmpty(Mono.defer(() -> {
          UUID userId = UUID.randomUUID();
          Users user = userMapper.toEntity(userDTO);
          user.setId(userId);
          user.setCreateAt(LocalDateTime.now());
          user.setUpdateAt(LocalDateTime.now());
          user.setUltimateSession(LocalDateTime.now());

          return userRepository.save(user)
              .flatMap(savedUser -> {
                List<Phone> phones = mapPhones(userDTO.getPhones(), userId);
                return phoneRepository.saveAll(phones)
                    .then(phoneRepository.findByUserId(savedUser.getId()).collectList())
                    .map(savedPhones -> buildUserResponseDTO(savedUser, savedPhones));
              });
        }));
  }



  @Override
  public Mono<UserResponseDTO> updateUser(UsersDTO userDTO) {
    return userRepository.findById(userDTO.getId())
        .flatMap(existingUser -> {
          Users updatedUser = userMapper.toEntity(userDTO);
          updatedUser.setId(existingUser.getId());
          updatedUser.setUltimateSession(LocalDateTime.now());

          return userRepository.save(updatedUser)
              .flatMap(savedUser -> {
                List<Phone> phones = mapPhones(userDTO.getPhones(), savedUser.getId());
                return phoneRepository.saveAll(phones)
                    .then(Mono.just(buildUserResponseDTO(savedUser, phones)));
              });
        });
  }

  @Override
  public Mono<UsersDTO> deleteUser(UUID id) {
    return null;
  }

  @Override
  public Flux<UsersDTO> getAllUsers() {
    return userRepository.findAll()
        .flatMap(user -> phoneRepository.findByUserId(user.getId())
            .collectList()
            .map(phones -> {
              UsersDTO usersDTO = userMapper.toDto(user);
              usersDTO.setPhones(phones.stream()
                  .map(phone -> new UsersDTO.Phones(
                      phone.getPhoneNumber(),
                      phone.getCityCode(),
                      phone.getCountryCode()))
                  .toList());
              return usersDTO;
            }));
  }

  private List<Phone> mapPhones(List<UsersDTO.Phones> phonesDTO, UUID userId) {
    return phonesDTO.stream()
        .map(phoneDTO -> new Phone(
            UUID.randomUUID(),
            phoneDTO.getPhoneNumber(),
            phoneDTO.getCityCode(),
            phoneDTO.getCountryCode(),
            userId
        ))
        .toList();
  }

  private UserResponseDTO buildUserResponseDTO(Users savedUser, List<Phone> phones) {
    String token = JwtUtil.generateToken(savedUser.getId().toString(), savedUser.getEmail());

    return UserResponseDTO.builder()
        .id(savedUser.getId())
        .created(savedUser.getCreateAt())
        .modified(savedUser.getUpdateAt())
        .lastLogin(savedUser.getUltimateSession())
        .token(token)
        .isActive(true)
        .name(savedUser.getName())
        .email(savedUser.getEmail())
        .phones(phones.stream()
            .map(phone -> new UserResponseDTO.PhoneResponse(
                phone.getPhoneNumber(),
                phone.getCityCode(),
                phone.getCountryCode()))
            .toList())
        .build();
  }

}
