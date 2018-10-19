package com.mariusz.contacts.dao;
import com.mariusz.contacts.entity.Customer;

import java.util.List;
import javax.sql.DataSource;


public interface CustomerDao {

    public void create(String name, String surname, String age);

    public Customer getCustomer(Long id);

    public List<Customer> listCustomers();

    public void delete(Long id);

}
