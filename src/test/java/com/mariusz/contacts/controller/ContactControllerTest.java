package com.mariusz.contacts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.service.ContactService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ContactControllerTest {


    private MockMvc mockMvc;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;


    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
    }

    @Test
    public void shouldReturnContactWithSpecificId() throws Exception {
        //given
        Contact contact = new Contact(1,"email@email.com");
        contact.setCustomer_id(1L);
        contact.setId(1L);
        given(contactService.getContactById(1L))
                .willReturn(Optional.of(contact));

        //when
        mockMvc
                .perform(get("/api/contacts/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_id", is(1)))
                .andExpect(jsonPath("$.type", is(1)))
                .andExpect(jsonPath("$.contact", is("email@email.com")));
    }


    @Test
    public void typedRequestShouldReturnAllContacts() throws Exception {

        //given
        Contact contact1 = new Contact(1, "email1@email.pl");
        contact1.setId(1L);
        contact1.setCustomer_id(1L);
        Contact contact2 = new Contact(1, "email2@email.pl");
        contact2.setId(2L);
        contact2.setCustomer_id(2L);
        given(contactService.getAllContacts()).willReturn(Arrays.asList(contact1, contact2));

        //when
        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customer_id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].type", Matchers.is(1)))
                .andExpect(jsonPath("$[0].contact", Matchers.is("email1@email.pl")))
                .andExpect(jsonPath("$[1].customer_id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].type", Matchers.is(1)))
                .andExpect(jsonPath("$[1].contact", Matchers.is("email2@email.pl")));

        verify(contactService, times(1)).getAllContacts();
        verifyNoMoreInteractions(contactService);
    }

    @Test
    public void shouldDeleteContactWithSpecificId() throws Exception {
        mockMvc
                .perform(delete("/api/contacts/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }


}