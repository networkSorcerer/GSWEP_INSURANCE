package com.gswep.insurance.user.entity;

import com.gswep.insurance.jwt.entity.RefreshToken;
import com.gswep.insurance.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true, name = "name")
    private String username;

    @Column(nullable = true)
    private String password;

    private Integer phoneNumber;
    private String profilePictureUrl;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = true)
    private UserRoleEnum role;

    @Column(name = "provider", length = 50)
    private String provider;

    @Column(name = "provider_id", length = 255)
    private String providerId;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshTokens = new ArrayList<>();
}