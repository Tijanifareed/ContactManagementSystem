package com.semicolon.africa.contactmanagementsystem.services;

import com.semicolon.africa.contactmanagementsystem.data.model.Contact;
import com.semicolon.africa.contactmanagementsystem.data.repositories.ContactRepository;
import com.semicolon.africa.contactmanagementsystem.dtos.request.CreateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.request.UpdateContactRequest;
import com.semicolon.africa.contactmanagementsystem.dtos.response.CreateContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.DeleteContactResponse;
import com.semicolon.africa.contactmanagementsystem.dtos.response.UpdateContactResponse;
import com.semicolon.africa.contactmanagementsystem.exceptions.ContactNotFoundException;
import com.semicolon.africa.contactmanagementsystem.exceptions.PhoneNumberExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Override
    public CreateContactResponse createContactWith(CreateContactRequest request) {
        validatePhoneNumber(request.getPhoneNumber());
        Contact contact = new Contact();
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhoneNumber(request.getPhoneNumber());
        contactRepository.save(contact);
        CreateContactResponse response = new CreateContactResponse();
        response.setEmail(contact.getEmail());
        response.setPhoneNumber(contact.getPhoneNumber());
        response.setFirstName(contact.getFirstName());
        response.setLastName(contact.getLastName());
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
    public DeleteContactResponse deleteContact(String contactId) {
        Contact contact = findById(contactId);
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
        UpdateContactResponse response = new UpdateContactResponse();

    }

    private Contact findById(String contactId) {
        return contactRepository.findById(contactId).orElseThrow(() -> new ContactNotFoundException("contact not found "));
    }

    public static UpdateNoteResponse mapNoteUpdateResponse(Note note){
        UpdateNoteResponse response = new UpdateNoteResponse();
        response.setNoteId(note.getId());
        response.setUpdatedTitle(note.getTitle());
        response.setUpdatedContent(note.getContent());
        response.setDateUpdated(note.getDateModified());
        return response;
    }
}
