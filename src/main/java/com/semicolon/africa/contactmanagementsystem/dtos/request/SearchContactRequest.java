package com.semicolon.africa.contactmanagementsystem.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchContactRequest {
    private String name;
    private String phoneNumber;
    private String ownerEmail;
}
