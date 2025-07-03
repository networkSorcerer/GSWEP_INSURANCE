package com.gswep.insurance.form.entity;

import com.gswep.insurance.insurance.entity.Insurance;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="form")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Integer form_id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="insurance_id")
    private Insurance insurance;
}
