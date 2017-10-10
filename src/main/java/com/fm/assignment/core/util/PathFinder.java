package com.fm.assignment.core.util;

import com.fm.assignment.core.entity.PathEntity;
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

    public List<List<PathEntity>> getAllPaths(String source, String destination) {
        List<List<PathEntity>> paths = new ArrayList<>();
        recursive(source, destination, paths, new LinkedHashSet<String>(), null, new LinkedHashSet<PathEntity>());
        return paths;
    }


    private void recursive(String current, String destination, List<List<PathEntity>> routes, LinkedHashSet<String> path, PathEntity pathEntity, LinkedHashSet<PathEntity> pathList) {
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

        Map<String, PathEntity> stringPathEntityMap = graphBuilder.pathFrom(current);
        final Set<String> paths = stringPathEntityMap.keySet();

        for (String t : paths) {
            if (!path.contains(t)) {
                recursive(t, destination, routes, path, stringPathEntityMap.get(t), pathList);
            }
        }
        path.remove(current);
        pathList.remove(pathEntity);
    }
}