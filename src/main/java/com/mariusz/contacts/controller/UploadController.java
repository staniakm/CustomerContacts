package com.mariusz.contacts.controller;

import com.mariusz.contacts.entity.Customer;
import com.mariusz.contacts.jdbc.CustomerJDBCTemplate;
import com.mariusz.contacts.service.ParseService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/upload")
public class UploadController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CustomerJDBCTemplate.class);

    private final ParseService parseService;

    public UploadController(ParseService parseService) {
        this.parseService = parseService;
    }

    /***
     * Upload file for parsing.
     * @param file - multipart file
     * @return - return status.
     */
    @PostMapping(value = "")
    public ResponseEntity<Customer> bulkLoadData(@RequestParam("file") MultipartFile file){
        try {
            parseService.parseFile(file);
        } catch (IOException e) {
            LOGGER.warn(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
