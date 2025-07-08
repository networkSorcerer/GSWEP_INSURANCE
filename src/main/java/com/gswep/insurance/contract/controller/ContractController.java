package com.gswep.insurance.contract.controller;

import com.gswep.insurance.contract.dto.ContractResponseDTO;
import com.gswep.insurance.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Map<String , Object>> contractList(@RequestParam(required = false) String select, @RequestParam(required = false) String keyword){
        log.info("selectValue: {}",select);
        log.info("keyword: {}",keyword);

        Map<String , Object> resultMap = new HashMap<>();
        List<ContractResponseDTO> list;

        if (keyword == null || keyword.isEmpty()) {
            list = contractService.list();
        }
        else {
            list = contractService.searchList(select, keyword); // 예: 키워드로 필터링
        }

        resultMap.put("list", list);
        return ResponseEntity.ok(resultMap);
    }
}
