package com.fm.assignment.repository;

import com.fm.assignment.core.dao.PlaceRepository;
import com.fm.assignment.core.entity.PlaceEntity;
import com.fm.assignment.repository.constants.PlaceEntityConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void findPathByName() {
        PlaceEntity placeEntity = savePlaceData();
        Assert.assertEquals(placeEntity.getName(), PlaceEntityConstants.PLACE_NAME);
        placeRepository.delete(placeEntity);
    }

    @Test
    public void findPathByVal() {
        PlaceEntity save = savePlaceData();
        PlaceEntity placeEntity = placeRepository.findByCode(PlaceEntityConstants.PLACE_CODE);
        Assert.assertEquals(placeEntity.getName(), PlaceEntityConstants.PLACE_NAME);
        placeRepository.delete(save);
    }

    @Test
     public void findPathByValNotExits() {
        PlaceEntity placeEntity = null;
        placeEntity = placeRepository.findByCode(PlaceEntityConstants.PLACE_CODE);
        if(placeEntity != null)
        {
            placeRepository.delete(placeEntity);
            PlaceEntity pe = savePlaceData();
            PlaceEntity save = placeRepository.save(pe);
            placeRepository.delete(save);
        }
        placeEntity = placeRepository.findByCode(PlaceEntityConstants.PLACE_CODE);
        Assert.assertNull(placeEntity);
    }

    @Test(expected = NullPointerException.class)
    public void findPathByValNullPointer() {
        PlaceEntity placeEntity = placeRepository.findByCode(PlaceEntityConstants.PLACE_CODE);
        placeEntity.getName();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void checkDuplicateEntry() {
        savePlaceData();
        PlaceEntity placeEntity = placeRepository.findByCode(PlaceEntityConstants.PLACE_CODE);
        Assert.assertNotNull(placeEntity.getName());
        try {
            savePlaceData();
        }finally {
            placeRepository.delete(placeEntity);
        }
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void checkNullCodeEntry() {
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setName(PlaceEntityConstants.PLACE_NAME);
        placeEntity.setLatitude(PlaceEntityConstants.PLACE_LATITUDE);
        placeEntity.setLongitude(PlaceEntityConstants.PLACE_LONGITUDE);
        placeRepository.save(placeEntity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void checkNullNameEntry() {
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setCode(PlaceEntityConstants.PLACE_CODE);
        placeEntity.setLatitude(PlaceEntityConstants.PLACE_LATITUDE);
        placeEntity.setLongitude(PlaceEntityConstants.PLACE_LONGITUDE);
        placeRepository.save(placeEntity);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void checkNullLatLongEntry() {
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setName(PlaceEntityConstants.PLACE_NAME);
        placeEntity.setCode(PlaceEntityConstants.PLACE_CODE);
        placeRepository.save(placeEntity);
    }

    private PlaceEntity savePlaceData() {
        PlaceEntity pe = new PlaceEntity();
        pe.setName(PlaceEntityConstants.PLACE_NAME);
        pe.setCode(PlaceEntityConstants.PLACE_CODE);
        pe.setLatitude(PlaceEntityConstants.PLACE_LATITUDE);
        pe.setLongitude(PlaceEntityConstants.PLACE_LONGITUDE);
        pe = placeRepository.save(pe);
        return pe;
    }
}
