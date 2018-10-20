package com.mariusz.contacts.helpers;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ContactTypeValidatorTest {

    @Test
    public void shouldReturn_1_ifProperEmailWillBeProvided() {
        int type = ContactTypeValidator.validate("test@test.pl");
        assertThat(type).isEqualTo(1);
    }

    @Test
    public void shouldReturn_2_ifProperPhoneWillBeProvided() {
        // we assume that proper phone number should be build with 9 digits. Other chars will be omitted
        int type = ContactTypeValidator.validate("858-585-585");
        assertThat(type).isEqualTo(2);
    }

    @Test
    public void shouldReturn_3_ifProperJabberWillBeProvided() {
        // information about proper jabber contact were not provided. Lest assume that jabber address contain 'jbr' string
        int type = ContactTypeValidator.validate("test@jbr");
        assertThat(type).isEqualTo(3);
    }

    @Test
    public void shouldReturn_0_ifTypeOfContactIsUnknown() {
        // information about proper jabber contact were not provided. Lest assume that jabber address contain 'jbr' string
        int type = ContactTypeValidator.validate("test@12358");
        assertThat(type).isEqualTo(0);
    }
}