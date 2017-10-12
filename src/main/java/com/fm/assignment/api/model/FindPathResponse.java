package com.fm.assignment.api.model;

import lombok.Data;

import java.util.List;

/**
 * @author Sanjoy Kumer Deb
 * @since 06/10/2017.
 */
@Data
public class FindPathResponse {
    private List<Results> results;
}
