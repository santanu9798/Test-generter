package com.santanu.Test.generate.dto;

import com.santanu.Test.generate.dto.enumaration.Difficulty;
import com.santanu.Test.generate.dto.enumaration.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionDTO {

    @NotBlank
    private String text;

    @NotBlank
    private QuestionType type;

    @NotBlank
    private String topic;

    @NotBlank
    private Difficulty difficulty;

    @Size(min = 1)
    private List<String> options;

    @NotBlank
    private String correctAnswer;

    @NotBlank
    private int marks;
}
