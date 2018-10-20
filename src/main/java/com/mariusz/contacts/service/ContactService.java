package com.mariusz.contacts.service;

import com.mariusz.contacts.dao.ContactDao;
import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.entity.Customer;
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

    public List<Contact> getCustomerContacts(Long customerId){
        return contactDao.listCustomerContacts(customerId);
    }

    public List<Contact> getCustomerContacts(Customer customer){
        return getCustomerContacts(customer.getId());
    }
}
