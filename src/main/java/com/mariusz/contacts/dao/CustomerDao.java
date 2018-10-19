package com.mariusz.contacts.dao;
import com.mariusz.contacts.entity.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerDao {

    Customer create(String name, String surname, String age);
    Customer create(Customer customer);

    Optional<Customer> getCustomer(Long id);

    List<Customer> listCustomers();

    void delete(Long id);

}
