package com.siteminder.challenge.emailchallenge.service;

import com.sendgrid.*;
import com.siteminder.challenge.emailchallenge.model.EMailModel;
import com.siteminder.challenge.emailchallenge.model.EmailResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotEmpty;

@Controller
public class SendGridService {
    private final Logger logger = LoggerFactory.getLogger(SendGridService.class);

    @Value("${sending.mail.sendgrid.authorization.api.key}")
    @NotEmpty
    private String apiKey;

    public EmailResponseModel sendMail(EMailModel email) throws Exception{
        logger.info("SendGridService... Starting sendMail");

        EmailResponseModel responseModel = null;
        Mail mail = setMailSettings(email);
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);

        responseModel = new EmailResponseModel();
        responseModel.setMessage("Queued. Thank you.");

        logger.info("SendGridService... Finishing sendMail");

        return responseModel;
    }

    private Mail setMailSettings(EMailModel emailModel){
        Email from = new Email(emailModel.getFrom());
        from.setName(emailModel.getSender());

        Content content = new Content("text/plain", emailModel.getMessage());
        Mail mail = new Mail();

        mail.setFrom(from);
        mail.setSubject(emailModel.getSubject());
        mail.addPersonalization(setPersonalization(emailModel));
        mail.addContent(content);

        return mail;
    }

    private Personalization setPersonalization(EMailModel emailModel){
        Personalization personalization = new Personalization();

        for (String emailAddress: emailModel.getTo()) {
            personalization.addTo(new Email(emailAddress));
        }

        if(emailModel.getCC() != null && ! emailModel.getCC().equals("")){
            for (String emailAddress: emailModel.getCC()) {
                if(! emailModel.getTo().contains(emailAddress)) {
                    personalization.addCc(new Email(emailAddress));
                }
            }
        }

        if(emailModel.getBCC() != null && ! emailModel.getBCC().equals("")){
            for (String emailAddress: emailModel.getBCC()) {
                if(! emailModel.getTo().contains(emailAddress) && ! emailModel.getCC().contains(emailAddress)) {
                    personalization.addBcc(new Email(emailAddress));
                }
            }
        }
        return personalization;
    }
}
