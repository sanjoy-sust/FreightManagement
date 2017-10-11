package com.fm.assignment.api.validator;


import com.fm.assignment.api.model.FindPathRequest;
import com.fm.assignment.api.model.PathResource;
import com.fm.assignment.util.RequestValidationMessage;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Sanjoy on 10/11/2017.
 */
public class FindPathValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return FindPathRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FindPathRequest findPathRequest = (FindPathRequest) target;
        if(findPathRequest.getCostFrom() != null && findPathRequest.getCostTo() != null){
            if (findPathRequest.getCostTo()<findPathRequest.getCostFrom())
            {
                errors.reject("findPath.costFrom", RequestValidationMessage.COST_FROM_GREATER_THAN_TO);
            }
        }

        if(findPathRequest.getDurationFrom() != null && findPathRequest.getDurationTo() != null){
            if (findPathRequest.getDurationTo()<findPathRequest.getDurationFrom())
            {
                errors.reject("findPath.durationFrom", RequestValidationMessage.DURATION_FROM_GREATER_THAN_TO);
            }
        }

    }
}
