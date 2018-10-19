package com.mariusz.contacts.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {
    private long id;
    private String name;
    private String surname;
    private String age;

}
