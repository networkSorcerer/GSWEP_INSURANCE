package com.gswep.insurance.contract.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;


@Entity
@Table(name="contract")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="contractId")
    private Long contractId;
    @Column(name="productCode")
    private String productCode;

    private String product_name;

    private String contract_no;

    private String co;

    private Date start_date;

    private Date end_date;

    private Date contract_date;

    private String co_insurance;

    private Integer total_insurance_pay;

    private Integer our_insurance_premium;

    private String insurance_state;

    private Integer user_id;

    private String user_name;

    private String store;

    private String shared;

    private Integer member_id;

    private String member_name;

    private Integer pre_contract_no;

    private String etc;
}
