package com.gswep.insurance.insurance.entity;

import com.gswep.insurance.form.entity.Form;
import com.gswep.insurance.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="insurance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="insurance_id")
    private Integer insurance_id;

    @Column(nullable = false)
    private String insurance_name;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @OneToMany
    @JoinColumn(name = "member_id")
    private Member member;

}
