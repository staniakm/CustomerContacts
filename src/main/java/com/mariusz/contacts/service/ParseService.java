package com.mariusz.contacts.service;

import com.mariusz.contacts.dao.ContactDao;
import com.mariusz.contacts.dao.CustomerDao;
import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.entity.Customer;
import com.mariusz.contacts.helpers.ContactTypeValidator;
import com.mariusz.contacts.parser.MyHandler;
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
            MyHandler handler = new MyHandler();
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
                    if (record.size() >= 3) {
                        Customer customer = new Customer();
                        customer.setName(record.get(0));
                        customer.setSurname(record.get(1));
                        customer.setAge(record.get(2));
                        customerDao.create(customer);
                        if (record.size() > 4) {
                            for (int i = 4; i < record.size(); i++) {
                                Contact contact = new Contact();
                                contact.setCustomer_id(customer.getId());
                                contact.setType(ContactTypeValidator.validate(record.get(i)));
                                contact.setContact(record.get(i));
                                contactDao.create(contact);
                            }
                        }
                    }
                }
            }
        }
    }
}
