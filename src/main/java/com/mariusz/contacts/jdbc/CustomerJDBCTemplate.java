package com.mariusz.contacts.jdbc;

import com.mariusz.contacts.dao.CustomerDao;
import com.mariusz.contacts.entity.Customer;
import com.mariusz.contacts.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomerJDBCTemplate implements CustomerDao {

    @Autowired
    private final JdbcTemplate jdbcTemplateObject;

    public CustomerJDBCTemplate(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    public void create(String name, String surname, String age) {
        String SQL = "insert into Customer (name, surname, age) values (?, ?, ?)";
        jdbcTemplateObject.update( SQL, name, surname, age);
        System.out.println("Created Record Name = " + name + " surname = " + surname);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomer(Long id) {
        String SQL = "select * from CUSTOMERS where id = ?";
        Customer customer = jdbcTemplateObject.queryForObject(SQL,
                new Object[]{id}, new CustomerMapper());
        return customer;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> listCustomers() {
        String SQL = "select * from CUSTOMERS";
        List <Customer> customers = jdbcTemplateObject.query(SQL, new CustomerMapper());
        return customers;
    }

    @Override
    public void delete(Long id) {
        String SQL = "delete from CUSTOMERS where id = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id );
    }
}
