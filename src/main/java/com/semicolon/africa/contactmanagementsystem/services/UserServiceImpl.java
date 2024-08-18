package com.semicolon.africa.contactmanagementsystem.services;

import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import com.semicolon.africa.contactmanagementsystem.data.model.User;
import com.semicolon.africa.contactmanagementsystem.data.repositories.ContactRepository;
import com.semicolon.africa.contactmanagementsystem.data.repositories.UserRepository;
import com.semicolon.africa.contactmanagementsystem.dtos.request.*;
import com.semicolon.africa.contactmanagementsystem.dtos.response.*;
import com.semicolon.africa.contactmanagementsystem.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.semicolon.africa.contactmanagementsystem.utils.MapUtils.*;


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
//        user.setPassword(request.getPassword())
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
        List<Contact> contacts = user.getContacts();
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

    @Override
    public DeleteContactResponse deleteContact(DeleteContactRequest request) {
        return contactService.deleteContact(request);
    }



    @Override
    public UpdateContactResponse updateContacts(UpdateContactRequest request2) {
        User user = findByEmail(request2.getOwnerEmail());
        List<Contact> contact = user.getContacts();
        for(Contact contact1: contact){
            if(contact1.getPhoneNumber().equals(request2.getPhoneNumber())){
                contact1.setFirstName(request2.getUpdatedFirstName());
                contact1.setLastName(request2.getUpdatedLastName());
                contact1.setPhoneNumber(request2.getUpdatedPhoneNumber());
                contact1.setEmail(request2.getUpdatedEmail());
                contact1.setAddress(request2.getUpdatedAddress());
                contact1.setId(request2.getId());
                contactRepository.save(contact1);
                return mapContactUpdateResponse(contact1);
            }
        }
        throw new ContactNotFoundException("contact not found");

    }

    @Override
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @Override
    public List<Contact> findContactByName(String name) {
        return contactService.findContactByName(name);
    }

    @Override
    public Contact findContactByPhoneNumber(String phoneNumber, String email) {
        User user = findByEmail(email);
        List<Contact> contacts = user.getContacts();
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                return contact;
            }
        }
        throw new ContactNotFoundException("Contact not found");
    }

    @Override
    public UpdateContactResponse updateContactWith(UpdateContactRequest updateContactRequest) {

        return contactService.updateContactWith(updateContactRequest);
    }

    private Contact findById(String contactId) {
        return contactRepository.findById(contactId).orElseThrow(() -> new ContactNotFoundException("contact not found "));
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
