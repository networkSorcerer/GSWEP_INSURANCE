package com.gswep.insurance.field_answers.controller;

import com.gswep.insurance.field_answers.dto.FieldAnswersRequestDTO;
import com.gswep.insurance.field_answers.dto.FieldAnswersResponseDTO;
import com.gswep.insurance.field_answers.service.FieldAnswersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class FieldsAnswersController {
    private final FieldAnswersService fieldAnswersService;

    @PostMapping("/addAnswers")
    public ResponseEntity<Boolean> addAnswers(@RequestBody FieldAnswersRequestDTO fieldAnswersRequestDTO){
        boolean isSuccess = fieldAnswersService.addAnswers(fieldAnswersRequestDTO);
        return ResponseEntity.ok(isSuccess);
    }

    @PostMapping("/update_answers")
    public ResponseEntity<Boolean> update_answers(@RequestBody FieldAnswersRequestDTO fieldAnswersRequestDTO){
        boolean isSuccess = fieldAnswersService.updateAnswers(fieldAnswersRequestDTO);
        return ResponseEntity.ok(isSuccess);
    }

    @PostMapping("/delete_answers")
    public ResponseEntity<Boolean> delete_answers(@RequestParam(name = "answer_id") Integer answerId){
        boolean isSuccess = fieldAnswersService.deleteAnswers(answerId);
        return ResponseEntity.ok(isSuccess);
    }

    @GetMapping("read_answers")
    public ResponseEntity<FieldAnswersResponseDTO> read_answers (@RequestParam(name="form_field_id") Integer formFiledId){
        FieldAnswersResponseDTO answers = fieldAnswersService.readAnswers(formFiledId);
        return ResponseEntity.ok(answers);
    }

}
