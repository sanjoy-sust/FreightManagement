package com.fm.assignment.repository;

import com.fm.assignment.core.dao.PathRepository;
import com.fm.assignment.core.dao.PlaceRepository;
import com.fm.assignment.core.entity.PathEntity;
import com.fm.assignment.core.entity.PlaceEntity;
import com.fm.assignment.core.enums.TransportTypeEnum;
import com.fm.assignment.repository.constants.PathEntityConstants;
import com.fm.assignment.repository.constants.PlaceEntityConstants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 16/01/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PathRepositoryTest {
    @Autowired
    PathRepository pathRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Test
    public void addTest()
    {
        List<PlaceEntity> placeEntities = savePlaceData(2);

        PathEntity pathEntity = new PathEntity();
        pathEntity.setFromCode(placeEntities.get(0));
        pathEntity.setToCode(placeEntities.get(1));
        pathEntity.setContainerSize(PathEntityConstants.CONTAINER_SIZE);
        pathEntity.setCost(PathEntityConstants.COST);
        pathEntity.setDuration(PathEntityConstants.DURATION);
        pathEntity.setRouteType(TransportTypeEnum.Road);
        PathEntity savedPath = pathRepository.save(pathEntity);
        Assert.assertNotNull(savedPath);
        pathRepository.delete(savedPath);
        Assert.assertNull(pathRepository.findOne(savedPath.getId()));
        placeRepository.delete(placeEntities);
    }

    @Test
    public void findPathByContainerSize()
    {
        List<PathEntity> byContainerSize = pathRepository.findByContainerSize(20);
        Assert.assertNotNull(byContainerSize);
    }

    private List<PlaceEntity> savePlaceData(int noOfPlaces) {
        List<PlaceEntity> savedPlaces = new ArrayList<>();
        for (int i=1;i<=noOfPlaces;i++) {
            PlaceEntity pe = new PlaceEntity();
            pe.setName(PlaceEntityConstants.PLACE_NAME.concat(String.valueOf(i)));
            pe.setCode(PlaceEntityConstants.PLACE_CODE.concat(String.valueOf(i)));
            pe.setLatitude(PlaceEntityConstants.PLACE_LATITUDE+i);
            pe.setLongitude(PlaceEntityConstants.PLACE_LONGITUDE+i);
            pe = placeRepository.save(pe);
            savedPlaces.add(pe);
        }
        return savedPlaces;
    }
}
