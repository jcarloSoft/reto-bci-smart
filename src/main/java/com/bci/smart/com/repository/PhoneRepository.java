package com.bci.smart.com.repository;

import com.bci.smart.com.model.Phone;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import java.util.UUID;
import reactor.core.publisher.Flux;

public interface PhoneRepository extends ReactiveCrudRepository<Phone, UUID> {
  Flux<Phone> findByUserId(UUID userId);
}
