package com.santanu.Test.generate.dto;

import com.santanu.Test.generate.dto.enumaration.Difficulty;
import com.santanu.Test.generate.dto.enumaration.QuestionType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionDTO {

    private Long id;
    private String text;
    private QuestionType type;
    private String topic;
    private Difficulty difficulty;
    private List<String> options;
    private String correctAnswer;
    private int marks;
}
