package com.semicolon.africa.contactmanagementsystem.data.repositories;

import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;



public interface ContactRepository extends MongoRepository<Contact, String> {
    boolean existsByPhoneNumber(String phoneNumber);

}
