package com.gswep.insurance.form.controller;

import com.gswep.insurance.form.dto.FormRequestDTO;
import com.gswep.insurance.form.dto.FormResponseDTO;
import com.gswep.insurance.form.service.FormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/form")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class FormController {

    private final FormService formService;

    @PostMapping("/add_form")
    public ResponseEntity<Boolean> add_form(@RequestBody FormRequestDTO formRequestDTO){
        boolean isSuccess =formService.addForm(formRequestDTO);
        return ResponseEntity.ok(isSuccess);
    }

    @GetMapping("/read_form")
    public ResponseEntity<List<FormResponseDTO>> read_form(@RequestParam String productCode){
        List<FormResponseDTO> formList = formService.readForm(productCode);
        return ResponseEntity.ok(formList);
    }

    @GetMapping("/find_product_code")
    public ResponseEntity<Map<String, Object>> find_product_code(@RequestParam(name = "contract_id") Integer contract_id){
        Map<String,Object> resultMap = new HashMap<>();
        String productCode = formService.getProductCode(contract_id);
        resultMap.put("productCode",productCode);
        return ResponseEntity.ok(resultMap);
    }
}
