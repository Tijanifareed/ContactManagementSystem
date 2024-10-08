package com.semicolon.africa.contactmanagementsystem.data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Document
@ToString
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isLoggedIn;
    private String password;
    @DBRef
    private List<Contact> contacts = new ArrayList<>();

}
