package com.example.demo.exception;

public class ArticleAccessDeniedException extends RuntimeException {

    public ArticleAccessDeniedException(String message) {
        super(message);
    }
}
