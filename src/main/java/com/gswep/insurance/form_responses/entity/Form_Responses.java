package com.gswep.insurance.form_responses.entity;

import com.gswep.insurance.form.entity.Form;
import com.gswep.insurance.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity
@Table(name="form_responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Form_Responses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="response_id")
    private Integer response_id;

    private Date submitted_at;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
