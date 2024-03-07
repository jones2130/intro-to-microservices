package com.introtomicroservices.inventory.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.introtomicroservices.inventory.exceptions.OrderNotFound;
import com.introtomicroservices.shared.models.ApiMessage;
import com.introtomicroservices.inventory.exceptions.OrderDataNotProvided;

/**
 * @author jjones
 */
@ControllerAdvice
@ResponseBody
public class InventoryHandlerAdvice {
    /**
     * Handles exceptions when resources are not found, returns a 404 status code
     * @param ex the exception to be handled
     * @param request the web request
     * @return an HTTP API message
     */
    @ExceptionHandler(OrderNotFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiMessage resourceNotFoundException(
        Exception ex, WebRequest request) {
        return getApiMessage(ex);
    }

    /**
     * Handleds exceptions when form data is not provided, returns a 400 status code
     * @param ex the exception to be handled
     * @param request the web request
     * @return an HTTP API message
     */
    @ExceptionHandler(OrderDataNotProvided.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiMessage dataNotProvidedException(
        Exception ex, WebRequest request) {
        return getApiMessage(ex);
    }

    /**
     * Returns an API message with the exception message string
     * @param ex the exception
     * @return the API message
     */
    private ApiMessage getApiMessage(Exception ex) {
        return new ApiMessage(ex.getMessage());
    }
}
