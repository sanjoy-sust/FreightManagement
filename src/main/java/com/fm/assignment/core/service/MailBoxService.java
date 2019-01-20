package com.fm.assignment.core.service;

import com.fm.assignment.core.enums.MailStatusEnum;
import com.fm.assignment.core.params.MailBoxParam;

import java.util.List;

/**
 * Created by Lenovo on 13/02/2018.
 */

public interface MailBoxService {
    long addMailBox(MailBoxParam param);
    long updateMailBox(MailBoxParam param);
    List<MailBoxParam> getAllMailBox();
    List<MailBoxParam> getMailBoxByStatus(MailStatusEnum status);
}
