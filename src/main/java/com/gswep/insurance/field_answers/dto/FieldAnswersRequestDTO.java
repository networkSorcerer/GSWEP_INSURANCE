package com.gswep.insurance.field_answers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldAnswersRequestDTO {
    private Integer fieldsId;
    private String answers;
    private Integer fieldAnswersId;
}
