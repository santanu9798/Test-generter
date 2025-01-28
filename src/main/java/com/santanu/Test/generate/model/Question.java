package com.santanu.Test.generate.model;


import com.santanu.Test.generate.dto.QuestionDTO;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Enumerated(EnumType.STRING)
    private QuestionDTO.QuestionType type;

    private String topic;

    @Enumerated(EnumType.STRING)
    private QuestionDTO.Difficulty difficulty;

    private String options;

    private String correctAnswer;

    private int marks;

}
