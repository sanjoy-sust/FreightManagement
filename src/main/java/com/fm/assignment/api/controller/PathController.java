package com.fm.assignment.api.controller;

import com.fm.assignment.api.validator.FindPathValidator;
import com.fm.assignment.errorhandler.DatabaseException;
import com.fm.assignment.errorhandler.ResourceNotFoundException;
import com.fm.assignment.api.model.*;
import com.fm.assignment.core.service.PathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Sanjoy Kumer Deb
 * @since 06/10/2017.
 */
@RestController
@Slf4j
@RequestMapping(value = "path")
public class PathController {

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder)
    {
        webDataBinder.setValidator(new FindPathValidator());
    }

    @Autowired
    private PathService pathService;

    @PostMapping
    public long addPath(@RequestBody PathResource resource) throws ResourceNotFoundException, DatabaseException {
        log.info("Add Path Request");
        return pathService.addPath(resource);
    }

    /**
     * Find all possible paths from source to destination.
     * @param resource
     * @return
     * @throws ResourceNotFoundException
     */
    @PostMapping(value = "find-all-path")
    public FindPathResponse findAllPath(@RequestBody @Valid FindPathRequest resource) throws Exception {
        return pathService.getAllPaths(resource);
    }

    @GetMapping
    public List<PathResource> getAll()
    {
        return null;
    }

    @GetMapping(value = "{id}")
    public PathResource getOne(@PathVariable long id)
    {
        return null;
    }

    @PutMapping(value = "{id}")
    public PathResource updateOne(@PathVariable long id,@RequestBody PathResource resource)
    {
        return null;
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable long id)
    {
    }

}
