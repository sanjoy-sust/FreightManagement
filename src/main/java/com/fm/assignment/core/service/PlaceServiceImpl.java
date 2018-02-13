package com.fm.assignment.core.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fm.assignment.core.entity.PlaceEntity;
import com.fm.assignment.api.model.PlaceResource;
import com.fm.assignment.core.dao.PlaceRepository;
import com.fm.assignment.core.enums.AttachmentYnEnum;
import com.fm.assignment.core.enums.MailStatusEnum;
import com.fm.assignment.core.params.MailBoxParam;
import com.fm.assignment.core.params.PlaceParam;
import com.fm.assignment.core.util.ParamAndEntityBuilder;
import com.fm.assignment.errorhandler.DatabaseException;
import com.fm.assignment.errorhandler.ErrorCodes;
import com.fm.assignment.mail.EmailService;
import com.vividsolutions.jts.awt.PointShapeFactory;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** This service is used to add Locations to location table
 * @author Sanjoy Kumer Deb
 * @since 07/10/2017.
 */
@Service
@Slf4j
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ParamAndEntityBuilder paramAndEntityBuilder;

    @Autowired
    private MailBoxService emailService;

    @Override
    @Transactional
    public Long addPlace(PlaceParam placeParam) throws DatabaseException {
        PlaceEntity entity = new PlaceEntity();
        entity.setCode(placeParam.getCode());
        entity.setName(placeParam.getName());
        entity.setLongitude(placeParam.getLongitude());
        entity.setLatitude(placeParam.getLatitude());
        PlaceEntity savedPlaceEntity;
        try {
            savedPlaceEntity = placeRepository.save(entity);
            addMailBox(savedPlaceEntity);
        } catch (Exception exp) {
            log.info("{}",exp);
            throw new DatabaseException(ErrorCodes.Feature.PLACE_ADD,
                    ErrorCodes.CODE.PLACE_SAVE_FAIL,ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.PLACE_SAVE_FAIL));
        }
        return savedPlaceEntity.getId();
    }

    private void addMailBox(PlaceEntity placeEntity) {
        MailBoxParam param = new MailBoxParam();
        param.setToEmail("sanjoyd.cse@gmail.com");
        param.setSubject("Freight Management Place Added.");
        param.setText("<html><body><h1>Place added for "+placeEntity.getName()+"</h1></br>" +
                "<p>" +
                "Latitude : "+placeEntity.getLatitude()+"</br>" +
                "Longitude : "+placeEntity.getLongitude()+"</br>" +
                "</p>" +
                "</body></html>");
        param.setAttachmentYN(AttachmentYnEnum.YES);
        param.setAttachmentName("teddy.jpeg");
        param.setStatus(MailStatusEnum.PENDING);
        emailService.addMailBox(param);
    }

    @Override
    @Transactional
    public Long updatePlace(long id, PlaceResource resource) {
        return null;
    }

    @Override
    public List<PlaceParam> getAllNearestPlaces(Double latitude,Double longitude,Double distance) {
        List<PlaceParam> locationWithinParams = new ArrayList<>();
        List<PlaceEntity> locationsWithinDistance = placeRepository.findLocationWithin(latitude,longitude,distance);
        locationWithinParams.addAll(locationsWithinDistance.stream().map(paramAndEntityBuilder::buildPlaceParam).collect(Collectors.toList()));
        return locationWithinParams;
    }

    @Override
    public List<PlaceParam> getAllPlaces() {
        List<PlaceEntity> placeEntities = placeRepository.findAll();
        List<PlaceParam> placeParams = new ArrayList<>();
        placeParams.addAll(placeEntities.stream().map(paramAndEntityBuilder::buildPlaceParam).collect(Collectors.toList()));
        return placeParams;
    }
}
