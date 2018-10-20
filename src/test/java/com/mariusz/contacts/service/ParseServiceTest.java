package com.mariusz.contacts.service;

import com.mariusz.contacts.dao.ContactDao;
import com.mariusz.contacts.dao.CustomerDao;
import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.entity.Customer;
import com.mariusz.contacts.jdbc.ContactJDBCTemplate;
import com.mariusz.contacts.jdbc.CustomerJDBCTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ParseServiceTest {

    @Mock
    private ContactDao contacts;

    @Mock
    private CustomerDao customers;


    private ParseService service;

    @Before
    public void setUp() throws Exception {
        service = new ParseService( contacts, customers);
    }

    @Test
    public void shouldCallJDBCMethodsNumberOfTimes() throws IOException {
        MockMultipartFile file =
                new MockMultipartFile("file", "file.txt", "text/plain", "Jan,Kowalski,12,Lublin,123123123,654 765 765,kowalski@gmail.com,jan@gmail.com".getBytes(StandardCharsets.UTF_8));
        service.parseFile(file);

        verify(contacts, times(4)).create(any(Contact.class));
        verify(customers, times(1)).create(any(Customer.class));
        verifyNoMoreInteractions(contacts, customers);

    }


    @Test
    public void shouldCorrectParseCsvFileAndCreateCustomer() throws IOException {
        Customer customer = new Customer("Jan","Kowalski","12");
        MockMultipartFile file =
                new MockMultipartFile("file", "file.txt", "text/plain", "Jan,Kowalski,12,Lublin,123123123,654 765 765,kowalski@gmail.com,jan@gmail.com".getBytes(StandardCharsets.UTF_8));
        service.parseFile(file);

        verify(customers, times(1)).create(customer);
        verifyNoMoreInteractions( customers);
    }

    @Test
    public void shouldCorrectParseCsvFile() throws IOException {
        Customer customer = new Customer("Jan","Kowalski","12");
        customer.setId(1L);
        given(customers.create(any(Customer.class))).willReturn(customer);

        Contact contact1 = new Contact(2,"123123123");
        Contact contact2 = new Contact(2,"654 765 765");

        MockMultipartFile file =
                new MockMultipartFile("file", "file.txt", "text/plain", "Jan,Kowalski,12,Lublin,123123123,654 765 765".getBytes(StandardCharsets.UTF_8));
        service.parseFile(file);

        verify(contacts).create(contact1);
        verify(contacts).create(contact2);
        verifyNoMoreInteractions( contacts);
    }

    @Test
    public void shouldCallJDBCMethodsNumberOfTimesParsingXml() throws IOException {
        String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<persons>\n" +
                "    <person>\n" +
                "        <name>Jan</name>\n" +
                "        <surname>Kowalski</surname>\n" +
                "        <age>12</age>\n" +
                "        <city>Lublin</city>\n" +
                "        <contacts>\n" +
                "            <phone>123123123</phone>\n" +
                "            <email>kowalski@gmail.com</email>\n" +
                "            <email>jan@gmail.com</email>\n" +
                "        </contacts>\n" +
                "    </person></persons>";


        MockMultipartFile file =
                new MockMultipartFile("file", "file.xlm", "application/xml", xml.getBytes(StandardCharsets.UTF_8));
        service.parseFile(file);

        verify(contacts, times(3)).create(any(Contact.class));
        verify(customers, times(1)).create(any(Customer.class));
        verifyNoMoreInteractions(contacts, customers);

    }


    @Test
    public void shouldCorrectParseXmlFileAndCreateCustomer() throws IOException {
        String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<persons>\n" +
                "    <person>\n" +
                "        <name>Jan</name>\n" +
                "        <surname>Kowalski</surname>\n" +
                "        <age>12</age>\n" +
                "        <city>Lublin</city>\n" +
                "    </person></persons>";


        Customer customer = new Customer("Jan","Kowalski","12");
        MockMultipartFile file =
                new MockMultipartFile("file", "file.xlm", "application/xml", xml.getBytes(StandardCharsets.UTF_8));
        service.parseFile(file);

        verify(customers, times(1)).create(customer);
        verifyNoMoreInteractions( customers);
    }

    @Test
    public void shouldCorrectParseXMLFileAndCreateCustomerWithContacts() throws IOException {
        String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<persons>\n" +
                "    <person>\n" +
                "        <name>Jan</name>\n" +
                "        <surname>Kowalski</surname>\n" +
                "        <age>12</age>\n" +
                "        <city>Lublin</city>\n" +
                "        <contacts>\n" +
                "            <phone>123123123</phone>\n" +
                "            <email>kowalski@gmail.com</email>\n" +
                "            <email>jan@gmail.com</email>\n" +
                "        </contacts>\n" +
                "    </person></persons>";

        Customer customer = new Customer("Jan","Kowalski","12");
        customer.setId(1L);

        given(customers.create(any(Customer.class))).willReturn(customer);

        Contact contact1 = new Contact(2,"123123123");
        Contact contact2 = new Contact(1,"kowalski@gmail.com");
        Contact contact3 = new Contact(1,"jan@gmail.com");


        MockMultipartFile file =
                new MockMultipartFile("file", "file.xlm", "application/xml", xml.getBytes(StandardCharsets.UTF_8));
        service.parseFile(file);

        verify(contacts).create(contact1);
        verify(contacts).create(contact2);
        verify(contacts).create(contact3);
        verifyNoMoreInteractions( contacts);
    }

}