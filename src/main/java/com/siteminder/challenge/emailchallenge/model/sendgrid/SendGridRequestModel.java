package com.siteminder.challenge.emailchallenge.model.sendgrid;

import java.util.List;

public class SendGridRequestModel {
    private List<PersonalizationModel> personalizations;
    private EmailAddressModel from;
    private String subject;
    private List<ContentModel> content;

    public List<PersonalizationModel> getPersonalizations() {
        return personalizations;
    }

    public void setPersonalizations(List<PersonalizationModel> personalizations) {
        this.personalizations = personalizations;
    }

    public EmailAddressModel getFrom() {
        return from;
    }

    public void setFrom(EmailAddressModel from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<ContentModel> getContent() {
        return content;
    }

    public void setContent(List<ContentModel> content) {
        this.content = content;
    }
}
