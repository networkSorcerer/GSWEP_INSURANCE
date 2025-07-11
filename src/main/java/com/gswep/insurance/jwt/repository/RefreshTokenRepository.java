package com.gswep.insurance.jwt.repository;

import com.gswep.insurance.jwt.entity.RefreshToken;
import com.gswep.insurance.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken>findByRefreshToken(String refreshToken);
//    Optional<RefreshToken> findByMemberId(Integer user_id);
    Optional<RefreshToken> findByUser(User user);
//    void deleteByUserId(Integer user_id);
}
