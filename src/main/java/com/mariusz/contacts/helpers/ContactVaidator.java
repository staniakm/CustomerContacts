package com.mariusz.contacts.helpers;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ContactVaidator {

    public int validateContactType(String contact){
        if (isPhoneNumber(contact)){
            return 2;
        }else if (isValidEmail(contact)){
            return 1;
        }else if (isValidJabber(contact)){
            return 3;
        }
        return 0;
    }

    private boolean isValidJabber(String contact) {
        return contact.contains("jbr");
    }

    private boolean isPhoneNumber(String contact){
        String isbnPattern = "([^\\d])+";
        if (contact!=null)
            return contact.replaceAll(isbnPattern,"").length()==9;
        return false;
    }



    private boolean isValidEmail(String contact) {
        Pattern email_pattern =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = email_pattern.matcher(contact);
        return matcher.find();
    }

}
