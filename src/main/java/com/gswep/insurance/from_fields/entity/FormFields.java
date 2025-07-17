package com.gswep.insurance.from_fields.entity;

import com.gswep.insurance.field_answers.entity.FieldAnswers;
import com.gswep.insurance.form.entity.Form;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="form_fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fieldId")
    private Integer fieldId;

    private String fieldLabel;
    private Integer fieldOrder;

    @ManyToOne
    @JoinColumn(name ="formId")
    private Form form;

    @OneToOne(mappedBy = "formFields", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FieldAnswers fieldAnswers;


}
