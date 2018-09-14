package com.fm.assignment.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.UUID;


/**
 * Created by Lenovo on 14/02/2018.
 */
@Controller
@Slf4j
public class NotificationHandler {

    private NotificationHandler notificationHandler;

    @Autowired
    public void setGameService(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    @MessageMapping("/create/{message}")
    @SendTo("/topic/board/{message}")
    public Notification createNotification(@DestinationVariable String message) {
        Notification notification = new Notification();
        notification.setSender("Sanju");
        notification.setMessage(message);
        return notification;
    }

    @MessageMapping("/notify/{message}")
    @SendTo("/topic/notify/{message}")
    public Notification takeNotification(@DestinationVariable String message) throws IllegalArgumentException {
        Notification notification = new Notification();
        notification.setSender("Sanju Moving");
        notification.setMessage(message);
        return notification;
    }
}
