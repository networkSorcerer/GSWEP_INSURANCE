package com.gswep.insurance.field_answers.service;

import com.gswep.insurance.field_answers.dto.FieldAnswersRequestDTO;
import com.gswep.insurance.field_answers.dto.FieldAnswersResponseDTO;
import com.gswep.insurance.field_answers.entity.FieldAnswers;
import com.gswep.insurance.field_answers.repository.FieldsAnswersRepository;
import com.gswep.insurance.from_fields.entity.FormFields;
import com.gswep.insurance.from_fields.repository.FormFieldsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        log.info("fieldId 확인 : {}", fieldAnswersRequestDTO.getFieldsId());
        try {
            FormFields form_fields = formFieldsRepository.findById(fieldAnswersRequestDTO.getFieldsId())
                    .orElseThrow(()-> new RuntimeException("해당 필드가 존재하지 않습니다."));
            FieldAnswers fieldAnswers = new FieldAnswers();
            fieldAnswers.setFormFields(form_fields);
            fieldAnswers.setAnswer_text(fieldAnswersRequestDTO.getAnswers());
            fieldsAnswersRepository.save(fieldAnswers);
            return true;
        } catch (Exception e) {
            log.error("field answers 등록 실패 : {}", e.getMessage());
            return false;
        }
    }

    public boolean updateAnswers(FieldAnswersRequestDTO fieldAnswersRequestDTO) {
        try{
            FormFields form_fields = formFieldsRepository.findById(fieldAnswersRequestDTO.getFieldsId())
                    .orElseThrow(()-> new RuntimeException("해당 필드 가 존재 하지않습니다."));
            FieldAnswers fieldAnswers = fieldsAnswersRepository.findById(fieldAnswersRequestDTO.getFieldAnswersId())
                    .orElseThrow(()-> new RuntimeException("해당 응답이 존재 하지 않습니다."));

            FieldAnswers fieldAnswers1 = new FieldAnswers();
            fieldAnswers1.setAnswer_id(fieldAnswers.getAnswer_id());
            fieldAnswers1.setAnswer_text(fieldAnswersRequestDTO.getAnswers());
            fieldAnswers1.setFormFields(form_fields);
            fieldsAnswersRepository.save(fieldAnswers1);
            return true;
        } catch (Exception e) {
            log.error("field answers 업데이트 실패 : {}", e.getMessage());
            return false;
        }
    }

    public boolean deleteAnswers(Integer answerId) {
        try {
            FieldAnswers fieldAnswers = fieldsAnswersRepository.findById(answerId)
                    .orElseThrow(()-> new RuntimeException("해당 필드 응답을 찾지 못했습니다."));
            fieldsAnswersRepository.deleteById(answerId);
            return true;
        }catch (Exception e) {
            log.error("필드 응답 삭제 중 에러 발생 : {}", e.getMessage());
            return false;
        }

    }

    public FieldAnswersResponseDTO readAnswers(Integer formFiledId) {
        FormFields formFields = formFieldsRepository.findById(formFiledId)
                .orElseThrow(()-> new RuntimeException("해당 필드가 존재하지 않습니다."));
        return convertEntityToDTO(formFields.getFieldAnswers());
    }

    private FieldAnswersResponseDTO convertEntityToDTO(FieldAnswers fieldAnswers) {
        FieldAnswersResponseDTO fieldAnswersResponseDTO = new FieldAnswersResponseDTO();
        fieldAnswersResponseDTO.setAnswers(fieldAnswers.getAnswer_text());
        fieldAnswersResponseDTO.setFieldsId(fieldAnswers.getFormFields().getFieldId());
        fieldAnswersResponseDTO.setId(fieldAnswers.getAnswer_id());
        return fieldAnswersResponseDTO;
    }
}
