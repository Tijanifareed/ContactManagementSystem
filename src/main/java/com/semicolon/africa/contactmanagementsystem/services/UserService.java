package com.semicolon.africa.contactmanagementsystem.services;

import com.semicolon.africa.contactmanagementsystem.dtos.request.RegisterUserRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.response.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse registerUserWith(RegisterUserRequest request);
}
