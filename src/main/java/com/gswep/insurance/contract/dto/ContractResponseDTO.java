package com.gswep.insurance.contract.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponseDTO {
    private Long contract_id;

    private String product_code;

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
