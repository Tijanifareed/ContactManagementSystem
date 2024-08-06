package com.semicolon.africa.contactmanagementsystem.dtos.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateContactRequest{
    private String id;
    private String updatedFirstName;
    private String updatedLastName;
    private String updatedPhoneNumber;
    private String updatedEmail;
    private String updatedAddress;
}
