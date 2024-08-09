package com.semicolon.africa.contactmanagementsystem.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponse {
    private boolean isSucessfull;
    private Object data;
}
