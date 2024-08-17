package com.semicolon.africa.contactmanagementsystem.services;

import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import com.semicolon.africa.contactmanagementsystem.data.repositories.ContactRepository;
import com.semicolon.africa.contactmanagementsystem.data.repositories.UserRepository;
import com.semicolon.africa.contactmanagementsystem.dtos.request.*;
import com.semicolon.africa.contactmanagementsystem.dtos.response.*;
import com.semicolon.africa.contactmanagementsystem.exceptions.EmailExistsException;
import com.semicolon.africa.contactmanagementsystem.exceptions.IncorrectPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    UserRepository userRepository;
    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Autowired
    private UserService userService;

    @Test
    public void testRegisterUser() {
        RegisterUserResponse response = createNewUser();
        assertThat(response).isNotNull();
        assertThat(userService.getAllUsers().size()).isEqualTo(1);
    }

    private RegisterUserResponse createNewUser() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("first name");
        request.setLastName("last name");
        request.setEmail("email@email.com");
        request.setPassword("password");
        return userService.registerUserWith(request);
    }

    @Test
    public void RegisterUserWithSameEmail_throwsExceptionTest() {
        createNewUser();
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("freddie");
        request.setLastName("tee");
        request.setEmail("email@email.com");
        request.setPassword("password");
        assertThrows(EmailExistsException.class, ()-> userService.registerUserWith(request));
    }

    @Test
    public void loginTest(){
        createNewUser();
        LoginResponse response = userLogin();
        assertThat(response.isLoggedIn()).isEqualTo(true);
    }

    private LoginResponse userLogin() {
        LoginRequest request = new LoginRequest();
        request.setEmail("email@email.com");
        request.setPassword("password");
        return userService.login(requests);
    }

    @Test
    public void testThatLoginWithIncorrectPassword_throwsExceptionTest(){
        createNewUser();
        LoginRequest request = new LoginRequest();
        request.setEmail("email@email.com");
        request.setPassword("wrong password");
        assertThrows(IncorrectPasswordException.class, ()-> userService.login(request));
    }

    @Test
    public void createContactTest_successfulTest(){
        createNewUser();
        LoginResponse response = userLogin();
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Fareed");
        request.setLastName("Tijani");
        request.setEmail("fareedtijani2810@gmail.com");
        request.setPhoneNumber("08084562163");
        request.setAddress("buhari estate");
        request.setOwnerEmail("email@email.com");
        CreateContactResponse response1 = userService.createContact(request);
        assertThat(userService.getAllContacts().size()).isEqualTo(1);

    }

    @Test
    public void logoutTest(){
        createNewUser();
        userLogin();
        LogoutRequest request = new LogoutRequest();
        request.setEmail("email@email.com");
        LogoutResponse response = userService.logout(request);
        assertThat(response.getMessage()).contains("logged out");
    }

    @Test
    public void testThatUserCanDeleteContact(){
        createNewUser();
        LoginResponse response1 = userLogin();
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Fareed");
        request.setLastName("Tijani");
        request.setEmail("fareedtijani2810@gmail.com");
        request.setPhoneNumber("08084562163");
        request.setAddress("buhari estate");
        request.setOwnerEmail("email@email.com");
        CreateContactResponse response2 = userService.createContact(request);
        DeleteContactRequest request2 = new DeleteContactRequest();
        request2.setPhoneNumber(request2.getPhoneNumber());
        DeleteContactResponse response3 = userService.deleteContact(request2);
        assertThat(response3.getMessage()).contains("deleted");

    }

    @Test
    public void testThatUserCan_editContact(){
        createNewUser();
        LoginResponse response1 = userLogin();
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Fareed");
        request.setLastName("Tijani");
        request.setEmail("fareedtijani2810@gmail.com");
        request.setPhoneNumber("08084562163");
        request.setAddress("buhari estate");
        request.setOwnerEmail("email@email.com");
        CreateContactResponse response2 = userService.createContact(request);
        UpdateContactRequest request2 = new UpdateContactRequest();
//        request2.setId(response2.getId());
        request2.setUpdatedPhoneNumber("2222");
        request2.setUpdatedEmail("email@email.coms");
        request2.setUpdatedAddress("buhari estates");
        request2.setUpdatedLastName("Tinubu");
        request2.setUpdatedFirstName("Jagaban");
        UpdateContactResponse response = userService.updateContacts(request2);
        assertThat(response.getUpdatedFirstName()).contains("Jagaban");

    }

    @Test
    public void testThatUserCan_getAllContact(){
        createNewUser();
        LoginResponse response1 = userLogin();
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Fareed");
        request.setLastName("Tijani");
        request.setEmail("fareedtijani2810@gmail.com");
        request.setPhoneNumber("08084562163");
        request.setAddress("buhari estate");
        request.setOwnerEmail("email@email.com");
        CreateContactResponse response2 = userService.createContact(request);
        List<Contact> contacts = userService.getAllContacts();
        assertThat(contacts.size()).isEqualTo(1);
    }

    @Test
    public void testThatUserCan_getContactByName(){
        createNewUser();
        LoginResponse response1 = userLogin();
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Fareed");
        request.setLastName("Tijani");
        request.setEmail("fareedtijani2810@gmail.com");
        request.setPhoneNumber("08084562163");
        request.setAddress("buhari estate");
        request.setOwnerEmail("email@email.com");
        CreateContactResponse response2 = userService.createContact(request);
        CreateContactRequest request2 = new CreateContactRequest();
        request2.setFirstName("Fareed");
        request2.setLastName("Tijani");
        request2.setEmail("fareedtijani2810@gmail.cum");
        request2.setPhoneNumber("09084562163");
        request2.setAddress("buhari estates");
        request2.setOwnerEmail("email@email.com");
        CreateContactResponse response3 = userService.createContact(request2);
        List<Contact> contacts = userService.findContactByName("Fareed");
        assertThat(contacts.size()).isEqualTo(2);
    }

}