package com.fm.assignment.core.dao;

import com.fm.assignment.core.entity.MailBoxEntity;
import com.fm.assignment.core.enums.MailStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Lenovo on 13/02/2018.
 */
@Repository
@Transactional(readOnly = true)
public interface MailBoxRepository extends JpaRepository<MailBoxEntity, Long> {
    List<MailBoxEntity> findByStatus(MailStatusEnum status);
}
