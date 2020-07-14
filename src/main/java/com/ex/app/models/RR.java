package com.ex.app.models;

import java.util.Date;

public class RR {

    private int id;
    private Date date;
    private Double amount;
    private String info;
    private String empID;
    private boolean resolved;
    private String manID;

    public RR() {
    }

    public RR(int id, Date date, Double amount, String info, String empID, boolean resolved, String manID) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.info = info;
        this.empID = empID;
        this.resolved = resolved;
        this.manID = manID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public String getManID() {
        return manID;
    }

    public void setManID(String manID) {
        this.manID = manID;
    }
}
