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
    public ResponseEntity<List<Contact>> getAllContacts(){
        return new ResponseEntity<>(contactService.getAllContacts(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Contact> delteContact(@PathVariable ("id") Long id){
        contactService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
