package com.mariusz.contacts.dao;
import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.entity.Customer;

import java.util.List;
import java.util.Optional;


public interface ContactDao {

    Contact create(Long contactId, int type, String contact);
    Contact create(Contact contact);

    Optional<Contact> getContact(Long id);

    List<Contact> listCustomerContacts(Long customerId);

    void deleteContact(Long id);

    void deleteCustomerContacts(Long id);

    List<Contact> getAll();
}
