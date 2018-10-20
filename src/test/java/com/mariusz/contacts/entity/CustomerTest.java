package com.mariusz.contacts.entity;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;
public class CustomerTest {

    @Test
    public void afterCreation_CustomerFieldsShouldBeEqualToConstructorArguments() {
        String name = "jan";
        String surname = "Kowalski";
        String age = "35";


        Customer customer = new Customer(name,surname,age);

        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getSurname()).isEqualTo(surname);
        assertThat(customer.getAge()).isEqualTo(age);
    }

    @Test
    public void afterCreationListOfContactsShouldNotBeNull() {
        Customer customer = new Customer();

        assertThat(customer.getContacts()).isNotNull();
    }

    @Test
    public void afterCreationIdShouldBeNull() {
        Customer customer = new Customer();
        assertThat(customer.getId()).isNull();
    }

    @Test
    public void shouldBeAbleToAddListOfContacts() {
        Contact contact1 = new Contact(1L,1,"test@test.pl");
        Contact contact2 = new Contact(1L, 2, "234 543 456");
        Customer customer = new Customer();
        customer.setId(1L);

        customer.addContacts(Arrays.asList(contact1, contact2));

        assertThat(customer.getContacts().size()).isEqualTo(2);
        assertThat(customer.getContacts().contains(contact1)).isTrue();
        assertThat(customer.getContacts().contains(contact2)).isTrue();
    }

    @Test
    public void toStroingShouldReturnCustomerDataInReadableForm() {
        String name = "jan";
        String surname = "Kowalski";
        String age = "35";

        Customer customer = new Customer(name,surname,age);

        assertThat(customer.toString()).isEqualTo("Customer{id=null, name='jan', surname='Kowalski', age='35'}");
    }
}