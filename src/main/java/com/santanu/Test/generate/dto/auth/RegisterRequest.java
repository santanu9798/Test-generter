package com.santanu.Test.generate.dto.auth;

import lombok.Data;
import lombok.Getter;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
}