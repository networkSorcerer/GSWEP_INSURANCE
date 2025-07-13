package com.gswep.insurance.from_fields.service;

import com.gswep.insurance.form.entity.Form;
import com.gswep.insurance.form.repository.FormRepository;
import com.gswep.insurance.from_fields.dto.FormFieldsRequestDTO;
import com.gswep.insurance.from_fields.dto.FormFieldsResponseDTO;
import com.gswep.insurance.from_fields.entity.Form_Fields;
import com.gswep.insurance.from_fields.repository.FormFieldsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FormFieldsService {
    private final FormFieldsRepository formFieldsRepository;
    private final FormRepository formRepository;
    public FormFieldsService(FormFieldsRepository formFieldsRepository, FormRepository formRepository) {
        this.formFieldsRepository = formFieldsRepository;
        this.formRepository = formRepository;
    }

    public List<FormFieldsResponseDTO> getFields(Integer formId) {
        List<Form_Fields> form_fields = formFieldsRepository.findByForm_formId(formId);
        List<FormFieldsResponseDTO> formFieldsResponseDTOList = new ArrayList<>();
        for(Form_Fields form_fields1 : form_fields){
            formFieldsResponseDTOList.add(convertEntityToDTO(form_fields1));
        }
        return formFieldsResponseDTOList;
    }

    private FormFieldsResponseDTO convertEntityToDTO(Form_Fields formFields1) {
        FormFieldsResponseDTO formFieldsResponseDTO = new FormFieldsResponseDTO();

        formFieldsResponseDTO.setFormId(formFields1.getForm().getFormId());
        formFieldsResponseDTO.setLabel(formFields1.getFieldLabel());
        formFieldsResponseDTO.setOrder(formFields1.getFieldOrder());

        return formFieldsResponseDTO;
    }

    public boolean addFields(FormFieldsRequestDTO formFieldsRequestDTO) {
        try{
            Form form = formRepository.findById(formFieldsRequestDTO.getFormId())
                    .orElseThrow(()-> new RuntimeException("해당 form 이 존재하지 않습니다."));
            Form_Fields form_fields = new Form_Fields();
            form_fields.setForm(form);
            form_fields.setFieldLabel(formFieldsRequestDTO.getLabel());
            form_fields.setFieldOrder(formFieldsRequestDTO.getOrder());
            formFieldsRepository.save(form_fields);
            return true;
        }catch (Exception e){
            log.error("form 라벨 등록 실패 : {}", e.getMessage());
            return false;
        }

    }

    public boolean updateFields(FormFieldsRequestDTO formFieldsRequestDTO) {
        try {
            Form_Fields form_fields = formFieldsRepository.findById(formFieldsRequestDTO.getFieldsId())
                            .orElseThrow(()-> new RuntimeException("해당 필드를 발견하지 못했습니다."));

            form_fields.setFieldLabel(formFieldsRequestDTO.getLabel());
            form_fields.setFieldOrder(formFieldsRequestDTO.getOrder());
            formFieldsRepository.save(form_fields);
            return true;
        }catch (Exception e){
            log.error("form 라벨 업데이트 실패 : {}", e.getMessage());
            return false;
        }
    }

    public boolean deleteFields(Integer fieldsId) {
        try {
            Form_Fields form_fields = formFieldsRepository.findById(fieldsId)
                    .orElseThrow(()-> new RuntimeException("해당 필드를 발견하지 못했습니다."));
            formFieldsRepository.delete(form_fields);
            return true;
        }catch (Exception e) {
            log.error("form 라벨 삭제 실패 : {}", e.getMessage());
            return false;
        }
    }
}
