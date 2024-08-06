package com.semicolon.africa.contactmanagementsystem.data.repositories;

import com.semicolon.africa.contactmanagementsystem.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
