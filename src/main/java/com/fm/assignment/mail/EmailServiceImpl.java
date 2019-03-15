package com.fm.assignment.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;

/**
 * Created by Lenovo on 08/02/2018.
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    public JavaMailSender emailSender;
    private final String imageLink = "images/teddy.jpeg";
    private final String imageNameToSend = "teddy.jpeg";

    @Override
    public void sendSimpleMessage(String to, String subject, String text) throws IOException, MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);


            helper.setTo(to);
            helper.setSubject(subject);

            FileSystemResource file
                    = new FileSystemResource(new ClassPathResource(imageLink).getFile());
            helper.addAttachment(imageNameToSend, file);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(text, "text/html");


            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            emailSender.send(message);
        } catch (MessagingException | IOException e ) {
            log.info("Exception catched {}",e);
            throw e;
        }
    }
}
