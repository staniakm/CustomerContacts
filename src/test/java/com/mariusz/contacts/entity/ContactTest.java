package com.mariusz.contacts.entity;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ContactTest {

    @Test
    public void shouldAllowToCreateNewContactWithourConstructorArtgs() {

        Contact contact = new Contact();
        assertThat(contact).isNotNull();
    }

    @Test
    public void shouldBeAbleToCreateContactWithTypeAndData() {
        Contact contact = new Contact(1,"emai@email.pl");

        assertThat(contact).isNotNull();
        assertThat(contact.getType()).isEqualTo(1);
        assertThat(contact.getContact()).isEqualTo("emai@email.pl");
    }

    @Test
    public void shouldReturnContactInReadableForm() {
        Contact contact = new Contact(1,"eamil@eamil.pl");
        assertThat(contact.toString()).isEqualTo("Contact(id=null, customer_id=null, type=1, contact=eamil@eamil.pl)");
    }
}