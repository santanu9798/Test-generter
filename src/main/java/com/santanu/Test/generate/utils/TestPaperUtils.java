package com.santanu.Test.generate.utils;

public class TestPaperUtils {

    public static char getOption(int index) {
        if (index < 0 || index >= 26) {
            throw new IllegalArgumentException("Index must be between 0 and 25.");
        }

        String alphabetString = "abcdefghijklmnopqrstuvwxyz";

        char[] alphabet = alphabetString.toCharArray();

        return alphabet[index];
    }
}
