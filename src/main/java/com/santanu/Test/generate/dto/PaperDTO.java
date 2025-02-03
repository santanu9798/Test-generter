package com.santanu.Test.generate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaperDTO {
    private String name;
    private String courseName;
    private String courseCode;
    private String description;
    private int totalMarks;
    private int totalQuestions;
    private int credit;
    private int totalTime;

    private List<DistributionDTO> distributions;

}
