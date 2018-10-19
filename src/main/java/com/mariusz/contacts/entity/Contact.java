package com.mariusz.contacts.entity;

import lombok.Data;

@Data
public class Contact {
    private Long id;
    private Customer customer;
    private int type;
    private String contact;


}
