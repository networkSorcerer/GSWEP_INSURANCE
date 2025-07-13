package com.gswep.insurance.field_answers.service;

import com.gswep.insurance.field_answers.dto.FieldAnswersRequestDTO;
import com.gswep.insurance.field_answers.entity.Field_Answers;
import com.gswep.insurance.field_answers.repository.FieldsAnswersRepository;
import com.gswep.insurance.from_fields.entity.Form_Fields;
import com.gswep.insurance.from_fields.repository.FormFieldsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FieldAnswersService {
    private final FieldsAnswersRepository fieldsAnswersRepository;
    private final FormFieldsRepository formFieldsRepository;
    public FieldAnswersService(FieldsAnswersRepository fieldsAnswersRepository, FormFieldsRepository formFieldsRepository) {
        this.fieldsAnswersRepository = fieldsAnswersRepository;
        this.formFieldsRepository = formFieldsRepository;
    }


    public boolean addAnswers(FieldAnswersRequestDTO fieldAnswersRequestDTO) {
        try {
            Form_Fields form_fields = formFieldsRepository.findById(fieldAnswersRequestDTO.getFieldsId())
                    .orElseThrow(()-> new RuntimeException("해당 필드가 존재하지 않습니다."));
            Field_Answers fieldAnswers = new Field_Answers();
            fieldAnswers.setForm_fields(form_fields);
            fieldAnswers.setAnswer_text(fieldAnswersRequestDTO.getAnswers());
            fieldsAnswersRepository.save(fieldAnswers);
            return true;
        } catch (Exception e) {
            log.error("field answers 등록 실패 : {}", e.getMessage());
            return false;
        }
    }
}
