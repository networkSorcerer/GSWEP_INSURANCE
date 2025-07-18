package com.gswep.insurance.user.repository;

import com.gswep.insurance.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(Integer phoneNumber);

    User findByEmail(String email);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
