package com.semicolon.africa.contactmanagementsystem.dtos.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String email;
    private String message;
    private boolean isLoggedIn;
}
