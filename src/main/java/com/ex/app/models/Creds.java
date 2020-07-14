package com.ex.app.models;

public class Creds {
    private String credId;
    private String credPassword;
    private boolean isManager;

    public Creds() {
    }

    public Creds(String credId, String credPassword, boolean isManager) {
        this.credId = credId;
        this.credPassword = credPassword;
        this.isManager = isManager;
    }

    public String getCredId() {
        return credId;
    }

    public void setCredId(String credId) {
        this.credId = credId;
    }

    public String getCredPassword() {
        return credPassword;
    }

    public void setCredPassword(String credPassword) {
        this.credPassword = credPassword;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }
}
