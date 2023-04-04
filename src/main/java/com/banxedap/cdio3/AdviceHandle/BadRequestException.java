package com.banxedap.cdio3.AdviceHandle;

public class BadRequestException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String message;

    public BadRequestException(String message) {
        super(message);
    }
}
