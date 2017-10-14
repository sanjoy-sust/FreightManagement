package com.fm.assignment.core.util;

import com.fm.assignment.core.entity.PathEntity;
import com.fm.assignment.errorhandler.ErrorCodes;
import com.fm.assignment.errorhandler.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Sanjoy Kumer Deb
 * @since 07/10/2017.
 */
public class GraphBuilder{
    private final Map<String, Map<String, PathEntity>> graph = new HashMap<String, Map<String, PathEntity>>();

    /**
     * This will add all locations. In graph terms its called node.
     * @param location
     * @return
     */
    public boolean addLocation(String location)
    {
        if (graph.containsKey(location)) return false;

        graph.put(location, new HashMap<String, PathEntity>());
        return true;
    }

    /**
     * This will add a directed path in graph.
     * Which will help to find all possible path from Source Location to destination Location.
     * @param source
     * @param destination
     * @param pathEntity
     */
    public void addPath(String source, String destination, PathEntity pathEntity){
        graph.get(source).put(destination, pathEntity);
    }

    /**
     * This will give all possible path no next location.
     * Here path in map will be unmodifiable.
     * @param location
     * @return
     */
    public Map<String, PathEntity> pathFrom(String location) throws ResourceNotFoundException {
        Map<String, PathEntity> paths = graph.get(location);
        if (paths == null)
        {
            throw new ResourceNotFoundException(ErrorCodes.Feature.PATH_FIND,
                    ErrorCodes.CODE.PATH_NOT_FOUND,
                    ErrorCodes.REASON_MAP.get(ErrorCodes.CODE.PATH_NOT_FOUND));
        }
        return paths;
    }
}
