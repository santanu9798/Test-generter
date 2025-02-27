package com.santanu.Test.generate.service;

public interface UserService {
     String login(String email, String password);
     void register(String fullName, String email, String password);
     void forgotPassword(String email);
     void resetPassword(String resetToken, String newPassword);
}
