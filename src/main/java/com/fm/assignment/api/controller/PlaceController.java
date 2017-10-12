package com.fm.assignment.api.controller;

import com.fm.assignment.api.model.PlaceResource;
import com.fm.assignment.core.params.PlaceParam;
import com.fm.assignment.core.service.PlaceService;
import com.fm.assignment.errorhandler.DatabaseException;
import com.fm.assignment.util.RequestAndParamBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return null;
    }

    @GetMapping(value = "{id}")
    public PlaceResource getOne(@PathVariable long id)
    {
        return null;
    }

    @PutMapping(value = "{id}")
    public PlaceResource updateOne(@PathVariable long id,@RequestBody PlaceResource placeResource)
    {
        return null;
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable long id)
    {
    }
}
