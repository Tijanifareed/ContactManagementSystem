package com.semicolon.africa.contactmanagementsystem.services;

import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import com.semicolon.africa.contactmanagementsystem.data.repositories.ContactRepository;
import com.semicolon.africa.contactmanagementsystem.dtos.request.CreateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.DeleteContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.UpdateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.response.CreateContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.DeleteContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.UpdateContactResponse;
import com.semicolon.africa.contactmanagementsystem.exceptions.ContactNotFoundException;
import com.semicolon.africa.contactmanagementsystem.exceptions.PhoneNumberExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import static com.semicolon.africa.contactmanagementsystem.utils.MapUtils.mapContactUpdateResponse;
import static com.semicolon.africa.contactmanagementsystem.utils.MapUtils.contactRequestMapper;



@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Override
    public CreateContactResponse createContactWith(CreateContactRequest request) {
        validatePhoneNumber(request.getPhoneNumber());
        Contact contact = new Contact();
//        contact.setFirstName(request.getFirstName());
//        contact.setLastName(request.getLastName());
//        contact.setEmail(request.getEmail());
//        contact.setPhoneNumber(request.getPhoneNumber());
        contactRepository.save(contactRequestMapper(contact,request));
        CreateContactResponse response = new CreateContactResponse();
        response.setPhoneNumber(contact.getPhoneNumber());
        response.setFirstName(contact.getFirstName());
        response.setLastName(contact.getLastName());
        response.setMessage("Contact created successfully");
        response.setId(contact.getId());
        return response;
    }

    private void validatePhoneNumber(String phoneNumber) {
        boolean existsByPhoneNumber = contactRepository.existsByPhoneNumber(phoneNumber);
        if(existsByPhoneNumber) throw new PhoneNumberExistsException(phoneNumber+"exists");

    }


    @Override
    public Long getTotalContacts() {
        return contactRepository.count();
    }


    @Override
    public DeleteContactResponse deleteContact(DeleteContactRequest request) {
        Contact contact = findById(request.getContactId());
        contactRepository.delete(contact);
        DeleteContactResponse response = new DeleteContactResponse();
        response.setMessage("contact deleted successfully");
        return response;
    }

    @Override
    public UpdateContactResponse updateContactWith(UpdateContactRequest request) {
        Contact contact = findById(request.getId());
        contact.setFirstName(request.getUpdatedFirstName());
        contact.setLastName(request.getUpdatedLastName());
        contact.setPhoneNumber(request.getUpdatedPhoneNumber());
        contact.setEmail(request.getUpdatedEmail());
        contact.setAddress(request.getUpdatedAddress());
        contact.setId(request.getId());
        contact = contactRepository.save(contact);
        return mapContactUpdateResponse(contact);

    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public List<Contact> findContactByName(String name) {
        List<Contact> contacts = new ArrayList<>();
        List<Contact> contact = contactRepository.findAll();
        for(Contact elements : contact){
            if(elements.getFirstName().equals(name) || elements.getLastName().equals(name)){
                contacts.add(elements);
            }
        }
        return contacts;
    }

    @Override
    public Contact findContactByPhoneNumber(String phoneNumber) {
        List<Contact> contacts = contactRepository.findAll();

        for(Contact elements : contacts){
            if(elements.getPhoneNumber().equals(phoneNumber)) contacts.add(elements);
            return elements;
        }throw new ContactNotFoundException("Contact not found");

    }

    private Contact findById(String contactId) {
        return contactRepository.findById(contactId).orElseThrow(() -> new ContactNotFoundException("contact not found "));
    }



}
