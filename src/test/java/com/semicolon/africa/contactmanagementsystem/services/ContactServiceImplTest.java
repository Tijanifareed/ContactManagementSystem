package com.semicolon.africa.contactmanagementsystem.services;

import com.semicolon.africa.contactmanagementsystem.data.repositories.ContactRepository;
import com.semicolon.africa.contactmanagementsystem.dtos.request.CreateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.UpdateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.response.CreateContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.DeleteContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.UpdateContactResponse;
import com.semicolon.africa.contactmanagementsystem.exceptions.PhoneNumberExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ContactServiceImplTest {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactRepository contactRepository;

    @BeforeEach
    public void setUp(){
        contactRepository.deleteAll();
    }

    @Test
    public void testCreateContact() {
        CreateContactResponse response = createNewContact();
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(contactService.getTotalContacts().intValue()).isEqualTo(1);
    }

    private CreateContactResponse createNewContact() {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("freddie");
        request.setLastName("Teejay");
        request.setEmail("tij@gmail.com");
        request.setPhoneNumber("08084562163");
        request.setAddress("Aradagun,badagry");
        return contactService.createContactWith(request);
    }

    @Test
    public void testDeleteContact() {
        CreateContactResponse response = createNewContact();
        String contactId = response.getId();
        DeleteContactResponse response1 = contactService.deleteContact(contactId);
        assertThat(response1.getMessage()).contains("deleted");
    }

    @Test
    public void createNoteWithSameNumber_throwsException() {
        CreateContactResponse response = createNewContact();
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("fareed");
        request.setLastName("Tijani");
        request.setEmail("fareedTijani@gmail.com");
        request.setPhoneNumber("08084562163");
        request.setAddress("Aradagun,badagry");
        assertThrows(PhoneNumberExistsException.class, ()->contactService.createContactWith(request));
    }


    @Test
    public void testThatCanEditContact() {
        CreateContactResponse response = createNewContact();
        UpdateContactRequest request = new UpdateContactRequest();
        request.setUpdatedFirstName("freddies");
        request.setUpdatedLastName("Teejay");
    request.setUpdatedEmail("tij@gmail.com");
        request.setUpdatedPhoneNumber("08084562163");
        request.setUpdatedAddress("Aradagun,badagry");
        UpdateContactResponse response1 = contactService.updateContactWith(request);
        assertThat(response1.getUpdatedFirstName()).contains("freddies");
    }



}