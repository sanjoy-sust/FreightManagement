package com.fm.assignment.core.service;

import com.fm.assignment.errorhandler.DatabaseException;
import com.fm.assignment.errorhandler.ResourceNotFoundException;
import com.fm.assignment.api.model.FindPathRequest;
import com.fm.assignment.api.model.FindPathResponse;
import com.fm.assignment.api.model.PathResource;
import org.springframework.stereotype.Service;

/**
 * @author Sanjoy Kumer Deb
 * @since 10/10/2017.
 */
@Service
public interface PathService {
    long addPath(PathResource pathResource) throws ResourceNotFoundException, DatabaseException;
    FindPathResponse getAllPaths(FindPathRequest request) throws Exception;
}
