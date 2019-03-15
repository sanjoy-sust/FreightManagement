package com.fm.assignment.api.controller;

import com.fm.assignment.api.model.PlaceResource;
import com.fm.assignment.core.params.PlaceParam;
import com.fm.assignment.core.service.PlaceService;
import com.fm.assignment.errorhandler.DatabaseException;
import com.fm.assignment.util.RequestAndParamBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanjoy Kumer Deb
 * @since 06/10/2017.
 */
@RestController
@Slf4j
@RequestMapping(value = "place")
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @PostMapping
    public long addPlace(@RequestBody PlaceResource resource) throws DatabaseException {
        log.info("add place request");
        PlaceParam placeParam = RequestAndParamBuilder.buildPlaceParam(resource);
        return placeService.addPlace(placeParam);
    }
    @GetMapping
    public List<PlaceResource> getAll()
    {
        List<PlaceParam> placeParams = placeService.getAllPlaces();
        List<PlaceResource> placeResourceList = new ArrayList<>();
        for (PlaceParam placeParam:placeParams)
        {
            PlaceResource resource = RequestAndParamBuilder.buildPlaceResource(placeParam);
            placeResourceList.add(resource);
        }
        return placeResourceList;
    }

    @GetMapping(value = "{id}")
    public PlaceResource getOne(@PathVariable long id)
    {
        PlaceParam param = placeService.findOne(id);
        return RequestAndParamBuilder.buildPlaceResource(param);
    }

    @PutMapping(value = "{id}")
    public Long update(@PathVariable long id,@RequestBody PlaceResource placeResource)
    {
        return placeService.updatePlace(id,
                RequestAndParamBuilder.buildPlaceParam(placeResource));
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable long id)
    {
    }
}
