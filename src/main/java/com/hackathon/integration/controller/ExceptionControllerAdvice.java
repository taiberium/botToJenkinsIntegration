package com.hackathon.integration.controller;

import com.hackathon.integration.model.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    private static void logException(HttpServletRequest request, Exception e) {
        log.error("Exception at {}", request.getRequestURI(), e);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ExceptionResponse handleUnexpected(NoSuchElementException e) {
        return new ExceptionResponse(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleUnexpected(HttpServletRequest request, Exception e) {
        logException(request, e);
        return new ExceptionResponse(e.getMessage());
    }
}
