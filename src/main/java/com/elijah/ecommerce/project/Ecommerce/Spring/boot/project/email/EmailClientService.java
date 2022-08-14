package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailClientService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendSimpleEmail(String toEmail,String subject,String body){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ukemespring@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailSender.send(mailMessage);
        System.out.println("Email Send.....");

    }
}
