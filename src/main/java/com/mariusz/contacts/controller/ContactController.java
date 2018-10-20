package com.mariusz.contacts.controller;

import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /***
     * Display list of all contacts
     * @return - status 200 will be returned
     */
    @GetMapping("")
    public ResponseEntity<List<Contact>> getAllContacts() {
        return new ResponseEntity<>(contactService.getAllContacts(), HttpStatus.OK);
    }

    /***
     * Display selected contact
     * @param contactId - contact id
     * @return - if exists selected contact will be returned else 404 status will be returned
     */
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable("id") Long contactId) {
        return contactService
                .getContactById(contactId)
                .map(contact -> new ResponseEntity<>(contact, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /***
     * delete selected contact
     * @param id - contact id
     * @return - status 204 will be returned
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Contact> delteContact(@PathVariable("id") Long id) {
        contactService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
