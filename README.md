**SitMinder E-mail Challenge**

_Objective:_

This application provides an abstraction between two different email service providers: Mail Gun and Send Grid.

If one of the services goes down, the service fails over to the other provider

_Solution_
This solution is for the Backend only and has the below features:
1 - Provides RESTful API to send e-mail.
* The URI is: '/send-email'
* Mandatory fields:
    * Sender: a string for the sender name. Mandatory.
    * From: an email of the sender. Mandatory and has to be well-formed e-mail address
    * To: list of recipients e-mails. At least one e-mail should be given and all e-mails have to be well-formed e-mail addresses.
    * CC: list of recipients e-mails in CC. Optional but when given then all e-mails have to be well-formed e-mail addresses.
    * BCC: list of recipients e-mails in BCC. Optional but when given then all e-mails have to be well-formed e-mail addresses.
    * Subject: Mandatory e-mail subject
    * Message: Mandatory e-mail body.
More details about the contract is represented in the YAML file 'email_challenge.yml'

* Fields are being validated
* Test cases have been provided
* Logging has been provided. Logging in file 'logs/siteminder_challenge.log'
* Sample of a valid request:
{
	"sender":"SiteMinder Test",
    "from": "admin@promiseit.com",
    "to": ["mail1@test.com", "mail2@test.com"],
    "cc": ["mail1_cc@test.com", "mail2_cc@test.com"],
    "bcc": ["mail1_bcc@test.com", "mail2_bcc@test.com"],
    "subject": "Test subject",
    "message": "This is to test the process of sending an email"
}

* Third party APIs were avoided but SendGrid are providing their API. That was used to fulfill integration with SendGrid

* Configurations:
    The file 'application.properties' include the below features:
    * server.port : The port will be used to start Tomcat
    * mail.service.active: A key to tell which e-mail provider is the main (values must be mail_gun or send_grid)
    * sending.mail.mailgun.url: A key for MailGun URL
    * sending.mail.mailgun.user.header.value: Value of the header to be used for MailGun
    * sending.mail.mailgun.user.header.authorization.value: Authorization key for MailGun
    * sending.mail.sendgrid.authorization.api.key=Authorization key for SendGrid
    
* Build and deploy steps:
    * Check in the code locally in any local folder (LOCAL_FOLDER)
    * To build, run the maven command: %LOCAL_FOLDER%/mavn clean install 
    * To run:
        %LOCAL_FOLDER%/java -jar target/emailchallenge-0.0.1-SNAPSHOT.jar 