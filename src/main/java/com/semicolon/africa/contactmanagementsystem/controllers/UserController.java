package com.semicolon.africa.contactmanagementsystem.controllers;


import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import com.semicolon.africa.contactmanagementsystem.dtos.request.*;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@CrossOrigin(origins = "*")
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) {
        try {
            LogoutResponse response = userService.logout(request);
            return new ResponseEntity<>(new ApiResponse(true,response), CREATED);
        }
        catch (Exception exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/find-all-contact")
    public ResponseEntity<?> findAllContact() {
        List<Contact> contact = userService.getAllContacts();
        return ResponseEntity.ok(contact);
    }

    @GetMapping("/find-contact-byName")
    public ResponseEntity<?> findContactByName(@RequestBody SearchContactRequest request) {
        List<Contact> contact = userService.findContactByName(request.getName());
        return ResponseEntity.ok(contact);
    }

    @GetMapping("/find-contact-byPhoneNumber")
    public ResponseEntity<?> findContactByPhoneNumber(@RequestBody SearchContactRequest request) {
        Contact contact = userService.findContactByPhoneNumber(request.getPhoneNumber());
        return ResponseEntity.ok(contact);
    }

    @PatchMapping("/update-contact")
    public ResponseEntity<?> updateContact(@RequestBody UpdateContactRequest updateContactRequest) {
        try{
            UpdateContactResponse updateContactResponse = userService.updateContactWith(updateContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, updateContactResponse), OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception), BAD_REQUEST);
        }
    }



}
