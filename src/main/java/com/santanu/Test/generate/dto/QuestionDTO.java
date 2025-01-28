package com.santanu.Test.generate.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {

    private Long id;
    private String text;
    private QuestionType type;
    private String topic;
    private Difficulty difficulty;
    private List<String> options;
    private String correctAnswer;
    private int marks;

    public enum QuestionType {
        MCQ, DESCRIPTIVE, TRUE_FALSE
    }

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
}
