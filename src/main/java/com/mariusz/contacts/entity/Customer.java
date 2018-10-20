package com.mariusz.contacts.entity;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String name;
    private String surname;
    private String age;

    public Customer(String name, String surname, String age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Customer() {
    }
}
