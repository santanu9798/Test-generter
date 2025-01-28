package com.santanu.Test.generate.controller;

import com.santanu.Test.generate.dto.QuestionDTO;
import com.santanu.Test.generate.service.QuestionBankService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
@Tag(name = "Question Management")
@Validated
public class QuestionBankController {

    private final QuestionBankService questionBankService;

    public QuestionBankController(QuestionBankService questionBankService) {
        this.questionBankService = questionBankService;
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> addQuestion(@Valid @RequestBody QuestionDTO questionDTO) {

        QuestionDTO response = questionBankService.createQuestion(questionDTO);

        return ResponseEntity.ok()
                .header("Custom-Header", "Value")
                .body(response);
    }
}