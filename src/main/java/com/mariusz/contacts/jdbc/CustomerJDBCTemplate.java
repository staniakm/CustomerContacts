package com.mariusz.contacts.jdbc;

import com.mariusz.contacts.dao.CustomerDao;
import com.mariusz.contacts.entity.Customer;
import com.mariusz.contacts.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Repository
public class CustomerJDBCTemplate implements CustomerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerJDBCTemplate.class);

    private final JdbcTemplate jdbcTemplateObject;

    @Autowired
    public CustomerJDBCTemplate(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }


    @Override
    public Customer create(String name, String surname, String age) {
        Customer customer = new Customer(name, surname, age);
        return create(customer);
    }

    @Override
    public Customer create(Customer customer) {
        final String SQL = "insert into CUSTOMERS (name, surname, age) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL, new String[] {"id"});
                    ps.setString(1,customer.getName());
                    ps.setString(2,customer.getSurname());
                    ps.setString(3,customer.getAge());
                    return ps;
                }, keyHolder);
        Long newCustomerId = keyHolder.getKey().longValue();
        customer.setId(newCustomerId);
        return customer;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerById(Long id) {
        final String SQL = "select * from CUSTOMERS where id = ?";
        Customer customer = null;
        try {
            customer = jdbcTemplateObject.queryForObject(SQL,
                            new Object[]{id}, new CustomerMapper());
        } catch (Exception e){
            LOGGER.warn("Customer "+id+" not found.");
        }
        return Optional.ofNullable(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> listCustomers() {
        String SQL = "select * from CUSTOMERS";
        return jdbcTemplateObject.query(SQL, new CustomerMapper());
    }

    @Override
    public void delete(Long id) {
        String SQL = "delete from CUSTOMERS where id = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id );
    }

}
