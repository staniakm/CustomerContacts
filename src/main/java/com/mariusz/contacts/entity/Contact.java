package com.mariusz.contacts.entity;

import lombok.Data;

@Data
public class Contact {
    private Long id;
    private Long customer_id;
    private int type;
    private String contact;

    public Contact(Long customer_id, int type, String contact) {
        this.customer_id = customer_id;
        this.type = type;
        this.contact = contact;
    }

    public Contact() {
    }
}
