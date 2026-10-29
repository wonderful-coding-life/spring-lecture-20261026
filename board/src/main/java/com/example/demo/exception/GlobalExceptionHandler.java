package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleNotFound(ResourceNotFoundException exception) {
        return errorPage(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ArticleAccessDeniedException.class)
    public ModelAndView handleAccessDenied(ArticleAccessDeniedException exception) {
        return errorPage(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    private ModelAndView errorPage(HttpStatus status, String message) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.setStatus(status);
        modelAndView.addObject("status", status.value());
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}
