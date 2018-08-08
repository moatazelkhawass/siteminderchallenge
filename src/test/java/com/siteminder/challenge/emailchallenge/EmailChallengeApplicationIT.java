package com.siteminder.challenge.emailchallenge;

import com.siteminder.challenge.emailchallenge.model.EMailModel;
import com.siteminder.challenge.emailchallenge.model.EmailResponseModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class EmailChallengeApplicationIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private EMailModel request = new EMailModel();

    @Before
    public void prepareRequest(){
        List<String> toList = new ArrayList<>();
        toList.add("receipient1_integration_test@test.com");
        toList.add("receipient2_integration_test@test.com");

        List<String> ccList = new ArrayList<>();
        ccList.add("cc1_receipient_integration_test@test.com");
        ccList.add("cc2_receipient_integration_test@test.com");

        List<String> bccList = new ArrayList<>();
        bccList.add("bcc1_receipient_integration_test@test.com");
        bccList.add("bcc2_receipient_integration_test@test.com");

        request.setSender("SiteMinder Challenge");
        request.setFrom("admin@siteminder.com");
        request.setTo(toList);
        request.setCc(ccList);
        request.setBcc(bccList);
        request.setSubject("Test subject");
        request.setMessage("This is to test the process of sending an email");
    }

	@Test
	public void contextLoads() {
        EmailResponseModel response = this.restTemplate.postForObject("/send-email", request, EmailResponseModel.class);

	    assertEquals("Queued. Thank you.",response.getMessage());
	}

}
