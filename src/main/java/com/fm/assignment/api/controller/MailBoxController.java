package com.fm.assignment.api.controller;

import com.fm.assignment.core.enums.AttachmentYnEnum;
import com.fm.assignment.core.enums.MailStatusEnum;
import com.fm.assignment.core.params.MailBoxParam;
import com.fm.assignment.core.service.MailBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lenovo on 13/02/2018.
 * This Class only for Test
 */
@RestController
@RequestMapping(value = "mailbox")
public class MailBoxController {

    @Autowired
    private MailBoxService mailBoxService;

    @PostMapping
    public long addMailBox()
    {
        MailBoxParam param = new MailBoxParam();
        param.setToEmail("sanjoyd.cse@gmailcom");
        param.setSubject("Freight Ma");
        param.setText("Test Freit");
        param.setAttachmentYN(AttachmentYnEnum.YES);
        param.setAttachmentName("tedd");
        param.setStatus(MailStatusEnum.PENDING);
        return mailBoxService.addMailBox(param);
    }
}
