package com.semicolon.africa.contactmanagementsystem.services;

import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import com.semicolon.africa.contactmanagementsystem.dtos.request.CreateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.DeleteContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.UpdateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.response.CreateContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.DeleteContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.UpdateContactResponse;

import java.util.List;

public interface ContactService {
    CreateContactResponse createContactWith(CreateContactRequest request);
    Long getTotalContacts();
    DeleteContactResponse deleteContact(DeleteContactRequest request);

    UpdateContactResponse updateContactWith(UpdateContactRequest request);

    List<Contact> getAllContacts();

    List<Contact> findContactByName(String name);
    Contact findContactByPhoneNumber(String email);
}
