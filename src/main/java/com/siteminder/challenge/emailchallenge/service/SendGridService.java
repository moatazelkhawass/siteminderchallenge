package com.siteminder.challenge.emailchallenge.service;

import com.siteminder.challenge.emailchallenge.model.EMailModel;
import com.siteminder.challenge.emailchallenge.model.EmailResponseModel;
import com.siteminder.challenge.emailchallenge.model.sendgrid.ContentModel;
import com.siteminder.challenge.emailchallenge.model.sendgrid.EmailAddressModel;
import com.siteminder.challenge.emailchallenge.model.sendgrid.PersonalizationModel;
import com.siteminder.challenge.emailchallenge.model.sendgrid.SendGridRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SendGridService {
    private final Logger logger = LoggerFactory.getLogger(SendGridService.class);

    @Value("${sending.mail.sendgrid.authorization.api.key}")
    private String apiKey;

    @Value("${sending.mail.sendgrid.url}")
    private String url;

    public EmailResponseModel sendMail(EMailModel email){
        logger.info("SendGridService... Starting sendMail");

        HttpHeaders headers = setHeaders();

        SendGridRequestModel sendGridRequest = buildSendGridRequest(email);

        HttpEntity request = new HttpEntity(sendGridRequest, headers);
        RestTemplate restTemplate = new RestTemplate();

        EmailResponseModel response = restTemplate.postForObject(url, request, EmailResponseModel.class);

        logger.info("MailGunService... Finishing sendMail");

        return response;
    }

    private HttpHeaders setHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);
        return headers;
    }

    private SendGridRequestModel buildSendGridRequest(EMailModel email){
        SendGridRequestModel sendGridRequest = new SendGridRequestModel();
        PersonalizationModel personalization = buildPersonalization(email);
        EmailAddressModel emailAddressModel;

        List<PersonalizationModel> personalizationModels = new ArrayList<>();
        personalizationModels.add(personalization);

        emailAddressModel = new EmailAddressModel(email.getFrom());
        emailAddressModel.setName(email.getSender());

        ContentModel content = new ContentModel(email.getMessage());

        sendGridRequest.setPersonalizations(personalizationModels);
        sendGridRequest.setFrom(emailAddressModel);
        sendGridRequest.setSubject(email.getSubject());
        sendGridRequest.setContent(content);

        return sendGridRequest;
    }

    private PersonalizationModel buildPersonalization(EMailModel email){
        PersonalizationModel personalization = new PersonalizationModel();
        List<EmailAddressModel> listOfMails = buildTo(email);

        personalization.setTo(listOfMails);

        listOfMails = buildCC(email);
        if(listOfMails.size() > 0)
            personalization.setCc(listOfMails);

        listOfMails = buildBCC(email);
        if(listOfMails.size() > 0)
            personalization.setBcc(listOfMails);

        return personalization;
    }

    private List<EmailAddressModel> buildTo(EMailModel email){
        List<EmailAddressModel> listOfMails = new ArrayList<>();
        EmailAddressModel emailAddressModel;
        for (String emailAddress : email.getTo()) {
            emailAddressModel = new EmailAddressModel(emailAddress);
            listOfMails.add(emailAddressModel);
        }
        return listOfMails;
    }

    private List<EmailAddressModel> buildCC(EMailModel email){
        List<EmailAddressModel> listOfMails = new ArrayList<>();
        if(email.getCC() != null){
            listOfMails = new ArrayList<>();
            EmailAddressModel emailAddressModel;
            for (String emailAddress : email.getCC()) {
                if(! email.getTo().contains(emailAddress)) {
                    emailAddressModel = new EmailAddressModel(emailAddress);
                    listOfMails.add(emailAddressModel);
                }
            }
        }

        return listOfMails;
    }

    private List<EmailAddressModel> buildBCC(EMailModel email){
        List<EmailAddressModel> listOfMails = new ArrayList<>();
        if(email.getBCC() != null) {
            listOfMails = new ArrayList<>();
            EmailAddressModel emailAddressModel;
            for (String emailAddress : email.getBCC()) {
                if(! email.getTo().contains(emailAddress) && ! email.getCC().contains(emailAddress)) {
                    emailAddressModel = new EmailAddressModel(emailAddress);
                    listOfMails.add(emailAddressModel);
                }
            }
        }

        return listOfMails;
    }
}
