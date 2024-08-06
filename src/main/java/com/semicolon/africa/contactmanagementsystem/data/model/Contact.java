package com.semicolon.africa.contactmanagementsystem.data.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;

}
