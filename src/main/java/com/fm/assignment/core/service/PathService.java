package com.fm.assignment.core.service;

import com.fm.assignment.core.params.FindPathParam;
import com.fm.assignment.core.params.PathParam;
import com.fm.assignment.core.params.ResultParam;
import com.fm.assignment.errorhandler.DatabaseException;
import com.fm.assignment.errorhandler.ResourceNotFoundException;
import com.fm.assignment.api.model.FindPathRequest;
import com.fm.assignment.api.model.FindPathResponse;
import com.fm.assignment.api.model.PathResource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sanjoy Kumer Deb
 * @since 10/10/2017.
 */
@Service
public interface PathService {
    long addPath(PathParam pathParam) throws ResourceNotFoundException, DatabaseException;
    List<ResultParam> getAllPaths(FindPathParam param) throws Exception;
    PathParam findById(long id);
    List<PathParam> findAll();
    Long updateOne(long id, PathParam pathParam) throws ResourceNotFoundException;
}
