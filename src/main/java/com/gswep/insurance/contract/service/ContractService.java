package com.gswep.insurance.contract.service;

import com.gswep.insurance.contract.dto.ContractResponseDTO;
import com.gswep.insurance.contract.entity.ContractEntity;
import com.gswep.insurance.contract.repository.ContractRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;


    public List<ContractResponseDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ContractEntity> contractEntities = contractRepository.findAll(pageable).getContent();
        List<ContractResponseDTO> contractResponseDTOS = new ArrayList<>();
        for (ContractEntity contractEntity : contractEntities){
            contractResponseDTOS.add(convertContractResponseDTO(contractEntity));
        }
        return contractResponseDTOS;
    }
    public ContractResponseDTO findUserInsurance(Long id) {
        ContractEntity contractEntity = contractRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("해당 회원 정보가 없습니다."));
        return convertContractResponseDTO(contractEntity);
    }

    public List<ContractResponseDTO> searchList(String select,String keyword, int page, int size ) {
        Pageable pageable = PageRequest.of(page, size);
        List<ContractEntity> contractEntities;
        if("contract_no".equals(select)){
            contractEntities = contractRepository.findByContract_no(keyword,pageable);
            log.info("hey contract no:{}",contractEntities);
        } else {
            contractEntities = contractRepository.findByMemberName(keyword,pageable);
            log.info("hey member:{}",contractEntities);
        }
        List<ContractResponseDTO> contractResponseDTOS = new ArrayList<>();
        for(ContractEntity contractEntity : contractEntities) {
            contractResponseDTOS.add(convertContractResponseDTO(contractEntity));
        }
        return contractResponseDTOS;
    }


    public Long totalCount() {
        return contractRepository.count();  // 전체 아이템 개수 리턴
    }

    public Long searchTotalCount(String select,String keyword) {
        if("contract_no".equals(select)){
            return contractRepository.findByContract_noCount(keyword);
        } else {
            return contractRepository.findByMemberNameCount(keyword);
        }
    }


    private ContractResponseDTO convertContractResponseDTO(ContractEntity contractEntity){
        ContractResponseDTO contractResponseDTO = new ContractResponseDTO();
        contractResponseDTO.setContract_id(contractEntity.getContract_id());
        contractResponseDTO.setProduct_code(contractEntity.getProduct_code());
        contractResponseDTO.setProduct_name(contractEntity.getProduct_name());
        contractResponseDTO.setContract_no(contractEntity.getContract_no());
        contractResponseDTO.setCo(contractEntity.getCo());
        contractResponseDTO.setStart_date(contractEntity.getStart_date());
        contractResponseDTO.setEnd_date(contractEntity.getEnd_date());
        contractResponseDTO.setContract_date(contractEntity.getContract_date());
        contractResponseDTO.setCo_insurance(contractEntity.getCo_insurance());
        contractResponseDTO.setTotal_insurance_pay(contractEntity.getTotal_insurance_pay());
        contractResponseDTO.setOur_insurance_premium(contractEntity.getOur_insurance_premium());
        contractResponseDTO.setInsurance_state(contractEntity.getInsurance_state());
        contractResponseDTO.setUser_id(contractEntity.getUser_id());
        contractResponseDTO.setUser_name(contractEntity.getUser_name());
        contractResponseDTO.setMember_id(contractEntity.getMember_id());
        contractResponseDTO.setMember_name(contractEntity.getMember_name());
        contractResponseDTO.setStore(contractEntity.getStore());
        contractResponseDTO.setPre_contract_no(contractEntity.getPre_contract_no());
        contractResponseDTO.setShared(contractEntity.getShared());
        contractResponseDTO.setEtc(contractEntity.getEtc());
        return contractResponseDTO;
    }



}
