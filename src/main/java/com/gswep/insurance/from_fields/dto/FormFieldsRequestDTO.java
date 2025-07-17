package com.gswep.insurance.from_fields.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormFieldsRequestDTO {
    private Integer fieldsId;
    private Long formId;
    private String label;
    private Integer order;
}
