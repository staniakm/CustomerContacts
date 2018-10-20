package com.mariusz.contacts.service;

import com.mariusz.contacts.dao.ContactDao;
import com.mariusz.contacts.entity.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public void deleteCustomerContacts(Long id) {
        contactDao.deleteCustomerContacts(id);
    }

    public List<Contact> getAllContacts() {
        return contactDao.getAll();
    }
}
