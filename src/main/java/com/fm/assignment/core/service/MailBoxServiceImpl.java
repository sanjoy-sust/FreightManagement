package com.fm.assignment.core.service;

import com.fm.assignment.core.dao.MailBoxRepository;
import com.fm.assignment.core.entity.MailBoxEntity;
import com.fm.assignment.core.enums.MailStatusEnum;
import com.fm.assignment.core.params.MailBoxParam;
import com.fm.assignment.core.util.ParamAndEntityBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 13/02/2018.
 */
@Service
@Slf4j
public class MailBoxServiceImpl implements MailBoxService {

    @Autowired
    private MailBoxRepository mailBoxRepository;

    @Autowired
    private ParamAndEntityBuilder paramAndEntityBuilder;

    @Override
    @Transactional
    public long addMailBox(MailBoxParam param) {
        MailBoxEntity entity = paramAndEntityBuilder.buildMailBoxEntity(param);
        MailBoxEntity saved = mailBoxRepository.save(entity);
        return saved.getId();
    }

    @Override
    public long updateMailBox(MailBoxParam param) {
        MailBoxEntity entity = paramAndEntityBuilder.buildMailBoxEntity(param);
        MailBoxEntity saved = mailBoxRepository.save(entity);
        return saved.getId();
    }

    @Override
    public List<MailBoxParam> getAllMailBox() {
        List<MailBoxEntity> entityList = mailBoxRepository.findAll();
        return getMailBoxParamsList(entityList);
    }

    @Override
    public List<MailBoxParam> getMailBoxByStatus(MailStatusEnum status) {
        List<MailBoxEntity> entityList = mailBoxRepository.findByStatus(status);
        return getMailBoxParamsList(entityList);
    }

    private List<MailBoxParam> getMailBoxParamsList(List<MailBoxEntity> entityList) {
        List<MailBoxParam> paramList = new ArrayList<>();
        for (MailBoxEntity entity : entityList) {
            paramList.add(paramAndEntityBuilder.buildMailBoxParam(entity));
        }
        return paramList;
    }
}
