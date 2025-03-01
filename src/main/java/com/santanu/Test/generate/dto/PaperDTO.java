package com.santanu.Test.generate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaperDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String courseName;

    @NotBlank
    private String courseCode;

    private String description;

    private int totalMarks;

    private int totalQuestions;

    private int credit;

    private int totalTime;

    @Size(min = 1)
    private List<DistributionDTO> distributions;

}
