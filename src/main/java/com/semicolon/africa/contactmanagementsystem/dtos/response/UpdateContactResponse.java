package com.semicolon.africa.contactmanagementsystem.dtos.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateContactResponse {
//    private String id;
    private String updatedFirstName;
    private String updatedLastName;
    private String updatedPhoneNumber;
    private String updatedEmail;
    private String updatedAddress;
}
