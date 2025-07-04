package com.gswep.insurance.user.entity;

import com.gswep.insurance.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    private Integer phoneNumber;
    private String profilePictureUrl;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user")
    private List<Member> member;
}