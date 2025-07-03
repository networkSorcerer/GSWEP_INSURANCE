package com.gswep.insurance.member.entity;

import com.gswep.insurance.insurance.entity.Insurance;
import com.gswep.insurance.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Member {
    //CREATE TABLE members (
//        member_id INT PRIMARY KEY,
//        member_name VARCHAR(255),
//birth_day DATE,
//employee_id INT NOT NULL,
//insurance_id INT NOT NULL
//);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Integer memberId;

    @Column(nullable = false)
    private String member_name;

    @Column(nullable = false)
    private LocalDateTime birth_day;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

}

