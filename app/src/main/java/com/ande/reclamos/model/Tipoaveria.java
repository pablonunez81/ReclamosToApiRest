package com.ande.reclamos.model;

import com.google.gson.annotations.SerializedName;

public class Tipoaveria {

    @SerializedName("id")
    private int id;

    @SerializedName("tipoaveria")
    private String tipoaveria;

    @SerializedName("tiporeclamo")
    private String tiporeclamo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoaveria() {
        return tipoaveria;
    }

    public void setTipoaveria(String tipoaveria) {
        this.tipoaveria = tipoaveria;
    }

    public String getTiporeclamo() {
        return tiporeclamo;
    }

    public void setTiporeclamo(String tiporeclamo) {
        this.tiporeclamo = tiporeclamo;
    }
}
