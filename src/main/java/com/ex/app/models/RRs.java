package com.ex.app.models;

import java.util.ArrayList;

public class RRs {
    public ArrayList<RR> rrs = new ArrayList<>();

    public RRs() {
    }

    public RRs(ArrayList<RR> rrs) {
        this.rrs = rrs;
    }

    public void addRR(RR rr) {
        rrs.add(rr);
    }

    public ArrayList<RR> getRRs() {
        return rrs;
    }

    public void setRRs(ArrayList<RR> rrs) {
        this.rrs = rrs;
    }
}
