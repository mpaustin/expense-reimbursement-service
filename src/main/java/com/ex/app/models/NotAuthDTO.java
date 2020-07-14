package com.ex.app.models;

public class NotAuthDTO {
    private String message;

    public NotAuthDTO() {
    }

    public NotAuthDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
