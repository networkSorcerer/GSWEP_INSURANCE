package com.gswep.insurance.from_fields.dto;

import com.gswep.insurance.field_answers.dto.FieldAnswersResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormFieldsResponseDTO {
    private Long formId;
    private Integer fieldId;
    private String label;
    private Integer order;

    private FieldAnswersResponseDTO fieldAnswersResponseDTO;
}
