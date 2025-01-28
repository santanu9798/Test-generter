package com.santanu.Test.generate.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/users/")
@Tag(name = "User Management")
public class UserController {

    @PostMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/login")
    public String login() {
        return "login";
    }
}