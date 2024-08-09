package com.semicolon.africa.contactmanagementsystem.data.repositories;

import com.semicolon.africa.contactmanagementsystem.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}
