package com.ex.app.service;

import com.ex.app.models.Employee;
import com.ex.app.models.Manager;
import com.ex.app.models.RR;
import com.ex.app.persistence.Persistable;

import java.util.List;

public class Service {
    Persistable persistence;

    public Service() {
    }

    public Service(Persistable persistence) {
        this.persistence = persistence;
    }

    public List<RR> getAllRR() {
        return persistence.getAllRR();
    }

    public List<RR> getAllPendingRR() {
        return persistence.getAllPendingRR();
    }

    public List<RR> getAllResRR() {
        return persistence.getAllResRR();
    }

    public List<RR> getAllEmpRR(String id) {
        return persistence.getAllEmpRR(id);
    }

    public List<RR> getAllEmpResRR(String id) {
        return persistence.getAllEmpResRR(id);
    }

    public List<Employee> getAllEmp() {
        return persistence.getAllEmp();
    }

    public List<Manager> getAllMan() {
        return persistence.getAllMan();
    }

    public RR getRRByID(int id) {
        return persistence.rrGetByID(id);
    }

    public Employee getEmpByID(String id) {
        return persistence.empGetByID(id);
    }

    public Manager getManByID(String id) {
        return persistence.manGetByID(id);
    }

    public void addNewRR(RR rr) {
        persistence.addNewRR(rr);
    }

    public void addNewEmp(Employee emp) {
        persistence.addNewEmp(emp);
    }

    public void addNewMan(Manager man) {
        persistence.addNewMan(man);
    }

    public void updateRR(RR rr) {
        persistence.updateRR(rr);
    }

    public void updateEmpEmail(String id, String email) {
        persistence.updateEmpEmail(id, email);
    }

    public void updateEmpPassword(String id, String password) {
        persistence.updateEmpPassword(id, password);
    }
}
