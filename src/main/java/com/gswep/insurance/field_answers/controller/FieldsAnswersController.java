package com.gswep.insurance.field_answers.controller;

import com.gswep.insurance.field_answers.dto.FieldAnswersRequestDTO;
import com.gswep.insurance.field_answers.service.FieldAnswersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class FieldsAnswersController {
    private final FieldAnswersService fieldAnswersService;

    public ResponseEntity<Boolean> addAnswers(@RequestBody FieldAnswersRequestDTO fieldAnswersRequestDTO){
        boolean isSuccess = fieldAnswersService.addAnswers(fieldAnswersRequestDTO);
        return ResponseEntity.ok(isSuccess);
    }
}
