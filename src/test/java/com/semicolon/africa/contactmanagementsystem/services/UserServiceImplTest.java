package com.semicolon.africa.contactmanagementsystem.services;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        return userService.login(request);
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
        assertThat(response1.getId()).isNotNull();

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
}