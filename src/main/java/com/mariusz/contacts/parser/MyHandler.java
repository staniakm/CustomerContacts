package com.mariusz.contacts.parser;

import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.entity.Customer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MyHandler extends DefaultHandler {

    //List to hold Employees object
    private List<Customer> customerList = null;
    private List<Contact> contacts = null;
    private Customer customer = null;

    // Getter method for list of computers list
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
                             Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("persons")) {
            // If the list of customers is null, then initialize it
            if (customerList == null)
                customerList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("person")) {
            // Create a new Computer object, and set the serial number from the attribute
            customer = new Customer();

            // Set boolean values for fields, will be used in setting Employee variables
        } else if (qName.equalsIgnoreCase("name")) {
            fName = true;
        } else if (qName.equalsIgnoreCase("surname")) {
            sName = true;
        } else if (qName.equalsIgnoreCase("age")) {
            age = true;
        }else if (qName.equalsIgnoreCase("contacts")){
                contacts = new ArrayList<>();
                bContacts = true;
        }else if (qName.equalsIgnoreCase("phone")){
            cPhone = true;
        }else if (qName.equalsIgnoreCase("email")){
            cEmail = true;
        }else if (qName.equalsIgnoreCase("jabber")){
            cJabber = true;
        }else {
            if (bContacts) {
                cOther = true;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("person")) {
            // Add the Computer object to the list
            customerList.add(customer);
        }else if (qName.equalsIgnoreCase("contacts")){
            customer.addContacts(contacts);
            bContacts = false;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (fName) {
            // Set name
            customer.setName(new String(ch, start, length));
            fName = false;
        } else if (sName) {
            customer.setSurname(new String(ch, start, length));
            sName = false;
        } else if (age) {
            customer.setAge(new String(ch, start, length));
            age = false;
        } else if (cOther) {
            contacts.add(new Contact(0,new String(ch, start, length)));
            cOther = false;
        } else if (cEmail) {
            contacts.add(new Contact(1,new String(ch, start, length)));
            cEmail = false;
        } else if (cPhone) {
            contacts.add(new Contact(2,new String(ch, start, length)));
            cPhone = false;
        } else if (cJabber) {
            contacts.add(new Contact(3,new String(ch, start, length)));
            cJabber = false;
        }

    }
}
