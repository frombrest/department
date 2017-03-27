package com.company.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class implements handler for Rest controllers
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 */

@ControllerAdvice
public class RestExceptionHandler {

    /**
     * log4j Logger object
     */
    private final static Logger logger = LogManager.getLogger(RestExceptionHandler.class);

    /**
     * Method for hendling exceptions DataAccessException and its children
     * @param ex Handled exception
     * @return Response body message
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    String handleDataAccessException(DataAccessException ex) {
        logger.debug("Handling DataAccessException: " + ex);
        return "DataAccessException: " + ex.getLocalizedMessage();
    }
}
