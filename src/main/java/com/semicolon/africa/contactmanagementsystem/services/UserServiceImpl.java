package com.semicolon.africa.contactmanagementsystem.services;

import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import com.semicolon.africa.contactmanagementsystem.data.model.User;
import com.semicolon.africa.contactmanagementsystem.data.repositories.ContactRepository;
import com.semicolon.africa.contactmanagementsystem.data.repositories.UserRepository;
import com.semicolon.africa.contactmanagementsystem.dtos.request.CreateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.LoginRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.LogoutRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.RegisterUserRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.response.CreateContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.LoginResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.LogoutResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.RegisterUserResponse;
import com.semicolon.africa.contactmanagementsystem.exceptions.EmailExistsException;
import com.semicolon.africa.contactmanagementsystem.exceptions.IncorrectPasswordException;
import com.semicolon.africa.contactmanagementsystem.exceptions.UserLoginException;
import com.semicolon.africa.contactmanagementsystem.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.semicolon.africa.contactmanagementsystem.utils.MapUtils.mapUserLogin;
import static com.semicolon.africa.contactmanagementsystem.utils.MapUtils.registerUserMapper;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private ContactRepository contactRepository;
    private ContactService contactService;

    @Override
    public RegisterUserResponse registerUserWith(RegisterUserRequest request) {
        validateExistingEmail(request.getEmail());
        User user = new User();
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setEmail(request.getEmail());
//        user.setPassword(request.getPassword());
        userRepository.save(registerUserMapper(user,request));
        RegisterUserResponse response = new RegisterUserResponse();
        response.setEmail(user.getEmail());
        response.setMessage(user.getEmail() + " registered successfully");
        return response;
    }

    private void validateExistingEmail(String email) {
        boolean existsByEmail = userRepository.existsByEmail(email);
        if (existsByEmail)throw new EmailExistsException(email+" already exists");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = findByEmail(request.getEmail());
        validatePassword(user, request.getPassword());
        user.setLoggedIn(true);
        userRepository.save(user);
        return mapUserLogin(user);
    }

    @Override
    public CreateContactResponse createContact(CreateContactRequest request) {
        User user = findByEmail(request.getOwnerEmail());
        validateUserLogin(user);
        CreateContactResponse response = contactService.createContactWith(request);
        Contact contact = contactService.findContactByPhoneNumber(response.getPhoneNumber());
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        user.setContacts(contacts);
        userRepository.save(user);
        return response;
    }

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        User user = findByEmail(request.getEmail());
        user.setLoggedIn(false);
        userRepository.save(user);
        LogoutResponse response = new LogoutResponse();
        response.setMessage("user logged out successfully");
        response.setLoggedIn(user.isLoggedIn());
        return response;
    }

    private void validateUserLogin(User user) {
        if(!user.isLoggedIn()) throw new UserLoginException("you need to login");

    }

    private void validatePassword(User user, String password) {
            if (!user.getPassword().equals(password)) throw new IncorrectPasswordException("invalid details");

    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));

    }
}
