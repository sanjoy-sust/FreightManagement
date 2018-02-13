package com.fm.assignment.core.params;

import com.fm.assignment.core.enums.AttachmentYnEnum;
import com.fm.assignment.core.enums.MailStatusEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Lenovo on 13/02/2018.
 */
@Data
public class MailBoxParam {

    private long id;
    private String toEmail;
    private String subject;
    private String text;
    private AttachmentYnEnum attachmentYN;
    private String attachmentName;
    private MailStatusEnum status;
}
