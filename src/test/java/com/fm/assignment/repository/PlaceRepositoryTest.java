package com.fm.assignment.repository;

import com.fm.assignment.core.dao.PathRepository;
import com.fm.assignment.core.dao.PlaceRepository;
import com.fm.assignment.core.entity.PathEntity;
import com.fm.assignment.core.entity.PlaceEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Lenovo on 17/01/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceRepositoryTest {
    @Autowired
    PlaceRepository placeRepository;

    @Test
    public void findPathByName()
    {
        PlaceEntity syl = placeRepository.findByName("Comilla");
        Assert.assertNotNull(syl);
    }
}
