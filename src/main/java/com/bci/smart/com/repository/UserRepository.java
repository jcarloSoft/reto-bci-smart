package com.bci.smart.com.repository;


import com.bci.smart.com.model.Users;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<Users, UUID> {
  Mono<Users> findByEmail(String email);

}
