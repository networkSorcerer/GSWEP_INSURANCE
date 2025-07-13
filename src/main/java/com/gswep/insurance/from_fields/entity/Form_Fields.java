package com.gswep.insurance.from_fields.entity;

import com.gswep.insurance.form.entity.Form;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="form_fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Form_Fields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fieldId")
    private Integer fieldId;
    private String fieldLabel;
    private Integer fieldOrder;
    @ManyToOne
    @JoinColumn(name ="formId")
    private Form form;
}
