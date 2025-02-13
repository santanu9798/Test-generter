package com.santanu.Test.generate.model;


import com.santanu.Test.generate.dto.enumaration.Difficulty;
import com.santanu.Test.generate.dto.enumaration.QuestionType;
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
    private QuestionType type;

    private String topic;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private String options;

    private String correctAnswer;

    private int marks;

}
