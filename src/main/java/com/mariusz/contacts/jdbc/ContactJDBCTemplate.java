package com.mariusz.contacts.jdbc;

import com.mariusz.contacts.dao.ContactDao;
import com.mariusz.contacts.entity.Contact;
import com.mariusz.contacts.mapper.ContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Repository
public class ContactJDBCTemplate implements ContactDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactJDBCTemplate.class);

    private final JdbcTemplate jdbcTemplateObject;

    @Autowired
    public ContactJDBCTemplate(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }


    /***
     * create new contact in database
     * @param contact - contact data
     * @return - newly created contact
     */
    public Contact create(Contact contact) {
        final String SQL = "insert into CONTACTS (ID_CUSTOMER, TYPE, CONTACT) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL, new String[] {"id"});
                    ps.setLong(1,contact.getCustomer_id());
                    ps.setInt(2,contact.getType());
                    ps.setString(3,contact.getContact());
                    return ps;
                }, keyHolder);
        Long newContactId = keyHolder.getKey().longValue();
        contact.setId(newContactId);
        return contact;
    }

    /***
     * retrieve contact from database based on provided id
     * @param id - contact id
     * @return - optional contact.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Contact> getContact(Long id) {
        final String SQL = "select * from CONTACTS where id = ?";
        Contact contact = null;
        try {
            contact = jdbcTemplateObject.queryForObject(SQL,
                            new Object[]{id}, new ContactMapper());
        } catch (Exception e){
            LOGGER.warn("Contact "+id+" not found.");
        }
        return Optional.ofNullable(contact);
    }

    /***
     * get all contact for specified customer
     * @param customerId -
     * @return - list of contacts
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contact> listCustomerContacts(Long customerId) {
        String SQL = "select * from CONTACTS where ID_CUSTOMER  = ?";
        return jdbcTemplateObject.query(SQL,new Object[]{customerId}, new ContactMapper());
    }

    /***
     * get all contacts form database
     * @return - list
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contact> getAll() {
        String SQL = "select * from CONTACTS";
        return jdbcTemplateObject.query(SQL, new ContactMapper());
    }

    /***
     * delete specified contact
     * @param id - contact id
     */
    @Override
    public void deleteContact(Long id) {
        String SQL = "delete from CONTACTS where id = ?";
        jdbcTemplateObject.update(SQL, id);
    }

    /***
     * delete all contacts assigned to specified customer
     * @param customerId - customer id
     */
    @Override
    public void deleteCustomerContacts(Long customerId) {
        String SQL = "delete from CONTACTS where id_customer = ?";
        jdbcTemplateObject.update(SQL, customerId);
    }

}
