package com.fm.assignment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by Lenovo on 08/02/2018.
 */
@Configuration
@PropertySource("classpath:mail.properties")
public class SmtpClientConfig {
    final String SMTP_HOST ="spring.mail.host";
    final String SMTP_PORT = "spring.mail.port";
    final String SMTP_USER_NAME = "spring.mail.username";
    final String SMTP_USER_PASS = "spring.mail.password";
    final String SMTP_USER_AUTH = "spring.mail.properties.mail.smtp.auth";
    final String SMTP_USER_TTLS = "spring.mail.properties.mail.smtp.starttls.enable";

    @Autowired
    private Environment env;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty(SMTP_HOST));
        mailSender.setPort(Integer.valueOf(env.getProperty(SMTP_PORT)));

        mailSender.setUsername(env.getProperty(SMTP_USER_NAME));
        mailSender.setPassword(env.getProperty(SMTP_USER_PASS));

        Properties props = mailSender.getJavaMailProperties();
      //  props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return mailSender;
    }
}
