package com.ex.app.models;

public class Manager {

    private String manID;
    private String password;
    private String name;

    public Manager() {
    }

    public Manager(String manID, String password, String name) {
        this.manID = manID;
        this.password = password;
        this.name = name;
    }

    public String getManID() {
        return manID;
    }

    public void setManID(String manID) {
        this.manID = manID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
