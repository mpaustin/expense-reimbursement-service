package com.ex.app.models;

import java.util.ArrayList;

public class Managers {

    public ArrayList<Manager> managers = new ArrayList<>();

    public Managers() {
    }

    public Managers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public void addMan(Manager man) {
        managers.add(man);
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }
}
