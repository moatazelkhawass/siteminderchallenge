package com.siteminder.challenge.emailchallenge.model.sendgrid;

import java.util.List;

public class PersonalizationModel {
    List<EmailAddressModel> to;
    List<EmailAddressModel> cc;
    List<EmailAddressModel> bcc;

    public List<EmailAddressModel> getTo() {
        return to;
    }

    public void setTo(List<EmailAddressModel> to) {
        this.to = to;
    }

    public List<EmailAddressModel> getCc() {
        return cc;
    }

    public void setCc(List<EmailAddressModel> cc) {
        this.cc = cc;
    }

    public List<EmailAddressModel> getBcc() {
        return bcc;
    }

    public void setBcc(List<EmailAddressModel> bcc) {
        this.bcc = bcc;
    }
}
