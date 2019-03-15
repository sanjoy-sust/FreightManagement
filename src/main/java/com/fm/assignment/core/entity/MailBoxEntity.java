package com.fm.assignment.core.entity;

import com.fm.assignment.core.enums.AttachmentYnEnum;
import com.fm.assignment.core.enums.MailStatusEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Lenovo on 12/02/2018.
 */
@Data
@Entity(name = "MAIL_BOX")
public class MailBoxEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    @Column(name = "TO_EMAIL")
    private String toEmail;
    @Column(name = "SUBJECT")
    private String subject;
    @Column(name = "BODY_TEXT")
    private String text;
    @Column(name = "ATTACHMENT_YN")
    @Enumerated(EnumType.STRING)
    private AttachmentYnEnum attachmentYN;
    @Column(name = "ATTACHMENT_NAME")
    private String attachmentName;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private MailStatusEnum status;
}
