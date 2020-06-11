package com.ande.reclamos.model;

//    numero = models.CharField(max_length=50)
//    coordx = models.IntegerField(null=True, blank=True)
//    coordy = models.IntegerField(null=True, blank=True)

import com.google.gson.annotations.SerializedName;

public class Movil {

    @SerializedName("id")
    private int id;

    @SerializedName("numero")
    private String numero;

    @SerializedName("coordx")
    private Float coordx;

    @SerializedName("coordy")
    private Float coordy;

    @SerializedName("visible")
    private boolean visible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Float getCoordx() {
        return coordx;
    }

    public void setCoordx(Float coordx) {
        this.coordx = coordx;
    }

    public Float getCoordy() {
        return coordy;
    }

    public void setCoordy(Float coordy) {
        this.coordy = coordy;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
