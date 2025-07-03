package com.gswep.insurance.insurance.entity;

import com.gswep.insurance.form.entity.Form;
import com.gswep.insurance.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "insurance")
    private List<Form> forms;


    @OneToMany(mappedBy = "insurance")
    private List<Member> member;

}
