package com.semicolon.africa.contactmanagementsystem.services;

import com.semicolon.africa.contactmanagementsystem.data.model.User;
import com.semicolon.africa.contactmanagementsystem.dtos.request.CreateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.LoginRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.LogoutRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.RegisterUserRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.response.CreateContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.LoginResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.LogoutResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.RegisterUserResponse;

import java.util.Collection;
import java.util.List;

public interface UserService {
    RegisterUserResponse registerUserWith(RegisterUserRequest request);

    List<User> getAllUsers();

    LoginResponse login(LoginRequest request);

    CreateContactResponse createContact(CreateContactRequest request);

    LogoutResponse logout(LogoutRequest request);
}
