package com.siteminder.challenge.emailchallenge.service;

import com.siteminder.challenge.emailchallenge.model.EMailModel;
import com.siteminder.challenge.emailchallenge.model.EmailResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class EmailService {
    @Value("${mail.service.active}")
    private String activeMailService;

    @Autowired
    private MailGunService mailGunService;

    @Autowired
    private SendGridService sendGridService;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailResponseModel sendMail(EMailModel email) {
        logger.info("Starting sendMail");
        EmailResponseModel responseModel;

        try{
            if(activeMailService.equals("mail_gun")){
                logger.info("Active mail server is MailGun.. using MailGun");
                responseModel = mailGunService.sendMail(email);
            }
            else {
                logger.info("Active mail server is SendGrid.. using SendGrid");
                responseModel = sendGridService.sendMail(email);
            }
        }
        catch (Exception ex){
            if(activeMailService.equals("mail_gun")){
                logger.info("Active mail server is MailGun.. Failing over to SendGrid");
                responseModel = sendGridService.sendMail(email);
            }
            else {
                logger.info("Active mail server is SendGrid.. Failing over to MailGun");
                responseModel = mailGunService.sendMail(email);
            }
        }

        logger.info("Finishing sendMail");

        return responseModel;
    }
}