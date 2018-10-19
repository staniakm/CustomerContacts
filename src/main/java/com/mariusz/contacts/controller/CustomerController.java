package com.mariusz.contacts.controller;

import com.mariusz.contacts.dao.CustomerDao;
import com.mariusz.contacts.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    @GetMapping(value = "/customers")
    public ResponseEntity<List<Customer>> getCustomers(){

        return new ResponseEntity<>(customerDao.listCustomers(),HttpStatus.OK);
    }
}
