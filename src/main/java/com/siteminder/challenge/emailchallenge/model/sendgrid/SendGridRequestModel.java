package com.siteminder.challenge.emailchallenge.model.sendgrid;

import java.util.List;

public class SendGridRequestModel {
    private List<PersonalizationModel> personalizations;
    private EmailAddressModel from;
    private String subject;
    private ContentModel content;

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

    public ContentModel getContent() {
        return content;
    }

    public void setContent(ContentModel content) {
        this.content = content;
    }
}
