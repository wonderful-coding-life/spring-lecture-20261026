package com.example.demo.exception;

public class DuplicateMemberException extends RuntimeException {

    private final String field;

    public DuplicateMemberException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
