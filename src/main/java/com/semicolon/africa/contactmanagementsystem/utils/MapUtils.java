package com.semicolon.africa.contactmanagementsystem.utils;

import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import com.semicolon.africa.contactmanagementsystem.dtos.response.UpdateContactResponse;

public class MapUtils {
    public static UpdateContactResponse mapContactUpdateResponse(Contact contact) {
        UpdateContactResponse response = new UpdateContactResponse();
        response.setUpdatedFirstName(contact.getFirstName());
        response.setUpdatedLastName(contact.getLastName());
        response.setUpdatedEmail(contact.getEmail());
        response.setUpdatedPhoneNumber(contact.getPhoneNumber());
        response.setUpdatedAddress(contact.getAddress());
        return response;
    }


}
