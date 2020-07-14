package com.ex.app.models;

import java.util.ArrayList;

public class Employees {

    public ArrayList<Employee> employees = new ArrayList<>();

    public Employees() {
    }

    public Employees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public void addEmp(Employee emp) {
        employees.add(emp);
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }
}
