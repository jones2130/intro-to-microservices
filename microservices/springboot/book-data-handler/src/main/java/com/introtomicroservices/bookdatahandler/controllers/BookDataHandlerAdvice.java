package com.introtomicroservices.bookdatahandler.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.introtomicroservices.bookdatahandler.exceptions.BookNotFoundException;
import com.introtomicroservices.bookdatahandler.exceptions.BookNotProvidedException;
import com.introtomicroservices.bookdatahandler.models.ApiMessage;

@ControllerAdvice
@ResponseBody
public class BookDataHandlerAdvice {
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiMessage resourceNotFoundException(
        Exception ex, WebRequest request) {
        return getApiMessage(ex);
    }

    @ExceptionHandler(BookNotProvidedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiMessage dataNotProvidedException(
        Exception ex, WebRequest request) {
        return getApiMessage(ex);
    }

    private ApiMessage getApiMessage(Exception ex) {
        return new ApiMessage(ex.getMessage());
    }
}
