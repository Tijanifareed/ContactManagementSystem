package com.semicolon.africa.contactmanagementsystem.controllers;


import com.semicolon.africa.contactmanagementsystem.dtos.request.CreateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.DeleteContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.LoginRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.RegisterUserRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.response.*;
import com.semicolon.africa.contactmanagementsystem.exceptions.MyContactsException;
import com.semicolon.africa.contactmanagementsystem.services.UserService;
import com.semicolon.africa.contactmanagementsystem.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@Controller
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest request) {
        try{
            RegisterUserResponse registerUserResponse = userService.registerUserWith(request);
            return new ResponseEntity<>(new ApiResponse(true,registerUserResponse), CREATED);
        }catch (MyContactsException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest request) {
        try{
            LoginResponse response = userService.login(request);
            return new ResponseEntity<>(new ApiResponse(true,response), CREATED);
        }catch (MyContactsException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/create-contact")
    public ResponseEntity<?> createContact(@RequestBody CreateContactRequest request) {
        try{
            CreateContactResponse response = userService.createContact(request);
            return new ResponseEntity<>(new ApiResponse(true,response), CREATED);
        }catch (MyContactsException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-contact")
    public ResponseEntity<?> deleteContact(@RequestBody DeleteContactRequest request) {
        try{
            DeleteContactResponse response = userService.deleteContact(request);
            return new ResponseEntity<>(new ApiResponse(true,response), CREATED);
        }
        catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), BAD_REQUEST);
        }
    }


}
