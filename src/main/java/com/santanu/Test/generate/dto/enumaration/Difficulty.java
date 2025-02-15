package com.santanu.Test.generate.dto.enumaration;

import lombok.Getter;

@Getter
public enum Difficulty {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String value;

    Difficulty(String value) {
        this.value = value;
    }

}