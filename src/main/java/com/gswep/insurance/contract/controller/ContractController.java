package com.gswep.insurance.contract.controller;

import com.gswep.insurance.contract.dto.ContractResponseDTO;
import com.gswep.insurance.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<Map<String , Object>> contractList(@RequestParam(required = false) String select,
                                                             @RequestParam(required = false) String keyword,
                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "5") int size){


        log.info("selectValue: {}",select);
        log.info("keyword: {}",keyword);



        Map<String , Object> resultMap = new HashMap<>();
        List<ContractResponseDTO> list;
        Long totalCount;

        if (keyword == null || keyword.isEmpty()) {
            list = contractService.list(page,size);
            totalCount = contractService.totalCount();
        }
        else {
            list = contractService.searchList(select, keyword,page, size); // 예: 키워드로 필터링
            totalCount = contractService.searchTotalCount(select,keyword);
        }
        resultMap.put("currentPage", page);    // 현재 페이지
        resultMap.put("size", size);           // 페이지 크기
        resultMap.put("totalCount", totalCount);
        resultMap.put("list", list);
        return ResponseEntity.ok(resultMap);
    }
    @GetMapping("/id")
    public ResponseEntity<Map<String,Object>> getDataById(@RequestParam(required = false) Long id){
        Map<String , Object> resultMap = new HashMap<>();
        ContractResponseDTO data = contractService.findUserInsurance(id);
        resultMap.put("data",data);

        return ResponseEntity.ok(resultMap);
    }
}
