package com.siteminder.challenge.emailchallenge.model.sendgrid;

public class EmailAddressModel {
    private String email;
    private String name;

    public EmailAddressModel(String email){
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
