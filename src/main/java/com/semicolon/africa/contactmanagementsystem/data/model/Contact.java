package com.semicolon.africa.contactmanagementsystem.data.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@ToString
public class Contact {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;

}
