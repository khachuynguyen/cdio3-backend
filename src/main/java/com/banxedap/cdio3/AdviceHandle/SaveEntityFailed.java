package com.banxedap.cdio3.AdviceHandle;

public class SaveEntityFailed extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String message;

    public SaveEntityFailed(String message) {
        super(message);
    }
}
