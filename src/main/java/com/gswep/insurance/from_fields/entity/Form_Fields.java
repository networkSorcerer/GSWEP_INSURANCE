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
    @Column(name="field_id")
    private Integer field_id;

    private String field_label;
    private String field_type;
    private Boolean is_required;
    private Integer field_order;

    @ManyToOne
    @JoinColumn(name ="form_id")
    private Form form;
}
