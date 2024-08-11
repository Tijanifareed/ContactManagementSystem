package com.semicolon.africa.contactmanagementsystem.dtos.response;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateContactResponse {

    private String id;
//    private String firstName;
//    private String lastName;
    private String phoneNumber;
    private String message;
}
