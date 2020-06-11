package com.ande.reclamos.model;

import com.google.gson.annotations.SerializedName;

public class Suministro {

    @SerializedName("id")
    private int id;

    @SerializedName("nis")
    private String nis;

    @SerializedName("coordx")
    private double coordx;

    @SerializedName("coordy")
    private double coordy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public double getCoordx() {
        return coordx;
    }

    public void setCoordx(double coordx) {
        this.coordx = coordx;
    }

    public double getCoordy() {
        return coordy;
    }

    public void setCoordy(double coordy) {
        this.coordy = coordy;
    }
}
