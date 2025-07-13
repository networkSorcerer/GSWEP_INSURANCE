package com.gswep.insurance.field_answers.entity;

import com.gswep.insurance.form_responses.entity.Form_Responses;
import com.gswep.insurance.from_fields.entity.Form_Fields;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="field_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Field_Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="answer_id")
    private Integer answer_id;
    private String answer_text;

    @OneToOne
    @JoinColumn(name = "field_id")
    private Form_Fields form_fields;

}
