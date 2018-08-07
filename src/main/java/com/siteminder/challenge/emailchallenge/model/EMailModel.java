package com.siteminder.challenge.emailchallenge.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class EMailModel {
    @ApiModelProperty(notes="Mandatory field")
    @NotNull @NotEmpty private String sender;

    @ApiModelProperty(notes="Mandatory field and has to be well e-mail format")
    @NotNull @NotEmpty @Email(message = "Invalid e-mail address") private String from;

    @ApiModelProperty(notes="Mandatory field. Can take multiple recipients, each has to be valid e-mail format")
    @NotNull @NotEmpty private List<@Email(message = "Invalid e-mail address") String> to;

    @ApiModelProperty(notes="Mandatory field")
    @NotNull @NotEmpty private String subject;

    @ApiModelProperty(notes="Mandatory field")
    @NotNull @NotEmpty private String message;

    @ApiModelProperty(notes="Optional field. Can take multiple recipients, each has to be valid e-mail format")
    private List<@Email(message = "Invalid e-mail address") String> cc;

    @ApiModelProperty(notes="Optional field. Can take multiple recipients, each has to be valid e-mail format")
    private List<@Email(message = "Invalid e-mail address") String> bcc;

    public EMailModel(){}

    public void setSender(String sender) { this.sender = sender; }

    public void setFrom(String from) { this.from = from; }

    public void setTo(List<String> to) { this.to = to; }

    public void setSubject(String subject) { this.subject = subject; }

    public void setMessage(String message) { this.message = message; }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public String getSender(){ return sender;}
    public String getFrom() {
        return from;
    }

    public List<String> getTo() {
        return to;
    }
    public List<String> getCC() {
        return cc;
    }
    public List<String> getBCC() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("{'sender':'%s','from':'%s','to':'%s','cc':'%s','bcc':'%s'," +
                "'subject':'%s','text':'%s'}",sender,from,to,cc,bcc,subject,message);
    }
}
