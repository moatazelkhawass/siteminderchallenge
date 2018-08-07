package com.siteminder.challenge.emailchallenge.controller;

import com.siteminder.challenge.emailchallenge.model.EMailModel;
import com.siteminder.challenge.emailchallenge.model.EmailResponseModel;
import com.siteminder.challenge.emailchallenge.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    private final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @PostMapping(path="/send-email")
    public EmailResponseModel sendEmail(@Valid @RequestBody EMailModel email) throws Exception{
        logger.info("Request received... Sender is " + email.getSender());
        EmailResponseModel responseModel = emailService.sendMail(email);
        logger.info("Response received from backend.. sending response back");
        return responseModel;
    }
}