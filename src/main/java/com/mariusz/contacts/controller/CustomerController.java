package com.mariusz.contacts.controller;

import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.entity.Customer;
import com.mariusz.contacts.jdbc.CustomerJDBCTemplate;
import com.mariusz.contacts.service.ContactService;
import com.mariusz.contacts.service.CustomerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @SuppressWarnings("unused")
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CustomerJDBCTemplate.class);

    private final CustomerService customerService;
    private final ContactService contactService;


    @Autowired
    public CustomerController(CustomerService customerService, ContactService contactService) {
        this.customerService = customerService;
        this.contactService = contactService;
    }


    /***
     * Display all saved customers
     * @return list of customers
     */
    @GetMapping(value = "")
    public ResponseEntity<List<Customer>> getCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(),HttpStatus.OK);
    }

    /***
     * display selected customer. If customer with selected id is not exist, 404 status will be display
     * @param id - customer id
     * @return - selected customer data
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        return customerService
                .getCustomerById(id)
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /***
     * Display all contacts that belong to selected customer. If customer with selected id is not exist, 404 status will be display
     * @param id - customer id
     * @return - list of all contacts that as assigned to selected customer
     */
    @GetMapping(value = "/{id}/contacts")
    public ResponseEntity<List<Contact>> getCustomerContacts(@PathVariable("id") Long id){
        return customerService
                .getCustomerById(id)
                .map(customer -> new ResponseEntity<>(contactService.getCustomerContacts(customer), HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /***
     * Delete selected customer if exist. All contacts that are assigned to customer will be also deleted.
     * @param id - customer id
     * @return - status 204 will be returned
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
        contactService.deleteCustomerContacts(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /***
     * Post new customer.
     * @param customer - json representation of customer.
     * @return - created customer. Status 201 will be returned.
     */
    @PostMapping(value = "")
    public ResponseEntity<Customer> createCustomer(@RequestBody final Customer customer){
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }


    /***
     * Post new customer.
     * @param customerId - id of customer for which new contact will be added
     * @param contact- json representation of new contact.
     * @return - created customer. Status 201 will be returned.
     */
    @PostMapping(value = "/{id}/contact")
    public ResponseEntity<Contact> createCustomer(@PathVariable("id") Long customerId, @RequestBody Contact contact){
        return customerService
                .getCustomerById(customerId)
                .map(customer -> new ResponseEntity<>(contactService.createContact(customer, contact), HttpStatus.CREATED))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
