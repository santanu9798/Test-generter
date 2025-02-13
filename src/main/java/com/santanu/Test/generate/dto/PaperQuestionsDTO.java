package com.santanu.Test.generate.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaperQuestionsDTO {
    private Long paperId;
    private String paperName;
    private List<QuestionGroupDTO> groupedQuestions;
}
