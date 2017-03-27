package com.company.webapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

/**
 * This class implements handler for Webapp controller
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 */

@ControllerAdvice
public class WebappExceptionHandler {

    /**
     * log4j Logger object
     */
    private final static Logger logger = LogManager.getLogger(WebappExceptionHandler.class);

    /**
     * Method for hendling exceptions RestClientException and its children
     * @param ex Handled exception
     * @return Response body message
     */
    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    String handleDataAccessException(RestClientException ex) {
        logger.debug("Handling RestClientException: " + ex);
        return "RestClientException: " + ex.getLocalizedMessage();
    }
}
