package com.example.greenstoreproject.util;

public enum SuccessMessage {
    SUCCESS_CREATED("Create successfully"),
    SUCCESS_UPDATED("Update successfully"),
    SUCCESS_DELETED("Delete successfully");


    private final String message;

    SuccessMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
