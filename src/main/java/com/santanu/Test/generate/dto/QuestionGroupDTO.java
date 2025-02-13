package com.santanu.Test.generate.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionGroupDTO {
    private String type;
    private List<QuestionDTO> questions;
}
