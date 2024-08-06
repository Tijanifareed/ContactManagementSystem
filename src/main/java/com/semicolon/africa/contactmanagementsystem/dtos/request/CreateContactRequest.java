package com.semicolon.africa.contactmanagementsystem.dtos.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class CreateContactRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String id;


}
