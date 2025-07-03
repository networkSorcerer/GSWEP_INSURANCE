package com.gswep.insurance.user.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false)
    private UserRoleEnum role;

    // 다른 필드 (예: email 등) 필요 시 추가
}