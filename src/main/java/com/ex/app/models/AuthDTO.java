package com.ex.app.models;

public class AuthDTO {
    private String role;
    private String id;

    public AuthDTO() {
    }

    public AuthDTO(String role, String id) {
        this.role = role;
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
