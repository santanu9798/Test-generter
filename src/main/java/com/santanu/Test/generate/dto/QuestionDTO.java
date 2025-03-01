package com.santanu.Test.generate.dto;

import com.santanu.Test.generate.dto.enumaration.Difficulty;
import com.santanu.Test.generate.dto.enumaration.QuestionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionDTO {

    @NotBlank(message = "Question text is required")
    private String question;

    private QuestionType type;

    @NotBlank(message = "Topic is required")
    private String topic;

    private Difficulty difficulty;

    @Size(min = 1, message = "At least one option is required")
    private List<String> options;

    @NotBlank(message = "Correct answer is required")
    private String correctAnswer;

    @Min(value = 1, message = "Marks must be at least 1")
    private int marks;
}
