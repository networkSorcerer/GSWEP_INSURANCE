package com.gswep.insurance.field_answers.entity;

import com.gswep.insurance.from_fields.entity.FormFields;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="field_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="answer_id")
    private Integer answer_id;
    private String answer_text;

    @OneToOne
    @JoinColumn(name = "fieldId")
    private FormFields formFields;


}
