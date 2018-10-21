package com.mariusz.contacts.parser;

import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.entity.Customer;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLHandler extends DefaultHandler {

    private List<Customer> customerList = null;
    private List<Contact> contacts = null;
    private Customer customer = null;

    public List<Customer> getCustomerList() {
        return customerList;
    }

    private boolean fName;
    private boolean sName;
    private boolean age;
    private boolean cPhone;
    private boolean cEmail;
    private boolean cJabber;
    private boolean cOther;
    private boolean bContacts = false;

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) {

        if (qName.equalsIgnoreCase("persons")) {
            // If the list of customers is null, then initialize it
            if (customerList == null)
                customerList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("person")) {
            // Create a new Customer object
            customer = new Customer();
            // Set boolean values for fields, will be used in setting variables
        } else if (qName.equalsIgnoreCase("name")) {
            fName = true;
        } else if (qName.equalsIgnoreCase("surname")) {
            sName = true;
        } else if (qName.equalsIgnoreCase("age")) {
            age = true;
        } else if (qName.equalsIgnoreCase("contacts")) {
            contacts = new ArrayList<>();
            //mark that contacts part of xml will be processed now
            bContacts = true;
        } else if (qName.equalsIgnoreCase("phone")) {
            cPhone = true;
        } else if (qName.equalsIgnoreCase("email")) {
            cEmail = true;
        } else if (qName.equalsIgnoreCase("jabber")) {
            cJabber = true;
        } else {
            //only if contacts part is processed, other type of contact may be registered
            if (bContacts) {
                cOther = true;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        // if closing element appear, customer will be added to list
        if (qName.equalsIgnoreCase("person")) {
            customerList.add(customer);
        } else if (qName.equalsIgnoreCase("contacts")) {
            // add all contacts to current customer
            customer.addContacts(contacts);
            bContacts = false;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) {
        if (fName) {
            customer.setName(new String(ch, start, length));
            fName = false;
        } else if (sName) {
            customer.setSurname(new String(ch, start, length));
            sName = false;
        } else if (age) {
            customer.setAge(new String(ch, start, length));
            age = false;
        } else if (cOther) {
            contacts.add(new Contact(0, new String(ch, start, length)));
            cOther = false;
        } else if (cEmail) {
            contacts.add(new Contact(1, new String(ch, start, length)));
            cEmail = false;
        } else if (cPhone) {
            contacts.add(new Contact(2, new String(ch, start, length)));
            cPhone = false;
        } else if (cJabber) {
            contacts.add(new Contact(3, new String(ch, start, length)));
            cJabber = false;
        }

    }
}
