package com.santanu.Test.generate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Distribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private int count;
    private int marksPerQuestion;

    @ManyToOne
    @JoinColumn(name = "paper_id", referencedColumnName = "id")
    @JsonBackReference
    private Paper paper;
}
