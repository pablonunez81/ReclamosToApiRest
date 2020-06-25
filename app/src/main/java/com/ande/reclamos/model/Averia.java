package com.ande.reclamos.model;

import com.google.gson.annotations.SerializedName;

public class Averia {

    @SerializedName("id")
    private int id;

    @SerializedName("codigo")
    private String codigo;

    @SerializedName("averia")
    private String averia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAveria() {
        return averia;
    }

    public void setAveria(String averia) {
        this.averia = averia;
    }
}
