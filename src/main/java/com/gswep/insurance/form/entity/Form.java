package com.gswep.insurance.form.entity;

import com.gswep.insurance.contract.entity.ContractEntity;
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
    @Column(name = "formId")
    private Long formId;


    @Column(nullable = false)
    private String productCode;

    @ManyToOne
    @JoinColumn(name="contractId")
    private ContractEntity contractEntity;

}
