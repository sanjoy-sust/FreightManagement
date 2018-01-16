package com.fm.assignment.repository;

import com.fm.assignment.core.dao.PathRepository;
import com.fm.assignment.core.entity.PathEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Lenovo on 16/01/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PathRepositoryTest {
    @Autowired
    PathRepository pathRepository;

    @Test
    public void findPathByContainerSize()
    {
        List<PathEntity> byContainerSize = pathRepository.findByContainerSize(20);
        Assert.assertNotNull(byContainerSize);
    }
}
