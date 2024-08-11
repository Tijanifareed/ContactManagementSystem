package com.semicolon.africa.contactmanagementsystem.services;

import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import com.semicolon.africa.contactmanagementsystem.data.model.User;
import com.semicolon.africa.contactmanagementsystem.dtos.request.*;
import com.semicolon.africa.contactmanagementsystem.dtos.response.*;

import java.util.List;

public interface UserService {
    RegisterUserResponse registerUserWith(RegisterUserRequest request);

    List<User> getAllUsers();

    LoginResponse login(LoginRequest request);

    CreateContactResponse createContact(CreateContactRequest request);

    LogoutResponse logout(LogoutRequest request);

    DeleteContactResponse deleteContact(DeleteContactRequest request);

    UpdateContactResponse updateContacts(UpdateContactRequest request2);

    List<Contact> getAllContacts();

    List<Contact> findContactByName(String fareed);

}
