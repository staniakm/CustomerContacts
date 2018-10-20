package com.mariusz.contacts.mapper;

import com.mariusz.contacts.entity.Contact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMapper implements RowMapper<Contact> {
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getLong("id"));
        contact.setCustomer_id(rs.getLong("ID_CUSTOMER"));
        contact.setType(rs.getInt("TYPE"));
        contact.setContact(rs.getString("CONTACT"));
        return contact;
    }
}
