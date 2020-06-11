package com.ande.reclamos.model;

import com.google.gson.annotations.SerializedName;

class Personal {

    @SerializedName("id")
    private int id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("apellido")
    private String apellido;

    @SerializedName("nropersonal")
    private int nropersonal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getNropersonal() {
        return nropersonal;
    }

    public void setNropersonal(int nropersonal) {
        this.nropersonal = nropersonal;
    }
}
