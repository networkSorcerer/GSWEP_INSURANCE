package com.gswep.insurance.form.service;

import com.gswep.insurance.contract.entity.ContractEntity;
import com.gswep.insurance.contract.repository.ContractRepository;
import com.gswep.insurance.form.dto.FormRequestDTO;
import com.gswep.insurance.form.dto.FormResponseDTO;
import com.gswep.insurance.form.entity.Form;
import com.gswep.insurance.form.repository.FormRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FormService {
    private final ContractRepository contractRepository;
    private final FormRepository formRepository;
    public FormService(ContractRepository contractRepository, FormRepository formRepository) {
        this.contractRepository = contractRepository;
        this.formRepository = formRepository;
    }

    public boolean addForm(FormRequestDTO formRequestDTO) {
        try{
            ContractEntity contractEntity = contractRepository.findById(formRequestDTO.getContractId())
                    .orElseThrow(()-> new RuntimeException("해당 계약은 존재하지 않습니다."));

            Form form = new Form();
            form.setContractEntity(contractEntity);
            form.setProductCode(formRequestDTO.getProductCode());
            formRepository.save(form);
            return true;
        }catch (Exception e){
            log.error("양식 생성 실패 :{}", e.getMessage());
            return false;
        }
    }

    public String getProductCode(Integer contractId) {
        log.info("contractId", contractId);
        String findProductCode = contractRepository.findProductCodeByContractId(contractId);
        return findProductCode;
    }

    public List<FormResponseDTO> readForm(String productCode) {

            List<Form> forms = formRepository.findByContractEntity_ProductCode(productCode);
            List<FormResponseDTO> formResponseDTOList = new ArrayList<>();
            for(Form form : forms){
                formResponseDTOList.add(convertEntityToDTO(form));
            }
            return formResponseDTOList;
    }

    private FormResponseDTO convertEntityToDTO(Form form) {
        FormResponseDTO formResponseDTO = new FormResponseDTO();
        formResponseDTO.setProductCode(form.getProductCode());
        formResponseDTO.setFormId(form.getFormId());
        return formResponseDTO;
    }


    public Integer getlatestId(FormRequestDTO formRequestDTO) {
        return formRepository.findLatestId(formRequestDTO.getContractId());
    }

    public Integer findFormId(Long contractId) {
        return formRepository.findFormIdByContractId(contractId);
    }
}
