package com.ex.app.persistence;

import java.sql.Connection;
import java.util.List;

import com.ex.app.models.Employee;
import com.ex.app.models.Manager;
import com.ex.app.models.RR;

public interface Persistable {

    List<RR> getAllRR();
    List<RR> getAllPendingRR();
    List<RR> getAllResRR();
    List<RR> getAllEmpRR(String id);
    List<RR> getAllEmpResRR(String id);
    List<Employee> getAllEmp();
    List<Manager> getAllMan();

    RR rrGetByID(int id);
    Employee empGetByID(String id);
    Manager manGetByID(String id);

    void addNewRR(RR rr);
    void addNewEmp(Employee emp);
    void addNewMan(Manager man);

    void updateRR(RR rr);
    void updateEmpEmail(String id, String email);
    void updateEmpPassword(String id, String password);

    void deleteRR(RR rr);
    void deleteEmp(Employee emp);
    void deleteMan(Manager man);
}
