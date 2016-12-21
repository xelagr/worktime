package com.luxoft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Aleksei Grishkov on 21.12.2016.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long userId) {
        super("Could not find employee with id '" + userId + "'.");
    }
}
