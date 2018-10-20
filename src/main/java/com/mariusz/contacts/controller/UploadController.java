package com.mariusz.contacts.controller;

import com.mariusz.contacts.entity.Customer;
import com.mariusz.contacts.jdbc.CustomerJDBCTemplate;
import com.mariusz.contacts.service.UploadService;
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

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping(value = "/bulkLoad")
    public ResponseEntity<Customer> bulkLoadData(@RequestParam("file") MultipartFile file){
        try {
            uploadService.parseFile(file);
        } catch (IOException e) {
            LOGGER.warn(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
