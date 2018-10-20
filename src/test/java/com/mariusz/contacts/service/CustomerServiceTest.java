package com.mariusz.contacts.service;

import com.mariusz.contacts.dao.CustomerDao;
import com.mariusz.contacts.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;

    private CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        customerService = new CustomerService(customerDao);
    }

    @Test
    public void shouldReturnListOfAllCustomers(){
        Customer customer1 = new Customer("Jan","Kowalski","12");
        Customer customer2 = new Customer("Paweł","Nowak","35");

        given(customerDao.findAll()).willReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = customerService.getAllCustomers();

        assertThat(customers.size()).isEqualTo(2);
        assertThat(customers.contains(customer1)).isTrue();
        assertThat(customers.contains(customer2)).isTrue();
        verify(customerDao, times(1)).findAll();
    }

    @Test
    public void shouldReturnNotEmptyOptionalWithCustomer() {
        Customer customer = new Customer("Jan","Kowalski","12");
        customer.setId(1L);

        given(customerDao.getCustomerById(1L)).willReturn(Optional.of(customer));

        Optional<Customer> newCustomer = customerService.getCustomerById(1L);

        assertThat(newCustomer.isPresent()).isTrue();
        assertThat( newCustomer.get().getName()).isEqualTo(customer.getName());
        assertThat(newCustomer.get().getId()).isEqualTo(customer.getId());
        verify(customerDao, times(1)).getCustomerById(1L);
    }

    @Test
    public void shouldDeleteBeExecutedOnlyOnce() {

        customerService.deleteCustomer(1L);

        verify(customerDao,times(1)).delete(1L);
    }

    @Test
    public void shouldBeAbleToCreateCustomer() {
        Customer customer = new Customer("Jan","Kowalski","12");
        customer.setId(1L);

        given(customerDao.create(any(Customer.class))).willReturn(customer);

        Customer newCustomer = customerService.createCustomer(new Customer("Jan","Kowalski","12"));

        assertThat(newCustomer.getId()).isNotZero();
        assertThat(newCustomer.getName()).isEqualTo(customer.getName());
        assertThat(newCustomer.getAge()).isEqualTo(customer.getAge());

        verify(customerDao, times(1)).create(new Customer("Jan","Kowalski","12"));
    }
}