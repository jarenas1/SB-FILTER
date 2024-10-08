package com.filtro.FILTRO_SPRINGBOOT.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderImplementation {

    @Autowired
    JavaMailSender javaMailSender;

    public void email(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        //set values
        message.setFrom("cuentadeplay2468@gmail.com"); //My Email
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        //Send
        javaMailSender.send(message);
    }
}
