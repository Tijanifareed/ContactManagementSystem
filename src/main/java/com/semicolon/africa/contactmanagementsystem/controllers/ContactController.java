package com.semicolon.africa.contactmanagementsystem.controllers;

import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import com.semicolon.africa.contactmanagementsystem.dtos.request.CreateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.DeleteContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.UpdateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.response.ApiResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.CreateContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.DeleteContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.UpdateContactResponse;
import com.semicolon.africa.contactmanagementsystem.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("/api/v1/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping("/create-contact")
    public ResponseEntity<?> addContact(@RequestBody CreateContactRequest createContactRequest) {
        try{
            CreateContactResponse createContactResponse = contactService.createContactWith(createContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, createContactResponse), CREATED);
        }
        catch(Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.fillInStackTrace()), BAD_REQUEST);
        }

    }




    @PatchMapping("/update-contact")
    public ResponseEntity<?> updateContact(@RequestBody UpdateContactRequest updateContactRequest) {
        try{
            UpdateContactResponse updateContactResponse = contactService.updateContactWith(updateContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, updateContactResponse), OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception), BAD_REQUEST);
        }
    }



    @DeleteMapping("/delete-contact")
    public ResponseEntity<?> deleteContact(@RequestBody DeleteContactRequest deleteContactRequest) {
        try{
            DeleteContactResponse deleteContactResponse = contactService.deleteContact(deleteContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, deleteContactResponse), OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(new ApiResponse(false, exception), BAD_REQUEST);
        }
    }

    @GetMapping("/find-all-contact")
    public ResponseEntity<List<Contact>> findAllContact() {
        List<Contact> contact = contactService.getAllContacts();
        return ResponseEntity.ok(contact);
    }


}
