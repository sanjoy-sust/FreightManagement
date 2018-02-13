package com.fm.assignment.mail;

import com.fm.assignment.core.enums.MailStatusEnum;
import com.fm.assignment.core.params.MailBoxParam;
import com.fm.assignment.core.service.MailBoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Lenovo on 12/02/2018.
 */
@Service
@Slf4j
public class EmailScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private MailBoxService mailBoxService;

    @Scheduled(fixedDelay = 3000)
    public void sendEmailSchedule(){
        log.info("Email schedule started at {}",new Date().getTime());
        List<MailBoxParam> mailBoxParams = mailBoxService.getMailBoxByStatus(MailStatusEnum.PENDING);


        for (MailBoxParam param : mailBoxParams) {
            try {
                emailService.sendSimpleMessage(param.getToEmail(), param.getSubject(), param.getText());
                param.setStatus(MailStatusEnum.SENT);
                mailBoxService.updateMailBox(param);
                log.info("Email SENT to {}", param.getToEmail());
            }catch (Exception ert)
            {
                param.setStatus(MailStatusEnum.FAILED);
                mailBoxService.updateMailBox(param);
                log.info("Email SENT Failed to {} {}", param.getToEmail(),ert);
            }

        }
        log.info("Email schedule ended {}",new Date().getTime());
    }
}
