package com.mariusz.contacts.dao;
import com.mariusz.contacts.entity.Customer;

import java.util.List;


public interface CustomerDao {

    void create(String name, String surname, String age);

    Customer getCustomer(Long id);

    List<Customer> listCustomers();

    void delete(Long id);

}
