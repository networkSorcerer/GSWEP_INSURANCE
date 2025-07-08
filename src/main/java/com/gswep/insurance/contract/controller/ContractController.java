package com.gswep.insurance.contract.controller;

import com.gswep.insurance.contract.dto.ContractResponseDTO;
import com.gswep.insurance.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class ContractController {
    @Autowired
    ContractService contractService;
    @GetMapping("/list")
    public ResponseEntity<Map<String , Object>> contractList(){
        Map<String , Object> resultMap = new HashMap<>();
        List<ContractResponseDTO> list = contractService.list();
        resultMap.put("list",list);
        return ResponseEntity.ok(resultMap);
    }
}
