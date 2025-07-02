package com.gswep.insurance.jwt.repository;

import com.gswep.insurance.jwt.entity.RefreshToken;
import com.gswep.insurance.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByMemberId(Integer memberId);
    Optional<RefreshToken> findByMember(Member member);
    Optional<RefreshToken> findByRefreshToken(String token);

    void deleteByMemberId(Integer memberId);
}
