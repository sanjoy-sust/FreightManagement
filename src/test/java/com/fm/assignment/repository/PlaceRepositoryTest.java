package com.fm.assignment.repository;

import com.fm.assignment.core.dao.PathRepository;
import com.fm.assignment.core.dao.PlaceRepository;
import com.fm.assignment.core.entity.PathEntity;
import com.fm.assignment.core.entity.PlaceEntity;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PlaceRepositoryTest {
    @Autowired
    PlaceRepository placeRepository;

    @Test
    public void addTest() {
        PlaceEntity save = savePlaceData();
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
        PlaceEntity placeEntity = savePlaceData();
        Assert.assertEquals(placeEntity.getName(), "DD");
    }

    @Test
    public void findPathByVal() {
        PlaceEntity pe = savePlaceData();
        PlaceEntity save = placeRepository.save(pe);
        PlaceEntity placeEntity = placeRepository.findByCode("CMI");
        Assert.assertEquals(placeEntity.getName(), "DD");
        placeRepository.delete(save);
    }

    @Test
     public void findPathByValNotExits() {
        PlaceEntity placeEntity = null;
        placeEntity = placeRepository.findByCode("CMI");
        if(placeEntity != null)
        {
            placeRepository.delete(placeEntity);
            PlaceEntity pe = savePlaceData();
            PlaceEntity save = placeRepository.save(pe);
            placeRepository.delete(save);
        }
        placeEntity = placeRepository.findByCode("CMI");
        Assert.assertNull(placeEntity);
    }

    @Test(expected = NullPointerException.class)
    public void findPathByValNullPointer() {
        PlaceEntity placeEntity = placeRepository.findByCode("CMI");
        String name = placeEntity.getName();
        log.error(name);
    }

    private PlaceEntity savePlaceData() {
        PlaceEntity pe = new PlaceEntity();
        pe.setName("DD");
        pe.setCode("CMI");
        pe.setLatitude(23.894929);
        pe.setLongitude(90.868706);
        return pe;
    }
}
