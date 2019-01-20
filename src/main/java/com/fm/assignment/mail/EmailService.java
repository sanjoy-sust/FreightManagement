package com.fm.assignment.mail;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created by Lenovo on 08/02/2018.
 */
public interface EmailService {
    void sendSimpleMessage(
            String to, String subject, String text) throws IOException, MessagingException;
}
