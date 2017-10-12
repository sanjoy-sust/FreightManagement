package com.fm.assignment.core.util;

import com.fm.assignment.core.entity.PathEntity;
import com.fm.assignment.errorhandler.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Sanjoy Kumer Deb
 * @since 10/10/2017.
 */
public class PathFinder {
    private final GraphBuilder graphBuilder;

    public PathFinder(GraphBuilder graphBuilder) {
        this.graphBuilder = graphBuilder;
    }

    public List<List<PathEntity>> getAllPaths(String source, String destination) throws ResourceNotFoundException {
        List<List<PathEntity>> paths = new ArrayList<>();
        recursive(source, destination, paths, new LinkedHashSet<String>(), null, new LinkedHashSet<PathEntity>());
        return paths;
    }


    private void recursive(String current, String destination, List<List<PathEntity>> routes, LinkedHashSet<String> path, PathEntity pathEntity, LinkedHashSet<PathEntity> pathList) throws ResourceNotFoundException {
        path.add(current);
        if (pathEntity != null) {
            pathList.add(pathEntity);
        }
        if (current.equals(destination)) {
            routes.add(new ArrayList<PathEntity>(pathList));
            pathList.remove(pathEntity);
            path.remove(current);
            return;
        }

        Map<String, PathEntity> nodesFromCurrentNode = graphBuilder.pathFrom(current);
        final Set<String> paths = nodesFromCurrentNode.keySet();

        for (String t : paths) {
            if (!path.contains(t)) {
                recursive(t, destination, routes, path, nodesFromCurrentNode.get(t), pathList);
            }
        }
        path.remove(current);
        pathList.remove(pathEntity);
    }
}