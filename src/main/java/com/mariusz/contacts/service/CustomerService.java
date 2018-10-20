package com.mariusz.contacts.service;

import com.mariusz.contacts.dao.CustomerDao;
import com.mariusz.contacts.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.listCustomers();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerDao.getCustomerById(id);
    }

    public void deleteCustomer(Long id) {
        customerDao.delete(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerDao.create(customer);
    }
}
