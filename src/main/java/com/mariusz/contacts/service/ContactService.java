package com.mariusz.contacts.service;

import com.mariusz.contacts.dao.ContactDao;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public void deleteCustomerContacts(Long id) {
        contactDao.deleteCustomerContacts(id);
    }
}
