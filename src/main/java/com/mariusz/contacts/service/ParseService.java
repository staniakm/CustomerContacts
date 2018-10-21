package com.mariusz.contacts.service;

import com.mariusz.contacts.dao.ContactDao;
import com.mariusz.contacts.dao.CustomerDao;
import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.entity.Customer;
import com.mariusz.contacts.helpers.ContactTypeValidator;
import com.mariusz.contacts.parser.XMLHandler;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.List;


@Service
public class ParseService {
    private final ContactDao contactDao;
    private final CustomerDao customerDao;

    @Autowired
    public ParseService(ContactDao contactDao, CustomerDao customerDao) {
        this.contactDao = contactDao;
        this.customerDao = customerDao;
    }

    public void parseFile(MultipartFile file) throws IOException {

        switch (file.getContentType()){
            case "application/xml":
                parseXMLFile(file);
                break;
            case "text/plain":
                parseCSVFile(file);
                break;
        }
    }


    private void parseXMLFile(MultipartFile file) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLHandler handler = new XMLHandler();
            try(InputStream stream = file.getInputStream()) {
                Reader reader = new InputStreamReader(stream,"UTF-8");
                InputSource is = new InputSource(reader);
                is.setEncoding("UTF-8");

                saxParser.parse(stream, handler);
            }
            List<Customer> customersList = handler.getCustomerList();
            for (Customer customer : customersList) {
                customerDao.create(customer);
                for (Contact contact: customer.getContacts()
                     ) {
                    contact.setCustomer_id(customer.getId());
                    contactDao.create(contact);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }


    private void parseCSVFile(MultipartFile file) throws IOException {
        try(InputStream stream = file.getInputStream()) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"))) {
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                for (CSVRecord record : records) {
                    // process only if record size is at least 3 (name, surname, age)
                    if (record.size() >= 3) {
                        Customer customer = createCustomer(record);
                        // all data greater then 4 should be considered as contact data
                        if (record.size() > 4) {
                            for (int i = 4; i < record.size(); i++) {
                                // don't add contact with empty string
                                String contactData = record.get(i);
                                if (!(contactData.trim().length()==0 || contactData.isEmpty())) {
                                    createContact(customer, contactData);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void createContact(Customer customer, String contactData) {
        Contact contact = new Contact();
        contact.setCustomer_id(customer.getId());
        contact.setType(ContactTypeValidator.validate(contactData));
        contact.setContact(contactData);
        contactDao.create(contact);
    }

    private Customer createCustomer(CSVRecord record) {
        Customer customer = new Customer();
        customer.setName(record.get(0));
        customer.setSurname(record.get(1));
        customer.setAge(record.get(2));
        customerDao.create(customer);
        return customer;
    }
}
