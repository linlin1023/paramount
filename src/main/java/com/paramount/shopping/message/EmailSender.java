package com.paramount.shopping.message;

import com.paramount.admin.service.SendMailSevice;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class EmailSender {

    @Autowired
    private  SendMailSevice sendMailSevice;


    /*public static void main(String[] args) {
        try {
            new EmailSender().sendMail("applepll1023@163.com","1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Transactional
    public  void sendMail(String emailString, String code) throws Exception {

        Email email = new SimpleEmail();
        email.setHostName("smtp.office365.com");
        email.setSmtpPort(587);
        email.setTLS(true);
        email.setAuthenticator(new DefaultAuthenticator("support@paramountmerchandise.co.nz", "Welcome2017!"));
        email.setSSL(false);
        email.setFrom("support@paramountmerchandise.co.nz");
        email.setSubject("Registration code of Boxlot");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        email.setMsg("Hi,\n This is Boxlot system automatically send mail at "+ dtf.format(now) + ".\nThe verification code of your registration is "+ code + ", you can input this code in the boxlot registration page. \n Have a good day!");
        email.addTo(emailString);
        email.send();
    }
}
