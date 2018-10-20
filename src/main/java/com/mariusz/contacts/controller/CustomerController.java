package com.mariusz.contacts.controller;

import com.mariusz.contacts.dao.CustomerDao;
import com.mariusz.contacts.entity.Customer;
import com.mariusz.contacts.jdbc.CustomerJDBCTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CustomerJDBCTemplate.class);

    private final CustomerDao customerDao;
    @Autowired
    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @GetMapping(value = "/customers")
    public ResponseEntity<List<Customer>> getCustomers(){

        return new ResponseEntity<>(customerDao.listCustomers(),HttpStatus.OK);
    }

    @GetMapping(value = "/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        return customerDao.getCustomer(id).map(customer -> new ResponseEntity<>(customer, HttpStatus.OK)).orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/customers/{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") Long id){
        customerDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody final Customer customer){
        return new ResponseEntity<>(customerDao.create(customer), HttpStatus.OK);
    }



}
