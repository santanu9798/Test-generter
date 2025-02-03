package com.santanu.Test.generate.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String courseName;
    private String courseCode;
    private String description;
    private int totalMarks;
    private int totalQuestions;
    private int credit;
    private int totalTime;

    @OneToMany(mappedBy = "paper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Distribution> distributions;

    private String createdBy;
    private Date createdAt;
    private String updateBy;
    private Date updateAt;
}
