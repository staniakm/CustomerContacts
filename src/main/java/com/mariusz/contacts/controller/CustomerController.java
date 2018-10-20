package com.mariusz.contacts.controller;

import com.mariusz.contacts.entity.Customer;
import com.mariusz.contacts.jdbc.CustomerJDBCTemplate;
import com.mariusz.contacts.service.CustomerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @SuppressWarnings("unused")
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CustomerJDBCTemplate.class);

    private final CustomerService customerService;


    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
            }

    @GetMapping(value = "/customers")
    public ResponseEntity<List<Customer>> getCustomers(){

        return new ResponseEntity<>(customerService.getAllCustomers(),HttpStatus.OK);
    }

    @GetMapping(value = "/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        return customerService
                .getCustomerById(id)
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/customers/{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody final Customer customer){
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.OK);
    }



}
