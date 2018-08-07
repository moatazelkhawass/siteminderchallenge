package com.siteminder.challenge.emailchallenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteminder.challenge.emailchallenge.model.EMailModel;
import com.siteminder.challenge.emailchallenge.service.EmailService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailController.class)
public class EmailControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    private static EMailModel emailModel = new EMailModel();

    @BeforeClass
    public static void setupRequest(){
        List<String> toList = new ArrayList<>();
        toList.add("first.receipient@test.com");
        toList.add("second.receipient@test.com");

        List<String> ccList = new ArrayList<>();
        ccList.add("first.cc.receipient@test.com");
        ccList.add("second.cc.receipient@test.com");

        List<String> bccList = new ArrayList<>();
        bccList.add("first.bcc.receipient@test.com");
        bccList.add("second.bcc.receipient@test.com");

        emailModel.setSender("SiteMinder Challenge");
        emailModel.setFrom("admin@siteminderchallenge.com");
        emailModel.setTo(toList);
        emailModel.setCc(ccList);
        emailModel.setBcc(bccList);
        emailModel.setSubject("Test subject");
        emailModel.setMessage("This is to test the process of sending an email");
    }

    @Test
    public void sendMailTestSuccessful() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .post("/send-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailModel));

       mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void sendMailTestInvalidSender() throws Exception{
        emailModel.setSender("");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/send-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailModel));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest() )
                .andExpect(content().json("{\"message\": \"Validation Failed\",\"details\": \"Invalid field: 'sender' - must not be empty\"}"))
                .andReturn();
    }

    @Test
    public void sendMailTestInvalidFrom() throws Exception{
        emailModel.setFrom("test");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/send-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailModel));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest() )
                .andExpect(content().json("{\"message\": \"Validation Failed\",\"details\": \"Invalid field: 'from' - Invalid e-mail address\"}"))
                .andReturn();
    }
}
