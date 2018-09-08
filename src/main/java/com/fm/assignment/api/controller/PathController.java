package com.fm.assignment.api.controller;

import com.fm.assignment.api.validator.FindPathValidator;
import com.fm.assignment.core.params.FindPathParam;
import com.fm.assignment.core.params.PathParam;
import com.fm.assignment.core.params.ResultParam;
import com.fm.assignment.errorhandler.DatabaseException;
import com.fm.assignment.errorhandler.ResourceNotFoundException;
import com.fm.assignment.api.model.*;
import com.fm.assignment.core.service.PathService;
import com.fm.assignment.util.RequestAndParamBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanjoy Kumer Deb
 * @since 06/10/2017.
 */
@RestController
@Slf4j
@RequestMapping(value = "path")
public class PathController {

    @InitBinder("findPathRequest")
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new FindPathValidator());
    }

    @Autowired
    private PathService pathService;

   @PostMapping
    public long addPath(@RequestBody @Valid PathResource resource) throws ResourceNotFoundException, DatabaseException {
        log.info("Add Path Request");
        return pathService.addPath(RequestAndParamBuilder.buildPathParam(resource));
    }

    /**
     * Find all possible paths from source to destination.
     *
     * @param resource
     * @return
     * @throws ResourceNotFoundException
     */
    @PostMapping(value = "find-all-path")
    public FindPathResponse findAllPath(@RequestBody @Valid FindPathRequest resource) throws Exception {
        FindPathParam findPathParam = RequestAndParamBuilder.buildFindPathParam(resource);
        List<ResultParam> allPaths = pathService.getAllPaths(findPathParam);
        return buildResponse(allPaths);
    }

    @GetMapping
    public List<PathResource> getAll() {
        List<PathParam> params = pathService.findAll();
        List<PathResource> resourceList = new ArrayList<>();
        for (PathParam param: params)
        {
            resourceList.add(RequestAndParamBuilder.buildPathResource(param));
        }
        return resourceList;
    }

    @GetMapping(value = "{id}")
    public PathResource getOne(@PathVariable long id) {
        return RequestAndParamBuilder.buildPathResource(pathService.findById(id));
    }

    @PutMapping(value = "{id}")
    public Long updateOne(@PathVariable long id, @RequestBody PathResource resource) throws ResourceNotFoundException {
        return pathService.updateOne(id,RequestAndParamBuilder.buildPathParam(resource));
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable long id) {
    }

    private FindPathResponse buildResponse(List<ResultParam> allPaths) {
        FindPathResponse response = new FindPathResponse();
        List<ResultsResource> results  = new ArrayList<>();
        for (ResultParam param : allPaths)
        {
            ResultsResource resultsResource = new ResultsResource();
            resultsResource.setTotalCost(param.getTotalCost());
            resultsResource.setTotalDuration(param.getTotalDuration());
            List<RouteResource> routes = new ArrayList<>();
            for (PathParam pathParam : param.getRoute())
            {
                RouteResource routeResource = RequestAndParamBuilder.buildRoutResource(pathParam);
                routes.add(routeResource);
            }
            resultsResource.setRoute(routes);
            results.add(resultsResource);
        }
        response.setResults(results);
        return response;
    }
}
