package com.ande.reclamos.model;

import com.google.gson.annotations.SerializedName;

public class ReclamosDetalle {

    @SerializedName("id")
    private int id;

    @SerializedName("observacion")
    private String observacion;

    @SerializedName("averia")
    private Averia averia;

    @SerializedName("reclamo")
    private Reclamo reclamo;

    public Averia getAveria() {
        return averia;
    }

    public void setAveria(Averia averia) {
        this.averia = averia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Reclamo getReclamo() {
        return reclamo;
    }

    public void setReclamo(Reclamo reclamo) {
        this.reclamo = reclamo;
    }
}
