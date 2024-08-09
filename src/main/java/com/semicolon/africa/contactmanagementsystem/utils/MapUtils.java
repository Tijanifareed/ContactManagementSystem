package com.semicolon.africa.contactmanagementsystem.utils;

import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import com.semicolon.africa.contactmanagementsystem.data.model.User;
import com.semicolon.africa.contactmanagementsystem.dtos.request.CreateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.RegisterUserRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.response.LoginResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.UpdateContactResponse;

public class MapUtils {
    public static UpdateContactResponse mapContactUpdateResponse(Contact contact) {
        UpdateContactResponse response = new UpdateContactResponse();
        response.setUpdatedFirstName(contact.getFirstName());
        response.setUpdatedLastName(contact.getLastName());
//        response.setUpdatedEmail(contact.getEmail());
//        response.setUpdatedAddress(contact.getAddress());
        response.setUpdatedPhoneNumber(contact.getPhoneNumber());
        return response;
    }

    public static Contact contactRequestMapper(Contact contact, CreateContactRequest request){
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setPhoneNumber(request.getPhoneNumber());
        contact.setEmail(request.getEmail());
        contact.setAddress(request.getAddress());
        contact.setId(request.getId());
        return contact;

    }

    public static User registerUserMapper(User user, RegisterUserRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public static LoginResponse mapUserLogin(User user){
        LoginResponse response = new LoginResponse();
        response.setEmail(user.getEmail());
        response.setMessage(user.getEmail()+"Logged in successfully");
        response.setLoggedIn(user.isLoggedIn());
        return response;
    }


}
