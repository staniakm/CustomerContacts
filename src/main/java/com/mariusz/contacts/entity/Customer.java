package com.mariusz.contacts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Customer {
    private Long id;
    private String name;
    private String surname;
    private String age;
    @JsonIgnore
    private List<Contact> contacts = new ArrayList<>();

    public Customer(String name, String surname, String age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Customer() {
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public void addContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
