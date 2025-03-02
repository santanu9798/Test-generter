package com.santanu.Test.generate.controller;

import com.santanu.Test.generate.dto.QuestionDTO;
import com.santanu.Test.generate.service.QuestionBankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@Tag(name = "Question Management")
@Validated
public class QuestionBankController {

    private final QuestionBankService questionBankService;

    public QuestionBankController(QuestionBankService questionBankService) {
        this.questionBankService = questionBankService;
    }

    @Operation(summary = "Add a New Question", description = "Creates a new question for a test paper")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid question data")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        questionBankService.createQuestion(questionDTO);
        return ResponseEntity.ok("{\"message\": \"Question added successfully\"}");
    }

    @Operation(summary = "Get All Questions", description = "Retrieves a list of all questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Questions retrieved successfully")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionBankService.getAllQuestion();
        return ResponseEntity.ok(questions);
    }
}