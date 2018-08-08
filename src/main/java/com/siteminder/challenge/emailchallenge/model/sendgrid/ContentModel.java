package com.siteminder.challenge.emailchallenge.model.sendgrid;

public class ContentModel {
    private final String type = "text/plain";

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    private String value;

    public ContentModel(String value){
        this.value = value;
    };


}
