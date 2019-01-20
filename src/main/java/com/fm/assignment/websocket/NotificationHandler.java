package com.fm.assignment.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lenovo on 14/02/2018.
 */
@Controller
public class NotificationHandler {

    @MessageMapping("/notify")
    @SendTo("/topic/messages")
    public Notification send(String message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        Notification notification = new Notification();
        notification.setSender("Flopcoder");
        notification.setMessage("Welcome to my freight management project".concat(message));
        return notification;
    }
}
