package com.gswep.insurance.form.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormResponseDTO {
        private Long formId;
        private String formName;
        private String productCode;
}
