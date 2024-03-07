package com.introtomicroservices.inventory.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.introtomicroservices.inventory.exceptions.OrderNotFound;
import com.introtomicroservices.inventory.exceptions.OrderDataNotProvided;
import com.introtomicroservices.inventory.models.ApiMessage;

@ControllerAdvice
@ResponseBody
public class InventoryHandlerAdvice {
    @ExceptionHandler(OrderNotFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiMessage resourceNotFoundException(
        Exception ex, WebRequest request) {
        return getApiMessage(ex);
    }

    @ExceptionHandler(OrderDataNotProvided.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiMessage dataNotProvidedException(
        Exception ex, WebRequest request) {
        return getApiMessage(ex);
    }

    private ApiMessage getApiMessage(Exception ex) {
        return new ApiMessage(ex.getMessage());
    }
}
