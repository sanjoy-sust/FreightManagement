package com.fm.assignment.mail;

/**
 * Created by Lenovo on 08/02/2018.
 */
public interface EmailService {
    void sendSimpleMessage(
            String to, String subject, String text);
}
