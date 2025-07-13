package com.gswep.insurance.from_fields.controller;

import com.gswep.insurance.from_fields.dto.FormFieldsRequestDTO;
import com.gswep.insurance.from_fields.dto.FormFieldsResponseDTO;
import com.gswep.insurance.from_fields.service.FormFieldsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/form_fields")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class FormFieldsController {
    private final FormFieldsService formFieldsService;

    @GetMapping("/get_fields")
    public ResponseEntity<List<FormFieldsResponseDTO>> get_fields (@RequestParam(name = "formId") Integer formId){
        List<FormFieldsResponseDTO> getFields = formFieldsService.getFields(formId);
        return ResponseEntity.ok(getFields);
    }

    @PostMapping("/add_fields")
    public ResponseEntity<Boolean> add_fields (@RequestBody FormFieldsRequestDTO formFieldsRequestDTO){
        boolean isSuccess = formFieldsService.addFields(formFieldsRequestDTO);
        return ResponseEntity.ok(isSuccess);
    }

    @PostMapping("/update_fields")
    public ResponseEntity<Boolean> update_fields(@RequestBody FormFieldsRequestDTO formFieldsRequestDTO){
        boolean isSuccess = formFieldsService.updateFields(formFieldsRequestDTO);
        return ResponseEntity.ok(isSuccess);
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> delete_fields(@RequestParam(name = "fieldsId") Integer fieldsId ){
        boolean isSuccess = formFieldsService.deleteFields(fieldsId);
        return ResponseEntity.ok(isSuccess);
    }
}
