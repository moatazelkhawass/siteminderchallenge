package com.siteminder.challenge.emailchallenge.model;

public class EmailResponseModel {
    private String message;

    public EmailResponseModel(String message) {
        this.message = message;
    }

    public EmailResponseModel() {
    }

    public String getMessage(){ return message;}

    public void setMessage(String message) {
        this.message = message;
    }
}
