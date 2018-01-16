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
    public void addTest() {
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setName("Sylhet");
        placeEntity.setCode("SYL");
        placeEntity.setLatitude(24.894929);
        placeEntity.setLongitude(91.868706);
        PlaceEntity save = placeRepository.save(placeEntity);
        long id = save.getId();
        Assert.assertNotNull(save);
        placeRepository.delete(save);
        Assert.assertNull(placeRepository.findOne(id));
    }

    @Test(expected = Exception.class)
    public void addEmptyObj() {
        PlaceEntity placeEntity = new PlaceEntity();
        placeRepository.save(placeEntity);
    }

    @Test
    public void findPathByName() {
        PlaceEntity placeEntity = placeRepository.findByName("Comilla");
        Assert.assertEquals(placeEntity.getName(), "Comilla");
    }

    @Test
    public void findPathByVal() {
        PlaceEntity placeEntity = placeRepository.findByCode("CML");
        Assert.assertEquals(placeEntity.getName(), "Comilla");
    }
}
