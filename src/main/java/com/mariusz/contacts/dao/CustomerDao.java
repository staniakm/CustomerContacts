package com.mariusz.contacts.dao;

import com.mariusz.contacts.entity.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerDao {

    Customer create(String name, String surname, String age);

    Customer create(Customer customer);

    Optional<Customer> getCustomerById(Long id);

    List<Customer> findAll();

    void delete(Long id);
}
