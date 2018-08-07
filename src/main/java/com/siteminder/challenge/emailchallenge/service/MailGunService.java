package com.siteminder.challenge.emailchallenge.service;

import com.siteminder.challenge.emailchallenge.model.EMailModel;
import com.siteminder.challenge.emailchallenge.model.EmailResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotEmpty;
import java.io.File;

@Controller
public class MailGunService {
    @Value("${sending.mail.mailgun.url}")
    @NotEmpty
    private String mailGunUrl;

    @Value("${sending.mail.mailgun.user.header.value}")
    @NotEmpty
    private String userHeaderValue;

    @Value("${sending.mail.mailgun.user.header.authorization.value}")
    @NotEmpty
    private String authorizationHeaderValue;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailResponseModel sendMail(EMailModel email){
        logger.info("MailGunService... Starting sendMail");

        HttpHeaders headers = addHeaders();
        MultiValueMap<String, Object> map = addParams(email);
        HttpEntity request = new HttpEntity(map, headers);
        RestTemplate restTemplate = new RestTemplate();

        EmailResponseModel response = restTemplate.postForObject(mailGunUrl, request, EmailResponseModel.class);

        logger.info("MailGunService... Finishing sendMail");

        return response;
    }

    private HttpHeaders addHeaders(){
        HttpHeaders headers = new HttpHeaders();

        headers.set("user", userHeaderValue);
        headers.set("Authorization", authorizationHeaderValue);

        return headers;
    }

    private MultiValueMap<String, Object> addParams(EMailModel email){
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("from", email.getSender() + " <" + email.getFrom() + ">");

        for (String recipient: email.getTo()) {
            map.add("to", recipient);
        }

        if(email.getCC() != null && ! email.getCC().equals("")) {
            for (String ccRecipient : email.getCC()) {
                map.add("cc", ccRecipient);
            }
        }

        if(email.getBCC() != null && ! email.getBCC().equals("")) {
            for (String bccRecipient: email.getBCC()) {
                map.add("bcc", bccRecipient);
            }
        }

        map.add("subject", email.getSubject());
        map.add("text", email.getMessage());
        map.add("attachment", new File("C:/Users/Moataz ElKhawass/Desktop/zizo_pics_2018/b0a391bdf25510f10b2b91d93ee19ef7.jpg"));
        return map;
    }
}