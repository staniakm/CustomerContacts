package com.mariusz.contacts.service;

import com.mariusz.contacts.dao.ContactDao;
import com.mariusz.contacts.dao.CustomerDao;
import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.entity.Customer;
import com.mariusz.contacts.helpers.ContactVaidator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Service
public class UploadService {
    private final ContactDao contactDao;
    private final CustomerDao customerDao;
    private final ContactVaidator contactVaidator;

    @Autowired
    public UploadService(ContactDao contactDao, CustomerDao customerDao, ContactVaidator contactVaidator) {
        this.contactDao = contactDao;
        this.customerDao = customerDao;
        this.contactVaidator = contactVaidator;
    }

    public void parseFile(MultipartFile file) throws IOException {

        switch (file.getContentType()){
            case "application/xml":
                loadXmlFile(file);
                break;
            case "text/plain":
                loadCSVFile(file);
                break;
        }

    }


    private void loadXmlFile(MultipartFile file) throws IOException{
        System.out.println(file.getContentType());
    }


    private void loadCSVFile(MultipartFile file) throws IOException {
        System.out.println(file.getContentType());
        try(InputStream stream = file.getInputStream()) {
            try(BufferedReader reader1 = new BufferedReader(new InputStreamReader(stream, "UTF-8"))) {
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader1);
                for (CSVRecord record : records) {
                    Customer customer = new Customer();
                    customer.setName(record.get(0));
                    customer.setSurname(record.get(1));
                    customer.setAge(record.get(2));
                    customerDao.create(customer);
                    if (record.size() > 4) {
                        for (int i = 4; i < record.size(); i++) {
                            Contact contact = new Contact();
                            contact.setCustomer_id(customer.getId());
                            contact.setType(contactVaidator.validateContactType(record.get(i)));
                            contact.setContact(record.get(i));
                            contactDao.create(contact);
                        }
                    }
                    System.out.println("==========================");
                }
            }
        }
    }
}
