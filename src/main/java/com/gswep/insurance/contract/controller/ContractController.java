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
                                                             @RequestParam(value = "size", defaultValue = "10") int size){


        log.info("selectValue: {}",select);
        log.info("keyword: {}",keyword);


        Map<String , Object> resultMap = new HashMap<>();
        List<ContractResponseDTO> list;

        if (keyword == null || keyword.isEmpty()) {
            list = contractService.list(page,size);
        }
        else {
            list = contractService.searchList(select, keyword,page, size); // 예: 키워드로 필터링
        }

        resultMap.put("list", list);
        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> listBoards(@RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        // PageRequest 객체 생성
        PageRequest pageRequest = PageRequest.of(page, size);

        // 서비스 로직을 통해 페이지 갯수 가져오기
        Integer pageCnt = contractService.totalCount(pageRequest);

        // 결과를 담을 Map 생성
        Map<String, Object> resultMap = new HashMap<>();

        // 결과 데이터와 추가 정보를 resultMap에 넣음
        resultMap.put("totalPages", pageCnt);  // 페이지 수
        resultMap.put("currentPage", page);    // 현재 페이지
        resultMap.put("size", size);           // 페이지 크기

        // 성공적인 응답 반환
        return ResponseEntity.ok(resultMap);
    }
}
